package com.xiaou.collabboard.websocket;

import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class CollabWebSocketHandler extends TextWebSocketHandler {

    private final Map<String, CopyOnWriteArraySet<WebSocketSession>> documentSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String documentId = getDocumentId(session);
        if (documentId != null) {
            documentSessions.computeIfAbsent(documentId, k -> new CopyOnWriteArraySet<>()).add(session);
            
            Map<String, Object> joinMessage = Map.of(
                "type", "USER_JOIN",
                "userId", getUserId(session),
                "onlineCount", documentSessions.get(documentId).size()
            );
            broadcast(documentId, new TextMessage(JSONUtil.toJsonStr(joinMessage)), session);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String documentId = getDocumentId(session);
        if (documentId != null) {
            broadcast(documentId, message, session);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String documentId = getDocumentId(session);
        if (documentId != null) {
            CopyOnWriteArraySet<WebSocketSession> sessions = documentSessions.get(documentId);
            if (sessions != null) {
                sessions.remove(session);
                
                Map<String, Object> leaveMessage = Map.of(
                    "type", "USER_LEAVE",
                    "userId", getUserId(session),
                    "onlineCount", sessions.size()
                );
                broadcast(documentId, new TextMessage(JSONUtil.toJsonStr(leaveMessage)), null);
                
                if (sessions.isEmpty()) {
                    documentSessions.remove(documentId);
                }
            }
        }
    }

    private void broadcast(String documentId, TextMessage message, WebSocketSession exclude) {
        CopyOnWriteArraySet<WebSocketSession> sessions = documentSessions.get(documentId);
        if (sessions != null) {
            sessions.forEach(session -> {
                if (session != exclude && session.isOpen()) {
                    try {
                        session.sendMessage(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private String getDocumentId(WebSocketSession session) {
        String path = session.getUri().getPath();
        String[] parts = path.split("/");
        return parts.length > 0 ? parts[parts.length - 1] : null;
    }

    private String getUserId(WebSocketSession session) {
        return session.getId();
    }
}


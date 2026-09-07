package com.xiaou.collabboard.websocket;

import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
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

            Map<String, Object> connectMessage = Map.of(
                    "type", "CONNECTED",
                    "userId", getUserId(session),
                    "username", getUsername(session),
                    "sessionId", session.getId(),
                    "onlineCount", documentSessions.get(documentId).size()
            );
            session.sendMessage(new TextMessage(JSONUtil.toJsonStr(connectMessage)));

            Map<String, Object> joinMessage = Map.of(
                    "type", "USER_JOIN",
                    "userId", getUserId(session),
                    "username", getUsername(session),
                    "onlineCount", documentSessions.get(documentId).size()
            );
            broadcast(documentId, new TextMessage(JSONUtil.toJsonStr(joinMessage)), session);
            broadcastOnlineCount(documentId);
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
                    "username", getUsername(session),
                    "onlineCount", sessions.size()
                );
                broadcast(documentId, new TextMessage(JSONUtil.toJsonStr(leaveMessage)), null);
                broadcastOnlineCount(documentId);
                
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

    private void broadcastOnlineCount(String documentId) {
        CopyOnWriteArraySet<WebSocketSession> sessions = documentSessions.get(documentId);
        if (sessions == null) {
            return;
        }

        Map<String, Object> payload = new HashMap<>();
        payload.put("type", "ONLINE_COUNT");
        payload.put("onlineCount", sessions.size());
        broadcast(documentId, new TextMessage(JSONUtil.toJsonStr(payload)), null);
    }

    private String getDocumentId(WebSocketSession session) {
        String path = session.getUri().getPath();
        String[] parts = path.split("/");
        return parts.length > 0 ? parts[parts.length - 1] : null;
    }

    private String getUserId(WebSocketSession session) {
        String userId = getQueryParam(session, "userId");
        return userId != null ? userId : session.getId();
    }

    private String getUsername(WebSocketSession session) {
        String username = getQueryParam(session, "username");
        return username != null ? username : "匿名协作者";
    }

    private String getQueryParam(WebSocketSession session, String key) {
        if (session.getUri() == null || session.getUri().getQuery() == null) {
            return null;
        }
        String[] pairs = session.getUri().getQuery().split("&");
        for (String pair : pairs) {
            String[] parts = pair.split("=", 2);
            if (parts.length == 2 && key.equals(parts[0])) {
                return URLDecoder.decode(parts[1], StandardCharsets.UTF_8);
            }
        }
        return null;
    }
}


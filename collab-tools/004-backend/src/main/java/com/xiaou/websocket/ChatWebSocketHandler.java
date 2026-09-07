package com.xiaou.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaou.entity.ChatMessage;
import com.xiaou.entity.Notification;
import com.xiaou.service.ChatMessageService;
import com.xiaou.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
public class ChatWebSocketHandler {
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    @Autowired
    private ChatMessageService chatMessageService;
    
    @Autowired
    private NotificationService notificationService;
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MessageMapping("/chat.send")
    public void sendMessage(@Payload Map<String, Object> payload, SimpMessageHeaderAccessor headerAccessor) {
        try {
            String type = (String) payload.get("type");
            Long fromUserId = Long.parseLong(payload.get("fromUserId").toString());
            Long toUserId = Long.parseLong(payload.get("toUserId").toString());
            String content = (String) payload.get("content");
            
            if ("CHAT".equals(type)) {
                ChatMessage message = new ChatMessage();
                message.setFromUserId(fromUserId);
                message.setToUserId(toUserId);
                message.setContent(content);
                message.setMsgType("TEXT");
                message.setIsRead(0);
                chatMessageService.save(message);
                
                Boolean isOnline = redisTemplate.hasKey("user:online:" + toUserId);
                if (Boolean.TRUE.equals(isOnline)) {
                    messagingTemplate.convertAndSendToUser(
                        toUserId.toString(),
                        "/queue/messages",
                        message
                    );
                }
            } else if ("NOTIFICATION".equals(type)) {
                Notification notification = new Notification();
                notification.setFromUserId(fromUserId);
                notification.setToUserId(toUserId);
                notification.setNotifyType((String) payload.get("notifyType"));
                notification.setTitle((String) payload.get("title"));
                notification.setContent(content);
                notification.setIsRead(0);
                if (payload.get("linkId") != null) {
                    notification.setLinkId(Long.parseLong(payload.get("linkId").toString()));
                }
                notificationService.save(notification);
                
                Boolean isOnline = redisTemplate.hasKey("user:online:" + toUserId);
                if (Boolean.TRUE.equals(isOnline)) {
                    messagingTemplate.convertAndSendToUser(
                        toUserId.toString(),
                        "/queue/notifications",
                        notification
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @MessageMapping("/user.connect")
    public void userConnect(@Payload Map<String, Object> payload) {
        try {
            Long userId = Long.parseLong(payload.get("userId").toString());
            redisTemplate.opsForValue().set("user:online:" + userId, "1", 24, TimeUnit.HOURS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @MessageMapping("/user.disconnect")
    public void userDisconnect(@Payload Map<String, Object> payload) {
        try {
            Long userId = Long.parseLong(payload.get("userId").toString());
            redisTemplate.delete("user:online:" + userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


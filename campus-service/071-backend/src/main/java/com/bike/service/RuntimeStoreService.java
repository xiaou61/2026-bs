package com.bike.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class RuntimeStoreService {

    private static final String TOKEN_PREFIX = "user:token:";

    private final Map<Long, String> localTokens = new ConcurrentHashMap<>();

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${app.redis.enabled:false}")
    private boolean redisEnabled;

    public void saveToken(Long userId, String token, long timeout, TimeUnit unit) {
        localTokens.put(userId, token);
        if (redisEnabled) {
            redisTemplate.opsForValue().set(TOKEN_PREFIX + userId, token, timeout, unit);
        }
    }

    public boolean isTokenActive(Long userId, String token) {
        String activeToken = localTokens.get(userId);
        if (activeToken == null && redisEnabled) {
            Object redisToken = redisTemplate.opsForValue().get(TOKEN_PREFIX + userId);
            activeToken = redisToken == null ? null : String.valueOf(redisToken);
        }
        return token != null && token.equals(activeToken);
    }

    public void removeToken(Long userId) {
        localTokens.remove(userId);
        if (redisEnabled) {
            redisTemplate.delete(TOKEN_PREFIX + userId);
        }
    }
}

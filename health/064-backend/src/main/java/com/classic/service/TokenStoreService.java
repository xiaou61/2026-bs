package com.classic.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class TokenStoreService {

    private final Map<Long, String> localTokens = new ConcurrentHashMap<>();

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${app.redis.enabled:true}")
    private boolean redisEnabled;

    public void storeToken(Long userId, String token) {
        localTokens.put(userId, token);
        if (!redisEnabled) {
            return;
        }
        try {
            redisTemplate.opsForValue().set(redisKey(userId), token, 24, TimeUnit.HOURS);
        } catch (Exception ignored) {
        }
    }

    public boolean isTokenValid(Long userId, String token) {
        if (userId == null || token == null || token.isEmpty()) {
            return false;
        }
        if (!redisEnabled) {
            return token.equals(localTokens.get(userId));
        }
        try {
            Object cacheToken = redisTemplate.opsForValue().get(redisKey(userId));
            if (cacheToken != null) {
                return token.equals(cacheToken.toString());
            }
        } catch (Exception ignored) {
        }
        return token.equals(localTokens.get(userId));
    }

    public void deleteToken(Long userId) {
        localTokens.remove(userId);
        if (!redisEnabled) {
            return;
        }
        try {
            redisTemplate.delete(redisKey(userId));
        } catch (Exception ignored) {
        }
    }

    private String redisKey(Long userId) {
        return "classic:token:" + userId;
    }
}

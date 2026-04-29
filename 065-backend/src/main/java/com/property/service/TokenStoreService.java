package com.property.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class TokenStoreService {

    private static final String KEY_PREFIX = "property:token:";
    private final Map<Long, String> localTokenStore = new ConcurrentHashMap<>();

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${app.redis.enabled:false}")
    private boolean redisEnabled;

    public void storeToken(Long userId, String token) {
        localTokenStore.put(userId, token);
        if (redisEnabled) {
            redisTemplate.opsForValue().set(KEY_PREFIX + userId, token, 24, TimeUnit.HOURS);
        }
    }

    public boolean matches(Long userId, String token) {
        String localToken = localTokenStore.get(userId);
        if (token.equals(localToken)) {
            return true;
        }
        if (!redisEnabled) {
            return false;
        }
        Object cacheToken = redisTemplate.opsForValue().get(KEY_PREFIX + userId);
        return cacheToken != null && token.equals(cacheToken.toString());
    }

    public void deleteToken(Long userId) {
        localTokenStore.remove(userId);
        if (redisEnabled) {
            redisTemplate.delete(KEY_PREFIX + userId);
        }
    }
}

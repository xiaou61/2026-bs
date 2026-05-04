package com.recruitmatch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void store(Long userId, String token) {
        redisTemplate.opsForValue().set(key(userId), token, 24, TimeUnit.HOURS);
    }

    public boolean isActive(Long userId, String token) {
        String value = redisTemplate.opsForValue().get(key(userId));
        return token.equals(value);
    }

    public void remove(Long userId) {
        redisTemplate.delete(key(userId));
    }

    private String key(Long userId) {
        return "recruit:token:" + userId;
    }
}

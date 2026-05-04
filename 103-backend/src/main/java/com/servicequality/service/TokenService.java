package com.servicequality.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void store(Long userId, String token) {
        redisTemplate.opsForValue().set("service:token:" + userId, token, 24, TimeUnit.HOURS);
    }

    public boolean isActive(Long userId, String token) {
        String value = redisTemplate.opsForValue().get("service:token:" + userId);
        return token.equals(value);
    }

    public void remove(Long userId) {
        redisTemplate.delete("service:token:" + userId);
    }
}

package com.coldchain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final RedisTemplate<String, String> redisTemplate;

    public void save(String token, String userId) {
        redisTemplate.opsForValue().set(key(token), userId, 24, TimeUnit.HOURS);
    }

    public boolean valid(String token) {
        return token != null && Boolean.TRUE.equals(redisTemplate.hasKey(key(token)));
    }

    public void remove(String token) {
        redisTemplate.delete(key(token));
    }

    private String key(String token) {
        return "coldchain:token:" + token;
    }
}

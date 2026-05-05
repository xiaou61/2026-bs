package com.carbonmanage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenService {
    private static final String PREFIX = "carbonmanage:token:";
    private final StringRedisTemplate redisTemplate;

    public void save(String token, String userId) {
        redisTemplate.opsForValue().set(PREFIX + token, userId, 24, TimeUnit.HOURS);
    }

    public boolean valid(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(PREFIX + token));
    }

    public void remove(String token) {
        redisTemplate.delete(PREFIX + token);
    }
}

package com.pethospital.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final StringRedisTemplate redisTemplate;

    public void save(String token, String userId) {
        redisTemplate.opsForValue().set("pethospital:token:" + token, userId, 24, TimeUnit.HOURS);
    }

    public boolean exists(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey("pethospital:token:" + token));
    }

    public void remove(String token) {
        redisTemplate.delete("pethospital:token:" + token);
    }
}

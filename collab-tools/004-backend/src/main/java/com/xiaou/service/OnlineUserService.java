package com.xiaou.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class OnlineUserService {
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    public void setOnline(Long userId) {
        redisTemplate.opsForValue().set("user:online:" + userId, "1", 24, TimeUnit.HOURS);
    }
    
    public void setOffline(Long userId) {
        redisTemplate.delete("user:online:" + userId);
    }
    
    public boolean isOnline(Long userId) {
        return Boolean.TRUE.equals(redisTemplate.hasKey("user:online:" + userId));
    }
}


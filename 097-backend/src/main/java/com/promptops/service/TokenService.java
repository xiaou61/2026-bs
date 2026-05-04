package com.promptops.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenService {
    private final Map<Long, String> tokens = new ConcurrentHashMap<>();

    public void store(Long userId, String token) {
        tokens.put(userId, token);
    }

    public boolean isActive(Long userId, String token) {
        return token != null && token.equals(tokens.get(userId));
    }

    public void remove(Long userId) {
        tokens.remove(userId);
    }
}

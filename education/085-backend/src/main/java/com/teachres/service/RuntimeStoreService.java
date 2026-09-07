package com.teachres.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RuntimeStoreService {

    private final Map<Long, String> tokens = new ConcurrentHashMap<>();

    public void saveToken(Long userId, String token) {
        tokens.put(userId, token);
    }

    public boolean isTokenActive(Long userId, String token) {
        String cached = tokens.get(userId);
        return cached != null && cached.equals(token);
    }

    public void removeToken(Long userId) {
        tokens.remove(userId);
    }
}

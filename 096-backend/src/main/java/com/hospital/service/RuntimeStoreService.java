package com.hospital.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RuntimeStoreService {
    private final Map<Long, String> tokens = new ConcurrentHashMap<>();

    public void storeToken(Long userId, String token) {
        tokens.put(userId, token);
    }

    public boolean isActiveToken(Long userId, String token) {
        return token != null && token.equals(tokens.get(userId));
    }

    public void removeToken(Long userId) {
        tokens.remove(userId);
    }
}

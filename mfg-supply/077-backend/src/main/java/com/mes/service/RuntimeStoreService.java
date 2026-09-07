package com.mes.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RuntimeStoreService {

    private final Map<Long, String> activeTokens = new ConcurrentHashMap<>();

    public void storeToken(Long userId, String token) {
        activeTokens.put(userId, token);
    }

    public boolean isActiveToken(Long userId, String token) {
        return token != null && token.equals(activeTokens.get(userId));
    }

    public void invalidateToken(Long userId) {
        activeTokens.remove(userId);
    }
}

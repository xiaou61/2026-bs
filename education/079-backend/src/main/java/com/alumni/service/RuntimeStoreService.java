package com.alumni.service;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RuntimeStoreService {

    private final Set<String> activeTokens = ConcurrentHashMap.newKeySet();

    public void activateToken(String token) {
        activeTokens.add(token);
    }

    public boolean isTokenActive(String token) {
        return activeTokens.contains(token);
    }

    public void invalidateToken(String token) {
        if (token != null) {
            activeTokens.remove(token);
        }
    }
}

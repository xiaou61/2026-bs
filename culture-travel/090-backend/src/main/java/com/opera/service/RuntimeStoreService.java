package com.opera.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RuntimeStoreService {

    private final Map<Long, String> tokenStore = new ConcurrentHashMap<>();

    public void storeToken(Long userId, String token) {
        if (userId != null && token != null) {
            tokenStore.put(userId, token);
        }
    }

    public boolean isValidToken(Long userId, String token) {
        return userId != null && token != null && token.equals(tokenStore.get(userId));
    }

    public void removeToken(Long userId) {
        if (userId != null) {
            tokenStore.remove(userId);
        }
    }
}

package com.petcafe.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RuntimeStoreService {

    private final Map<String, String> tokenStore = new ConcurrentHashMap<>();

    public void storeToken(Long userId, String token) {
        if (userId != null && token != null) {
            tokenStore.put(tokenKey(userId), token);
        }
    }

    public String getToken(Long userId) {
        return userId == null ? null : tokenStore.get(tokenKey(userId));
    }

    public void removeToken(Long userId) {
        if (userId != null) {
            tokenStore.remove(tokenKey(userId));
        }
    }

    private String tokenKey(Long userId) {
        return "petcafe:token:" + userId;
    }
}

package com.teachres.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RuntimeStoreService {
    private final Map<String, String> userTokens = new ConcurrentHashMap<>();
    private final Set<String> invalidTokens = ConcurrentHashMap.newKeySet();

    public void storeUserToken(Long userId, String token) {
        if (userId != null && token != null && !token.isEmpty()) {
            userTokens.put(String.valueOf(userId), token);
        }
    }

    public boolean matchesUserToken(String userId, String token) {
        if (userId == null || token == null || token.isEmpty()) {
            return false;
        }
        return token.equals(userTokens.get(userId));
    }

    public String removeUserToken(Long userId) {
        if (userId == null) {
            return null;
        }
        return userTokens.remove(String.valueOf(userId));
    }

    public void invalidateToken(String token) {
        if (token != null && !token.isEmpty()) {
            invalidTokens.add(token);
        }
    }

    public boolean isTokenInvalid(String token) {
        return token != null && invalidTokens.contains(token);
    }
}

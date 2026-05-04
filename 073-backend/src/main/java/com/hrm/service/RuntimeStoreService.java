package com.hrm.service;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RuntimeStoreService {

    private final Set<String> invalidTokens = ConcurrentHashMap.newKeySet();

    public void invalidateToken(String token) {
        if (token != null && !token.isBlank()) {
            invalidTokens.add(token);
        }
    }

    public boolean isTokenInvalid(String token) {
        return token != null && invalidTokens.contains(token);
    }
}

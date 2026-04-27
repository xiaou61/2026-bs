package com.security.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

@Service
public class LocalCacheService {

    private final ConcurrentMap<String, CacheValue> cache = new ConcurrentHashMap<>();

    public Object get(String key) {
        CacheValue cacheValue = cache.get(key);
        if (cacheValue == null) {
            return null;
        }
        if (cacheValue.isExpired()) {
            cache.remove(key);
            return null;
        }
        return cacheValue.value;
    }

    public boolean hasKey(String key) {
        return get(key) != null;
    }

    public void set(String key, Object value, long timeout, TimeUnit unit) {
        long expiresAt = System.currentTimeMillis() + unit.toMillis(timeout);
        cache.put(key, new CacheValue(value, expiresAt));
    }

    private static class CacheValue {
        private final Object value;
        private final long expiresAt;

        private CacheValue(Object value, long expiresAt) {
            this.value = value;
            this.expiresAt = expiresAt;
        }

        private boolean isExpired() {
            return System.currentTimeMillis() > expiresAt;
        }
    }
}

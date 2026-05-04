package com.railway.service;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RuntimeStoreService {

    private static final long SEAT_LOCK_EXPIRE_MILLIS = 15 * 60 * 1000L;

    private final Map<Long, String> tokenStore = new ConcurrentHashMap<>();
    private final Map<String, ExpiringValue> seatLockStore = new ConcurrentHashMap<>();

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

    public void storeSeatLock(String key, String userId) {
        if (key != null && userId != null) {
            seatLockStore.put(key, new ExpiringValue(userId, Instant.now().toEpochMilli() + SEAT_LOCK_EXPIRE_MILLIS));
        }
    }

    public String getSeatLocker(String key) {
        if (key == null) {
            return null;
        }
        ExpiringValue value = seatLockStore.get(key);
        if (value == null) {
            return null;
        }
        if (value.expireAt < Instant.now().toEpochMilli()) {
            seatLockStore.remove(key);
            return null;
        }
        return value.value;
    }

    public void removeSeatLock(String key) {
        if (key != null) {
            seatLockStore.remove(key);
        }
    }

    private record ExpiringValue(String value, long expireAt) {}
}

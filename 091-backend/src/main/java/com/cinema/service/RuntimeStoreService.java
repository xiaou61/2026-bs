package com.cinema.service;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RuntimeStoreService {

    private final Map<Long, String> tokenStore = new ConcurrentHashMap<>();
    private final Map<String, LockRecord> seatLockStore = new ConcurrentHashMap<>();

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

    public Long getSeatLocker(String key) {
        LockRecord record = seatLockStore.get(key);
        if (record == null) {
            return null;
        }
        if (record.expiresAt() <= Instant.now().toEpochMilli()) {
            seatLockStore.remove(key, record);
            return null;
        }
        return record.userId();
    }

    public void lockSeat(String key, Long userId, long ttlMillis) {
        if (key != null && userId != null) {
            seatLockStore.put(key, new LockRecord(userId, Instant.now().toEpochMilli() + ttlMillis));
        }
    }

    public void unlockSeat(String key) {
        if (key != null) {
            seatLockStore.remove(key);
        }
    }

    private record LockRecord(Long userId, long expiresAt) {}
}

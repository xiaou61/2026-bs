package com.ticket.service;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class RuntimeStoreService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final boolean redisEnabled;
    private final Map<Long, String> localTokens = new ConcurrentHashMap<>();
    private final Map<String, ExpiringValue> localSeatLocks = new ConcurrentHashMap<>();

    public RuntimeStoreService(ObjectProvider<RedisTemplate<String, Object>> redisTemplateProvider,
                               @Value("${app.redis.enabled:false}") boolean redisEnabled) {
        this.redisTemplate = redisTemplateProvider.getIfAvailable();
        this.redisEnabled = redisEnabled;
    }

    public void storeToken(Long userId, String token) {
        if (useRedis()) {
            redisTemplate.opsForValue().set(tokenKey(userId), token, 24, TimeUnit.HOURS);
            return;
        }
        localTokens.put(userId, token);
    }

    public boolean isTokenValid(Long userId, String token) {
        Object cacheToken = useRedis() ? redisTemplate.opsForValue().get(tokenKey(userId)) : localTokens.get(userId);
        return cacheToken != null && Objects.equals(String.valueOf(cacheToken), token);
    }

    public void deleteToken(Long userId) {
        if (useRedis()) {
            redisTemplate.delete(tokenKey(userId));
            return;
        }
        localTokens.remove(userId);
    }

    public void lockSeat(Long userId, Long showtimeId, Long seatId, Duration ttl) {
        String key = seatLockKey(showtimeId, seatId);
        if (useRedis()) {
            redisTemplate.opsForValue().set(key, userId, ttl.toMillis(), TimeUnit.MILLISECONDS);
            return;
        }
        localSeatLocks.put(key, new ExpiringValue(String.valueOf(userId), System.currentTimeMillis() + ttl.toMillis()));
    }

    public String getSeatLocker(Long showtimeId, Long seatId) {
        String key = seatLockKey(showtimeId, seatId);
        if (useRedis()) {
            Object locker = redisTemplate.opsForValue().get(key);
            return locker == null ? null : String.valueOf(locker);
        }
        ExpiringValue value = localSeatLocks.get(key);
        if (value == null) {
            return null;
        }
        if (value.expiresAt() <= System.currentTimeMillis()) {
            localSeatLocks.remove(key);
            return null;
        }
        return value.value();
    }

    public void deleteSeatLock(Long showtimeId, Long seatId) {
        String key = seatLockKey(showtimeId, seatId);
        if (useRedis()) {
            redisTemplate.delete(key);
            return;
        }
        localSeatLocks.remove(key);
    }

    private boolean useRedis() {
        return redisEnabled && redisTemplate != null;
    }

    private String tokenKey(Long userId) {
        return "ticket:token:" + userId;
    }

    private String seatLockKey(Long showtimeId, Long seatId) {
        return "ticket:seat:lock:" + showtimeId + ":" + seatId;
    }

    private record ExpiringValue(String value, long expiresAt) {
    }
}

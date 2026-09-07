package com.xiaou.health.common;

public class Constants {
    public static final String JWT_SECRET = "health_management_platform_jwt_secret_key_2025";
    public static final long JWT_EXPIRATION = 7 * 24 * 60 * 60 * 1000L;
    public static final String REDIS_USER_KEY_PREFIX = "user:";
    
    public static final String ROLE_PATIENT = "PATIENT";
    public static final String ROLE_DOCTOR = "DOCTOR";
    public static final String ROLE_ADMIN = "ADMIN";
}

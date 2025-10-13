package com.xiaou.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expire}")
    private Long expire;

    public String generateToken(Long userId, String role) {
        return JWT.create()
                .withClaim("userId", userId)
                .withClaim("role", role)
                .withExpiresAt(new Date(System.currentTimeMillis() + expire * 1000))
                .sign(Algorithm.HMAC256(secret));
    }

    public DecodedJWT verify(String token) {
        return JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
    }

    public Long getUserId(String token) {
        DecodedJWT jwt = verify(token);
        return jwt.getClaim("userId").asLong();
    }

    public String getRole(String token) {
        DecodedJWT jwt = verify(token);
        return jwt.getClaim("role").asString();
    }
}


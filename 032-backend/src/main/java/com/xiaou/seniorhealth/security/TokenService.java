package com.xiaou.seniorhealth.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.PublicKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Component
public class TokenService {
    @Value("${app.jwt.secret}")
    private String secret;
    public String create(String username, String role, long ttlSeconds) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(username)
                .claims(Map.of("role", role))
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(ttlSeconds)))
                .signWith(key)
                .compact();
    }
    public Map<String, Object> parse(String token) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return Jwts.parser().verifyWith((PublicKey) key).build().parseSignedClaims(token).getPayload();
    }
}

package com.smartwarehouse.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expire:86400000}")
    private long expire;

    private Key secretKey;

    @PostConstruct
    public void init() {
        if (secret == null || secret.length() < 32) {
            throw new IllegalStateException("JWT 密钥长度不能小于 32 位，请在配置中设置足够强度的 jwt.secret");
        }
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());
    }

    public String generateToken(Long userId, String username, String role) {
        return Jwts.builder().setSubject(String.valueOf(userId)).claim("username", username).claim("role", role).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + expire)).signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    public Claims parse(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
}

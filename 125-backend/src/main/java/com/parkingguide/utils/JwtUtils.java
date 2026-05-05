package com.parkingguide.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtils {
    private static final String SECRET = "smart-parking-guide-platform-secret";
    private static final long EXPIRE = 24 * 60 * 60 * 1000L;

    public static String generateToken(Long userId, String username, String role) {
        return Jwts.builder().setSubject(String.valueOf(userId)).claim("username", username).claim("role", role).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + EXPIRE)).signWith(SignatureAlgorithm.HS256, SECRET).compact();
    }

    public static Claims parse(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }
}

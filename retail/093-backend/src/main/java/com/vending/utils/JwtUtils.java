package com.vending.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public final class JwtUtils {

    private static final String SECRET = "vending-management-system-093-secret";
    private static final long EXPIRE = 24 * 60 * 60 * 1000L;

    private JwtUtils() {
    }

    public static String generateToken(Long userId, String role) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("role", role)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public static Long getUserId(String token) {
        return Long.parseLong(getClaims(token).getSubject());
    }

    public static String getRole(String token) {
        Object role = getClaims(token).get("role");
        return role == null ? null : role.toString();
    }

    public static boolean validateToken(String token) {
        try {
            return getClaims(token).getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private static Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }
}

package com.adoption.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class JwtUtilsTest {

    @Test
    void shouldGenerateAndParseToken() {
        JwtUtils jwtUtils = new JwtUtils();
        ReflectionTestUtils.setField(jwtUtils, "secret", "test-secret");
        ReflectionTestUtils.setField(jwtUtils, "expiration", 60000L);
        String token = jwtUtils.generateToken(1L, "admin");
        Assertions.assertTrue(jwtUtils.validateToken(token));
        Assertions.assertEquals(Long.valueOf(1L), jwtUtils.getUserId(token));
        Assertions.assertEquals("admin", jwtUtils.getRole(token));
    }
}

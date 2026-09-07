package com.xiaou.confession;

import com.xiaou.confession.interceptor.JwtInterceptor;
import com.xiaou.confession.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtInterceptorTest {

    @Mock
    private JwtUtil jwtUtil;

    @Test
    void shouldAllowPublicPostListWithoutToken() throws Exception {
        JwtInterceptor interceptor = new JwtInterceptor(jwtUtil);
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/posts");
        MockHttpServletResponse response = new MockHttpServletResponse();

        boolean passed = interceptor.preHandle(request, response, new Object());

        assertTrue(passed);
        assertEquals(200, response.getStatus());
    }

    @Test
    void shouldRejectProtectedEndpointWithoutToken() throws Exception {
        JwtInterceptor interceptor = new JwtInterceptor(jwtUtil);
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/posts/my");
        MockHttpServletResponse response = new MockHttpServletResponse();

        boolean passed = interceptor.preHandle(request, response, new Object());

        assertFalse(passed);
        assertEquals(401, response.getStatus());
    }

    @Test
    void shouldRejectAdminEndpointForUserToken() throws Exception {
        JwtInterceptor interceptor = new JwtInterceptor(jwtUtil);
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/admin/users");
        request.addHeader("Authorization", "Bearer user-token");
        MockHttpServletResponse response = new MockHttpServletResponse();

        when(jwtUtil.validateToken("user-token")).thenReturn(true);
        when(jwtUtil.getUserIdFromToken("user-token")).thenReturn(1L);
        when(jwtUtil.getUserTypeFromToken("user-token")).thenReturn("USER");

        boolean passed = interceptor.preHandle(request, response, new Object());

        assertFalse(passed);
        assertEquals(403, response.getStatus());
    }

    @Test
    void shouldAllowAdminEndpointForAdminToken() throws Exception {
        JwtInterceptor interceptor = new JwtInterceptor(jwtUtil);
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/admin/users");
        request.addHeader("Authorization", "Bearer admin-token");
        MockHttpServletResponse response = new MockHttpServletResponse();

        when(jwtUtil.validateToken("admin-token")).thenReturn(true);
        when(jwtUtil.getUserIdFromToken("admin-token")).thenReturn(99L);
        when(jwtUtil.getUserTypeFromToken("admin-token")).thenReturn("ADMIN");

        boolean passed = interceptor.preHandle(request, response, new Object());

        assertTrue(passed);
        assertEquals(99L, request.getAttribute("userId"));
        assertEquals("ADMIN", request.getAttribute("userType"));
    }
}

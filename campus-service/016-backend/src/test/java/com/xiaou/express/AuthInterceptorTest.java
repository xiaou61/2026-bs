package com.xiaou.express;

import com.xiaou.express.interceptor.AuthInterceptor;
import com.xiaou.express.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthInterceptorTest {

    private AuthInterceptor authInterceptor;

    @Mock
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        authInterceptor = new AuthInterceptor();
        ReflectionTestUtils.setField(authInterceptor, "jwtUtil", jwtUtil);
    }

    @Test
    void shouldRejectUserTokenForAdminApi() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/admin/users");
        request.addHeader("Authorization", "Bearer user-token");
        MockHttpServletResponse response = new MockHttpServletResponse();

        when(jwtUtil.getUserId("user-token")).thenReturn(1L);
        when(jwtUtil.getUserType("user-token")).thenReturn("user");

        boolean allowed = authInterceptor.preHandle(request, response, new Object());

        assertFalse(allowed);
        assertTrue(response.getContentAsString().contains("无管理员权限"));
    }

    @Test
    void shouldAllowAdminTokenForAdminApi() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/admin/users");
        request.addHeader("Authorization", "Bearer admin-token");
        MockHttpServletResponse response = new MockHttpServletResponse();

        when(jwtUtil.getUserId("admin-token")).thenReturn(1L);
        when(jwtUtil.getUserType("admin-token")).thenReturn("admin");

        boolean allowed = authInterceptor.preHandle(request, response, new Object());

        assertTrue(allowed);
    }
}

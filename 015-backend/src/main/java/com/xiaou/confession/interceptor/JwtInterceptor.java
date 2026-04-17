package com.xiaou.confession.interceptor;

import com.xiaou.confession.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {
    private static final Pattern PUBLIC_POST_DETAIL_PATTERN = Pattern.compile("^/api/posts/\\d+$");
    private static final Pattern PUBLIC_POST_COMMENTS_COMPAT_PATTERN = Pattern.compile("^/api/posts/\\d+/comments$");
    private static final Pattern PUBLIC_POST_COMMENTS_PATTERN = Pattern.compile("^/api/comments/post/\\d+$");
    private static final Pattern PUBLIC_COMMENT_REPLIES_PATTERN = Pattern.compile("^/api/comments/replies/\\d+$");

    private final JwtUtil jwtUtil;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String requestUri = request.getRequestURI();
        if (isPublicEndpoint(request.getMethod(), requestUri)) {
            return true;
        }

        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        if (token == null || !jwtUtil.validateToken(token)) {
            writeJson(response, 401, "{\"code\":401,\"message\":\"未登录或token已过期\"}");
            return false;
        }

        Long userId = jwtUtil.getUserIdFromToken(token);
        String userType = jwtUtil.getUserTypeFromToken(token);

        if (requestUri.startsWith("/api/admin") && !"ADMIN".equals(userType)) {
            writeJson(response, 403, "{\"code\":403,\"message\":\"无权访问管理员接口\"}");
            return false;
        }

        request.setAttribute("userId", userId);
        request.setAttribute("userType", userType);

        return true;
    }

    private boolean isPublicEndpoint(String method, String requestUri) {
        if (!"GET".equalsIgnoreCase(method)) {
            return false;
        }
        return "/api/posts".equals(requestUri)
                || "/api/posts/search".equals(requestUri)
                || PUBLIC_POST_DETAIL_PATTERN.matcher(requestUri).matches()
                || PUBLIC_POST_COMMENTS_COMPAT_PATTERN.matcher(requestUri).matches()
                || PUBLIC_POST_COMMENTS_PATTERN.matcher(requestUri).matches()
                || PUBLIC_COMMENT_REPLIES_PATTERN.matcher(requestUri).matches();
    }

    private void writeJson(HttpServletResponse response, int status, String body) throws Exception {
        response.setStatus(status);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(body);
    }
}


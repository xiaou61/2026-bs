package com.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LiteBlogApplicationSmokeTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void blogMainFlowsShouldWorkWithDefaultH2Profile() {
        assertSuccess(restTemplate.getForEntity("/api/post/public-page", Map.class));
        assertSuccess(restTemplate.getForEntity("/api/post/detail/1", Map.class));
        assertSuccess(restTemplate.getForEntity("/api/comment/post-page?postId=1", Map.class));
        assertSuccess(restTemplate.getForEntity("/api/notice/list", Map.class));

        ResponseEntity<Map> unauthenticated = restTemplate.getForEntity("/api/dashboard/stats", Map.class);
        assertThat(unauthenticated.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

        LoginSession admin = login("admin", "123456", "ADMIN", "博客管理员");
        LoginSession user = login("user", "123456", "USER", "普通用户");

        ResponseEntity<Map> forbidden = exchange("/api/user/page", HttpMethod.GET, null, user.token);
        assertThat(forbidden.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

        assertSuccess(exchange("/api/user/page", HttpMethod.GET, null, admin.token));
        assertSuccess(exchange("/api/category/list", HttpMethod.GET, null, user.token));
        assertSuccess(exchange("/api/tag/list", HttpMethod.GET, null, user.token));
        assertSuccess(exchange("/api/dashboard/stats", HttpMethod.GET, null, admin.token));

        assertSuccess(exchange("/api/notice", HttpMethod.POST, Map.of(
                "title", "冒烟测试公告",
                "content", "默认 H2 自举环境可正常发布博客公告",
                "status", 1
        ), admin.token));

        assertSuccess(exchange("/api/post", HttpMethod.POST, Map.of(
                "title", "冒烟测试文章",
                "summary", "默认 H2 自举环境下的文章发布验证",
                "content", "这是一篇由冒烟测试创建的博客文章。",
                "categoryId", 1,
                "status", 1,
                "tagIds", List.of(1, 2)
        ), user.token));
        Map<String, Object> myPosts = data(assertSuccess(exchange("/api/post/page?status=1", HttpMethod.GET, null, user.token)));
        Long postId = firstId(myPosts);

        assertSuccess(exchange("/api/post/top", HttpMethod.PUT, Map.of(
                "id", postId,
                "isTop", 1
        ), admin.token));
        assertSuccess(restTemplate.getForEntity("/api/post/detail/" + postId, Map.class));

        assertSuccess(exchange("/api/comment", HttpMethod.POST, Map.of(
                "postId", postId,
                "content", "冒烟测试评论内容"
        ), user.token));
        Map<String, Object> pendingComments = data(assertSuccess(exchange("/api/comment/page?status=0", HttpMethod.GET, null, admin.token)));
        Long commentId = firstId(pendingComments);
        assertSuccess(exchange("/api/comment/review", HttpMethod.PUT, Map.of(
                "id", commentId,
                "status", 1,
                "replyContent", "冒烟测试审核通过"
        ), admin.token));
        assertSuccess(restTemplate.getForEntity("/api/comment/post-page?postId=" + postId, Map.class));
        assertSuccess(exchange("/api/dashboard/trend", HttpMethod.GET, null, admin.token));

        assertSuccess(exchange("/api/auth/logout", HttpMethod.POST, null, user.token));
        ResponseEntity<Map> afterLogout = exchange("/api/auth/info", HttpMethod.GET, null, user.token);
        assertThat(afterLogout.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    private LoginSession login(String username, String password, String role, String nickname) {
        ResponseEntity<Map> response = restTemplate.postForEntity("/api/auth/login", Map.of(
                "username", username,
                "password", password
        ), Map.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Map<String, Object> body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.get("code")).isEqualTo(200);

        Map<String, Object> data = data(response);
        assertThat(data.get("token")).isInstanceOf(String.class);
        Map<String, Object> user = castMap(data.get("user"));
        assertThat(user.get("role")).isEqualTo(role);
        assertThat(user.get("nickname")).isEqualTo(nickname);
        assertThat(user).doesNotContainKey("password");
        return new LoginSession((String) data.get("token"));
    }

    private ResponseEntity<Map> exchange(String path, HttpMethod method, Object body, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return restTemplate.exchange(path, method, new HttpEntity<>(body, headers), Map.class);
    }

    private ResponseEntity<Map> assertSuccess(ResponseEntity<Map> response) {
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().get("code")).isEqualTo(200);
        return response;
    }

    private Map<String, Object> data(ResponseEntity<Map> response) {
        return castMap(response.getBody().get("data"));
    }

    private Long firstId(Map<String, Object> page) {
        List<?> records = (List<?>) page.get("records");
        assertThat(records).isNotEmpty();
        Map<String, Object> first = castMap(records.get(0));
        return ((Number) first.get("id")).longValue();
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> castMap(Object value) {
        return (Map<String, Object>) value;
    }

    private static class LoginSession {
        private final String token;

        private LoginSession(String token) {
            this.token = token;
        }
    }
}

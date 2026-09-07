package com.xiaou;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Map;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "debug=false")
@AutoConfigureMockMvc
class KaoyanStudyApplicationSmokeTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldStartWithDemoDataAndSupportCoreStudyFlows() throws Exception {
        mockMvc.perform(get("/api/subject/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.length()").value(greaterThanOrEqualTo(8)));

        mockMvc.perform(get("/api/course/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.records.length()").value(greaterThanOrEqualTo(4)))
                .andExpect(jsonPath("$.data.records[0].teacherName", notNullValue()));

        mockMvc.perform(get("/api/question/list").param("subjectId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.records.length()").value(greaterThanOrEqualTo(2)))
                .andExpect(jsonPath("$.data.records[0].optionA", notNullValue()));

        String adminToken = extractToken(login("admin", "123456")
                .andExpect(jsonPath("$.data.user.role").value(2))
                .andExpect(jsonPath("$.data.user.password").doesNotExist())
                .andReturn());

        mockMvc.perform(get("/api/admin/dashboard").header("Authorization", bearer(adminToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.userCount").value(greaterThanOrEqualTo(5)))
                .andExpect(jsonPath("$.data.courseCount").value(greaterThanOrEqualTo(4)));

        String studentToken = extractToken(login("student1", "123456")
                .andExpect(jsonPath("$.data.user.role").value(0))
                .andReturn());

        mockMvc.perform(get("/api/user/info").header("Authorization", bearer(studentToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.username").value("student1"))
                .andExpect(jsonPath("$.data.password").doesNotExist());

        mockMvc.perform(post("/api/question/submit")
                        .header("Authorization", bearer(studentToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "questionId", 1,
                                "userAnswer", "B",
                                "timeSpent", 12
                        ))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.isCorrect").value(false))
                .andExpect(jsonPath("$.data.correctAnswer").value("A"));

        mockMvc.perform(get("/api/question/wrong").header("Authorization", bearer(studentToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.records.length()").value(greaterThanOrEqualTo(1)));

        mockMvc.perform(post("/api/study/plan")
                        .header("Authorization", bearer(studentToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "title", "自动化巡检学习计划",
                                "targetContent", "完成一轮政治复习",
                                "subjectId", 1,
                                "startDate", LocalDate.now().toString(),
                                "endDate", LocalDate.now().plusDays(30).toString(),
                                "dailyHours", 2,
                                "progress", 10
                        ))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        Long planId = extractPlanId(studentToken, "自动化巡检学习计划");
        mockMvc.perform(put("/api/study/plan/" + planId + "/progress")
                        .header("Authorization", bearer(studentToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("progress", 100))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        mockMvc.perform(post("/api/note")
                        .header("Authorization", bearer(studentToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "title", "自动化巡检笔记",
                                "content", "验证笔记创建链路",
                                "subjectId", 1,
                                "isPublic", 0
                        ))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        mockMvc.perform(post("/api/favorite")
                        .header("Authorization", bearer(studentToken))
                        .param("targetType", "1")
                        .param("targetId", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        mockMvc.perform(get("/api/favorite/check")
                        .header("Authorization", bearer(studentToken))
                        .param("targetType", "1")
                        .param("targetId", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").value(true));

        mockMvc.perform(post("/api/post")
                        .header("Authorization", bearer(studentToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "title", "自动化巡检帖子",
                                "content", "验证社区发帖链路",
                                "subjectId", 1
                        ))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        Long postId = extractPostId("自动化巡检帖子");
        mockMvc.perform(post("/api/post/" + postId + "/comment")
                        .header("Authorization", bearer(studentToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of("content", "自动化巡检评论"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "username", "auto049",
                                "password", "123456",
                                "nickname", "自动化同学",
                                "targetSchool", "复旦大学",
                                "targetMajor", "软件工程",
                                "examYear", 2026
                        ))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        String newUserToken = extractToken(login("auto049", "123456").andReturn());
        mockMvc.perform(post("/api/study/checkin")
                        .header("Authorization", bearer(newUserToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "studyDuration", 60,
                                "questionCount", 10,
                                "correctCount", 8,
                                "note", "自动化巡检打卡",
                                "mood", 4
                        ))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        mockMvc.perform(get("/api/study/checkin/stats").header("Authorization", bearer(newUserToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.totalDays").value(1));
    }

    private ResultActionsWrapper login(String username, String password) throws Exception {
        return new ResultActionsWrapper(mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Map.of(
                        "username", username,
                        "password", password
                ))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.token").isNotEmpty()));
    }

    private Long extractPlanId(String token, String title) throws Exception {
        MvcResult result = mockMvc.perform(get("/api/study/plans")
                        .header("Authorization", bearer(token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andReturn();
        for (JsonNode plan : readJson(result).path("data")) {
            if (title.equals(plan.path("title").asText())) {
                return plan.path("id").asLong();
            }
        }
        throw new AssertionError("未找到学习计划：" + title);
    }

    private Long extractPostId(String title) throws Exception {
        MvcResult result = mockMvc.perform(get("/api/post/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andReturn();
        for (JsonNode post : readJson(result).path("data").path("records")) {
            if (title.equals(post.path("title").asText())) {
                return post.path("id").asLong();
            }
        }
        throw new AssertionError("未找到帖子：" + title);
    }

    private String extractToken(MvcResult result) throws Exception {
        return readJson(result).path("data").path("token").asText();
    }

    private JsonNode readJson(MvcResult result) throws Exception {
        return objectMapper.readTree(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }

    private String bearer(String token) {
        return "Bearer " + token;
    }

    private record ResultActionsWrapper(org.springframework.test.web.servlet.ResultActions delegate) {
        ResultActionsWrapper andExpect(org.springframework.test.web.servlet.ResultMatcher matcher) throws Exception {
            delegate.andExpect(matcher);
            return this;
        }

        MvcResult andReturn() {
            return delegate.andReturn();
        }
    }
}

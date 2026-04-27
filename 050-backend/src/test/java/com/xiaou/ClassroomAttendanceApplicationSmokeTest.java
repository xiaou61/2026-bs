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
import java.time.LocalDateTime;
import java.util.Map;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "debug=false")
@AutoConfigureMockMvc
class ClassroomAttendanceApplicationSmokeTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldStartWithDemoDataAndSupportCoreAttendanceFlows() throws Exception {
        mockMvc.perform(get("/api/notice/latest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.length()").value(greaterThanOrEqualTo(2)));

        mockMvc.perform(get("/api/semester/current"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.name").value("2025-2026学年第二学期"));

        mockMvc.perform(get("/api/course/page"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.records.length()").value(greaterThanOrEqualTo(4)));

        String adminToken = extractToken(login("admin", "123456")
                .andExpect(jsonPath("$.data.user.role").value(2))
                .andExpect(jsonPath("$.data.user.password").doesNotExist())
                .andReturn());

        mockMvc.perform(get("/api/user/page").header("Authorization", bearer(adminToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.records.length()").value(greaterThanOrEqualTo(9)));

        String teacherToken = extractToken(login("teacher1", "123456")
                .andExpect(jsonPath("$.data.user.role").value(1))
                .andExpect(jsonPath("$.data.user.password").doesNotExist())
                .andReturn());

        mockMvc.perform(get("/api/course/teacher").header("Authorization", bearer(teacherToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.length()").value(greaterThanOrEqualTo(2)));

        String studentToken = extractToken(login("student1", "123456")
                .andExpect(jsonPath("$.data.user.role").value(0))
                .andExpect(jsonPath("$.data.user.password").doesNotExist())
                .andReturn());

        mockMvc.perform(get("/api/user/info").header("Authorization", bearer(studentToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.realName").value("张三"))
                .andExpect(jsonPath("$.data.password").doesNotExist());

        mockMvc.perform(get("/api/course/student").header("Authorization", bearer(studentToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.length()").value(greaterThanOrEqualTo(3)));

        mockMvc.perform(get("/api/attendance/task/active").header("Authorization", bearer(studentToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.length()").value(greaterThanOrEqualTo(1)));

        Long taskId = createAttendanceTask(teacherToken);

        mockMvc.perform(post("/api/attendance/sign")
                        .header("Authorization", bearer(studentToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "taskId", taskId,
                                "deviceInfo", "MockMvc"
                        ))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.status").value(1))
                .andExpect(jsonPath("$.data.signType").value(1));

        mockMvc.perform(get("/api/attendance/record/stats/" + taskId)
                        .header("Authorization", bearer(teacherToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.normal").value(greaterThanOrEqualTo(1)));

        mockMvc.perform(get("/api/attendance/stat/student")
                        .header("Authorization", bearer(studentToken))
                        .param("courseId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.totalCount").value(greaterThanOrEqualTo(3)));

        mockMvc.perform(post("/api/leave/submit")
                        .header("Authorization", bearer(studentToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "courseId", 1,
                                "taskId", taskId,
                                "leaveType", 1,
                                "startTime", LocalDateTime.now().plusDays(1).toString(),
                                "endTime", LocalDateTime.now().plusDays(1).plusHours(2).toString(),
                                "reason", "自动化巡检请假"
                        ))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.status").value(0));

        mockMvc.perform(get("/api/leave/teacher").header("Authorization", bearer(teacherToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.records.length()").value(greaterThanOrEqualTo(1)));

        mockMvc.perform(get("/api/message/unreadCount").header("Authorization", bearer(studentToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").value(greaterThanOrEqualTo(1)));
    }

    private Long createAttendanceTask(String teacherToken) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/attendance/task/create")
                        .header("Authorization", bearer(teacherToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "courseId", 1,
                                "title", "自动化巡检签到",
                                "signType", 1,
                                "duration", 60,
                                "lateMinutes", 30
                        ))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.totalCount").value(3))
                .andExpect(jsonPath("$.data.signedCount").value(0))
                .andReturn();
        return readJson(result).path("data").path("id").asLong();
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

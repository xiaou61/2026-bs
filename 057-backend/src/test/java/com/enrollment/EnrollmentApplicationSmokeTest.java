package com.enrollment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EnrollmentApplicationSmokeTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void coreEnrollmentApisShouldWorkWithH2DataAndJwtAuth() throws Exception {
        mockMvc.perform(get("/api/auth/info"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code", is(401)));

        String superToken = login("admin", "123456");
        String adminToken = login("user", "123456");

        mockMvc.perform(get("/api/admin/page")
                        .header("Authorization", bearer(adminToken)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code", is(403)));

        mockMvc.perform(get("/api/admin/page")
                        .header("Authorization", bearer(superToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)));

        mockMvc.perform(get("/api/stats/dashboard")
                        .header("Authorization", bearer(superToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)));

        mockMvc.perform(get("/api/department/list")
                        .header("Authorization", bearer(adminToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)));

        mockMvc.perform(get("/api/major/page")
                        .param("pageNum", "1")
                        .param("pageSize", "5")
                        .header("Authorization", bearer(superToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)));

        mockMvc.perform(get("/api/plan/page")
                        .param("year", "2026")
                        .header("Authorization", bearer(superToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)));

        mockMvc.perform(post("/api/student")
                        .header("Authorization", bearer(superToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "examNo": "2026990001",
                                  "name": "冒烟考生",
                                  "gender": 1,
                                  "idCard": "110101200812120099",
                                  "birthday": "2008-12-12",
                                  "phone": "13999999999",
                                  "email": "smoke@example.com",
                                  "province": "北京",
                                  "city": "北京",
                                  "address": "测试地址",
                                  "highSchool": "测试中学",
                                  "status": 0
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)));

        mockMvc.perform(put("/api/student/audit/9")
                        .param("status", "1")
                        .header("Authorization", bearer(superToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)));

        mockMvc.perform(post("/api/score")
                        .header("Authorization", bearer(superToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "studentId": 9,
                                  "year": 2026,
                                  "chinese": 120,
                                  "math": 130,
                                  "english": 125,
                                  "comprehensive": 240
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)));

        mockMvc.perform(post("/api/application")
                        .header("Authorization", bearer(superToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "studentId": 9,
                                  "planId": 1,
                                  "firstMajorId": 1,
                                  "secondMajorId": 2,
                                  "adjust": 1,
                                  "status": 0
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)));

        mockMvc.perform(put("/api/application/audit/7")
                        .header("Authorization", bearer(superToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "status": 1,
                                  "remark": "材料完整"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)));

        mockMvc.perform(post("/api/admission")
                        .header("Authorization", bearer(superToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "studentId": 9,
                                  "planId": 1,
                                  "majorId": 1,
                                  "score": 615,
                                  "status": 0
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)));

        mockMvc.perform(put("/api/admission/confirm/6")
                        .header("Authorization", bearer(superToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)));

        mockMvc.perform(get("/api/notice/page")
                        .header("Authorization", bearer(superToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)));
    }

    private String login(String username, String password) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "username": "%s",
                                  "password": "%s"
                                }
                                """.formatted(username, password)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.data.admin.password").doesNotExist())
                .andReturn();
        return objectMapper.readTree(result.getResponse().getContentAsString())
                .get("data").get("token").asText();
    }

    private String bearer(String token) {
        return "Bearer " + token;
    }
}

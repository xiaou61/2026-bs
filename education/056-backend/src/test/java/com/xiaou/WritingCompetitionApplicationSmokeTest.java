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

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WritingCompetitionApplicationSmokeTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void coreWritingCompetitionApisShouldWorkWithH2DataAndJwtAuth() throws Exception {
        mockMvc.perform(get("/api/category/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)));

        mockMvc.perform(get("/api/competition/public/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)));

        mockMvc.perform(get("/api/notice/public/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)));

        mockMvc.perform(get("/api/user/info"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code", is(401)));

        String adminToken = login("admin", "123456");
        String judgeToken = login("judge", "123456");

        MvcResult userLogin = mockMvc.perform(post("/api/auth/wxLogin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "openid": "smoke-openid",
                                  "nickname": "冒烟参赛者",
                                  "avatar": ""
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andExpect(jsonPath("$.data.userInfo.password").doesNotExist())
                .andReturn();
        String userToken = objectMapper.readTree(userLogin.getResponse().getContentAsString())
                .get("data").get("token").asText();

        mockMvc.perform(get("/api/stats/dashboard")
                        .header("Authorization", bearer(userToken)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code", is(403)));

        mockMvc.perform(get("/api/stats/dashboard")
                        .header("Authorization", bearer(adminToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)));

        mockMvc.perform(get("/api/user/list")
                        .header("Authorization", bearer(userToken)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code", is(403)));

        mockMvc.perform(post("/api/work/submit")
                        .header("Authorization", bearer(userToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "competitionId": 1,
                                  "title": "冒烟测试作品",
                                  "content": "%s"
                                }
                                """.formatted("春".repeat(850))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)));

        MvcResult myWorks = mockMvc.perform(get("/api/work/my")
                        .header("Authorization", bearer(userToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)))
                .andReturn();
        JsonNode records = objectMapper.readTree(myWorks.getResponse().getContentAsString())
                .get("data").get("records");
        long workId = records.get(0).get("id").asLong();

        mockMvc.perform(put("/api/work/audit")
                        .header("Authorization", bearer(adminToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id": %d,
                                  "status": 1,
                                  "rejectReason": ""
                                }
                                """.formatted(workId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)));

        mockMvc.perform(get("/api/score/pending")
                        .param("competitionId", "1")
                        .header("Authorization", bearer(judgeToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)));

        mockMvc.perform(post("/api/score/submit")
                        .header("Authorization", bearer(judgeToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "workId": %d,
                                  "scores": [
                                    { "standardId": 1, "score": 28 },
                                    { "standardId": 2, "score": 27 },
                                    { "standardId": 3, "score": 18 },
                                    { "standardId": 4, "score": 18 }
                                  ],
                                  "comment": "结构完整，表达流畅"
                                }
                                """.formatted(workId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)));

        mockMvc.perform(get("/api/score/detail/" + workId)
                        .header("Authorization", bearer(adminToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is(200)));

        mockMvc.perform(post("/api/score-standard/save")
                        .header("Authorization", bearer(adminToken))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "competitionId": 3,
                                  "standards": [
                                    { "name": "立意", "maxScore": 50, "weight": 0.50, "description": "主题与创意" },
                                    { "name": "表达", "maxScore": 50, "weight": 0.50, "description": "文字与结构" }
                                  ]
                                }
                                """))
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
                .andExpect(jsonPath("$.data.userInfo.password").doesNotExist())
                .andReturn();
        return objectMapper.readTree(result.getResponse().getContentAsString())
                .get("data").get("token").asText();
    }

    private String bearer(String token) {
        return "Bearer " + token;
    }
}

package com.alumni;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AlumniApplicationSmokeTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void smokeFlowCoversAuthAuthorizationAndCoreBusiness() throws Exception {
        mockMvc.perform(get("/api/auth/info"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value(401));

        String adminToken = login("admin", "123456", "admin");
        String userToken = login("user", "123456", "alumni");
        String user3Token = login("user3", "123456", "alumni");
        String user4Token = login("user4", "123456", "alumni");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(Map.of("username", "user2", "password", "123456"))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400));

        mockMvc.perform(auth(get("/api/stats/overview"), userToken))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code").value(403));

        mockMvc.perform(auth(get("/api/stats/overview"), adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.alumniCount").value(greaterThan(0)));

        mockMvc.perform(get("/api/news/list")).andExpect(status().isOk());
        mockMvc.perform(get("/api/activity/list")).andExpect(status().isOk());
        mockMvc.perform(get("/api/banner/list")).andExpect(status().isOk());
        mockMvc.perform(get("/api/forum/category/list")).andExpect(status().isOk());
        mockMvc.perform(get("/api/forum/post/list")).andExpect(status().isOk());
        mockMvc.perform(get("/api/job/list")).andExpect(status().isOk());
        mockMvc.perform(get("/api/company/list")).andExpect(status().isOk());
        mockMvc.perform(get("/api/donation/project/list")).andExpect(status().isOk());

        Map<String, Object> alumni = new HashMap<>();
        alumni.put("id", 4);
        alumni.put("studentNo", "safe-user-student-no");
        alumni.put("gradeId", 1);
        alumni.put("classId", 1);
        alumni.put("gender", 1);
        alumni.put("major", "Computer Science");
        alumni.put("company", "Safe Alumni Co");
        alumni.put("position", "Engineer");
        alumni.put("industry", "Internet");
        alumni.put("city", "Hangzhou");
        alumni.put("isContact", 1);
        mockMvc.perform(auth(put("/api/alumni"), userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(alumni)))
                .andExpect(status().isOk());
        mockMvc.perform(auth(get("/api/alumni/my"), userToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userId").value(2))
                .andExpect(jsonPath("$.data.studentNo").value("safe-user-student-no"));

        mockMvc.perform(auth(get("/api/activity/2/signs"), user4Token))
                .andExpect(status().isForbidden());
        mockMvc.perform(auth(get("/api/activity/2/signs"), adminToken))
                .andExpect(status().isOk());
        mockMvc.perform(auth(post("/api/activity/2/sign"), user4Token))
                .andExpect(status().isOk());
        mockMvc.perform(auth(post("/api/activity/2/sign"), user4Token))
                .andExpect(status().isBadRequest());
        mockMvc.perform(auth(delete("/api/activity/2/sign"), user4Token))
                .andExpect(status().isOk());

        Map<String, Object> donation = new HashMap<>();
        donation.put("projectId", 1);
        donation.put("amount", new BigDecimal("123.45"));
        donation.put("message", "smoke donation");
        donation.put("isAnonymous", 0);
        mockMvc.perform(auth(post("/api/donation/donate"), userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(donation)))
                .andExpect(status().isOk());
        mockMvc.perform(auth(get("/api/donation/record/list"), userToken))
                .andExpect(status().isForbidden());
        long donationId = firstId(auth(get("/api/donation/record/list?projectId=1&userId=2"), adminToken));
        mockMvc.perform(auth(put("/api/donation/record/" + donationId + "/confirm"), adminToken))
                .andExpect(status().isOk());

        String suffix = String.valueOf(System.currentTimeMillis());
        String companyName = "Smoke Company " + suffix;
        Map<String, Object> company = new HashMap<>();
        company.put("name", companyName);
        company.put("industry", "Internet");
        company.put("scale", "1-50");
        company.put("address", "Hangzhou");
        company.put("introduction", "Smoke test company");
        mockMvc.perform(auth(post("/api/company"), userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(company)))
                .andExpect(status().isOk());
        long companyId = firstId(get("/api/company/list").param("name", companyName));
        mockMvc.perform(auth(put("/api/company/" + companyId + "/audit"), adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(Map.of("status", 1))))
                .andExpect(status().isOk());
        company.put("id", companyId);
        company.put("name", companyName + " Updated");
        mockMvc.perform(auth(put("/api/company"), user3Token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(company)))
                .andExpect(status().isForbidden());
        mockMvc.perform(auth(put("/api/company"), userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(company)))
                .andExpect(status().isOk());

        String jobTitle = "Smoke Job " + suffix;
        Map<String, Object> job = new HashMap<>();
        job.put("companyId", companyId);
        job.put("title", jobTitle);
        job.put("salary", "10K-20K");
        job.put("city", "Hangzhou");
        job.put("experience", "1-3 years");
        job.put("education", "Bachelor");
        job.put("description", "Smoke job");
        job.put("contact", "13800000001");
        mockMvc.perform(auth(post("/api/job"), userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(job)))
                .andExpect(status().isOk());
        long jobId = firstId(get("/api/job/list").param("title", jobTitle));
        job.put("id", jobId);
        job.put("title", jobTitle + " Updated");
        mockMvc.perform(auth(put("/api/job"), user3Token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(job)))
                .andExpect(status().isForbidden());
        mockMvc.perform(auth(put("/api/job"), userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(job)))
                .andExpect(status().isOk());
        mockMvc.perform(auth(put("/api/job/" + jobId + "/audit"), adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(Map.of("status", 1))))
                .andExpect(status().isOk());

        String postTitle = "Smoke Post " + suffix;
        Map<String, Object> post = new HashMap<>();
        post.put("categoryId", 1);
        post.put("title", postTitle);
        post.put("content", "Smoke forum content");
        mockMvc.perform(auth(post("/api/forum/post"), userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(post)))
                .andExpect(status().isOk());
        long postId = firstId(get("/api/forum/post/list").param("title", postTitle));
        mockMvc.perform(auth(delete("/api/forum/post/" + postId), user3Token))
                .andExpect(status().isForbidden());
        mockMvc.perform(auth(post("/api/forum/post/" + postId + "/like"), user3Token))
                .andExpect(status().isOk());
        mockMvc.perform(auth(delete("/api/forum/post/" + postId + "/like"), user3Token))
                .andExpect(status().isOk());
        mockMvc.perform(auth(post("/api/forum/post/" + postId + "/reply"), user3Token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(Map.of("content", "Smoke reply"))))
                .andExpect(status().isOk());
        long replyId = firstReplyId(postId);
        mockMvc.perform(auth(delete("/api/forum/reply/" + replyId), userToken))
                .andExpect(status().isOk());
        mockMvc.perform(auth(delete("/api/forum/post/" + postId), userToken))
                .andExpect(status().isOk());

        mockMvc.perform(auth(post("/api/auth/logout"), adminToken))
                .andExpect(status().isOk());
        mockMvc.perform(auth(get("/api/auth/info"), adminToken))
                .andExpect(status().isUnauthorized());
    }

    private String login(String username, String password, String role) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(Map.of("username", username, "password", password))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.user.role").value(role))
                .andExpect(jsonPath("$.data.user.password").doesNotExist())
                .andReturn();
        return objectMapper.readTree(result.getResponse().getContentAsString()).at("/data/token").asText();
    }

    private org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder auth(
            org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder builder,
            String token
    ) {
        return builder.header("Authorization", "Bearer " + token);
    }

    private long firstId(org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder builder) throws Exception {
        MvcResult result = mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andReturn();
        JsonNode records = objectMapper.readTree(result.getResponse().getContentAsString()).at("/data/records");
        return records.get(0).get("id").asLong();
    }

    private long firstReplyId(long postId) throws Exception {
        MvcResult result = mockMvc.perform(get("/api/forum/post/" + postId + "/replies"))
                .andExpect(status().isOk())
                .andReturn();
        JsonNode records = objectMapper.readTree(result.getResponse().getContentAsString()).at("/data");
        return records.get(0).get("id").asLong();
    }

    private String json(Object value) throws Exception {
        return objectMapper.writeValueAsString(value);
    }
}

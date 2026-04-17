package com.xiaou.artist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ArtistPlatformApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void loginShouldUseSeedData() throws Exception {
        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "username": "buyer01",
                                  "password": "123456"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.username").value("buyer01"))
                .andExpect(jsonPath("$.data.role").value("USER"));
    }

    @Test
    void approvedArtistsShouldBeQueryable() throws Exception {
        mockMvc.perform(get("/artist/approved"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data[0].nickname").value("星川绘"));
    }

    @Test
    void orderDetailShouldExposeSeededWorkflow() throws Exception {
        mockMvc.perform(get("/order/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.data.status").value("PENDING_FINAL_PAYMENT"))
                .andExpect(jsonPath("$.data.artistName").value("星川绘"));
    }
}

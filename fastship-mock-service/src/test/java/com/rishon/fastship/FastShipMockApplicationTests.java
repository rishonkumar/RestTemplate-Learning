package com.rishon.fastship;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FastShipMockApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    void oauthToken_withValidCredentials_returns200() throws Exception {
        mockMvc.perform(post("/oauth/token")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("grant_type", "client_credentials")
                        .param("client_id", "order-client")
                        .param("client_secret", "secret")
                        .param("scope", "orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").value("fastship-access-token"))
                .andExpect(jsonPath("$.token_type").value("Bearer"))
                .andExpect(jsonPath("$.expires_in").value(3600));
    }

    @Test
    void oauthToken_withInvalidCredentials_returns401() throws Exception {
        mockMvc.perform(post("/oauth/token")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("grant_type", "client_credentials")
                        .param("client_id", "wrong-client")
                        .param("client_secret", "wrong-secret")
                        .param("scope", "orders"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void createProduct_withValidBearerToken_returns201() throws Exception {
        mockMvc.perform(post("/api/products")
                        .header("Authorization", "Bearer fastship-access-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Laptop\",\"price\":1000.0}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Laptop"))
                .andExpect(jsonPath("$.price").value(1000.0));
    }

    @Test
    void createProduct_withoutAuthorizationHeader_returns401() throws Exception {
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Laptop\",\"price\":1000.0}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void createProduct_withInvalidToken_returns401() throws Exception {
        mockMvc.perform(post("/api/products")
                        .header("Authorization", "Bearer invalid-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Laptop\",\"price\":1000.0}"))
                .andExpect(status().isUnauthorized());
    }
}

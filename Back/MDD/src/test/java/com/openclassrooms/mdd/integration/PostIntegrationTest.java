package com.openclassrooms.mdd.integration;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.openclassrooms.mdd.dto.auth.LoginRequest;
import com.openclassrooms.mdd.dto.auth.RegisterRequest;

import com.openclassrooms.mdd.dto.post.CreatePostRequest;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PostIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void post_flow_shouldWork() throws Exception {

        RegisterRequest registerRequest =
                new RegisterRequest();

        registerRequest.setUsername("postUser");
        registerRequest.setEmail("post@test.com");
        registerRequest.setPassword("Password123!");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(
                                        registerRequest
                                )
                        ))
                .andExpect(status().isCreated());

        LoginRequest loginRequest =
                new LoginRequest();

        loginRequest.setEmailOrUsername("post@test.com");
        loginRequest.setPassword("Password123!");

        String loginResponse = mockMvc.perform(
                        post("/api/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(
                                                loginRequest
                                        )
                                )
                )
                .andReturn()
                .getResponse()
                .getContentAsString();

        String token = objectMapper.readTree(loginResponse)
                .get("token")
                .asText();

        mockMvc.perform(post("/api/topics/1/subscribe")
                        .header(
                                "Authorization",
                                "Bearer " + token
                        ))
                .andExpect(status().isOk());

        CreatePostRequest postRequest =
                new CreatePostRequest();

        postRequest.setTitle("My post");
        postRequest.setContent(
                "This is my post content"
        );
        postRequest.setTopicId(1L);

        mockMvc.perform(post("/api/posts")
                        .header(
                                "Authorization",
                                "Bearer " + token
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(
                                        postRequest
                                )
                        ))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title")
                        .value("My post"));

        mockMvc.perform(get("/api/posts/feed")
                        .header(
                                "Authorization",
                                "Bearer " + token
                        ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title")
                        .value("My post"));
    }
}

package com.openclassrooms.mdd.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.mdd.dto.topic.TopicDto;
import com.openclassrooms.mdd.security.jwt.JwtAuthenticationFilter;
import com.openclassrooms.mdd.service.TopicService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TopicController.class)
@AutoConfigureMockMvc(addFilters = false)
class TopicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TopicService topicService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void getAllTopics_shouldReturn200() throws Exception {

        TopicDto topic = TopicDto.builder()
                .id(1L)
                .name("Java")
                .description("Java description")
                .subscribed(true)
                .build();

        when(topicService.getAllTopics())
                .thenReturn(List.of(topic));

        mockMvc.perform(get("/api/topics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name")
                        .value("Java"));
    }

    @Test
    void subscribe_shouldReturn200() throws Exception {

        doNothing().when(topicService).subscribe(1L);

        mockMvc.perform(post("/api/topics/1/subscribe"))
                .andExpect(status().isOk());
    }

    @Test
    void unsubscribe_shouldReturn200() throws Exception {

        doNothing().when(topicService).unsubscribe(1L);

        mockMvc.perform(delete("/api/topics/1/subscribe"))
                .andExpect(status().isOk());
    }
}

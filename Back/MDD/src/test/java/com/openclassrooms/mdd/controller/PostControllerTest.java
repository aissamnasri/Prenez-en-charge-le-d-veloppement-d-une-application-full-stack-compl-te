package com.openclassrooms.mdd.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.openclassrooms.mdd.dto.comment.CreateCommentRequest;
import com.openclassrooms.mdd.dto.post.CreatePostRequest;
import com.openclassrooms.mdd.dto.post.PostDto;

import com.openclassrooms.mdd.security.jwt.JwtAuthenticationFilter;
import com.openclassrooms.mdd.service.PostService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
@AutoConfigureMockMvc(addFilters = false)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PostService postService;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void createPost_shouldReturn201() throws Exception {

        CreatePostRequest request =
                new CreatePostRequest();

        request.setTitle("Post title");
        request.setContent("Post content");
        request.setTopicId(1L);

        PostDto response = PostDto.builder()
                .id(1L)
                .title("Post title")
                .build();

        when(postService.createPost(any()))
                .thenReturn(response);

        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(request)
                        ))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title")
                        .value("Post title"));
    }

    @Test
    void getFeed_shouldReturn200() throws Exception {

        PostDto dto = PostDto.builder()
                .id(1L)
                .title("Post title")
                .build();

        when(postService.getFeed())
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/api/posts/feed"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title")
                        .value("Post title"));
    }

    @Test
    void addComment_shouldReturn201() throws Exception {

        CreateCommentRequest request =
                new CreateCommentRequest();

        request.setContent("Nice post");

        doNothing().when(postService)
                .addComment(eq(1L), any());

        mockMvc.perform(post("/api/posts/1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(request)
                        ))
                .andExpect(status().isCreated());
    }
}

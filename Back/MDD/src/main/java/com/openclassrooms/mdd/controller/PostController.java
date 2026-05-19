package com.openclassrooms.mdd.controller;

import com.openclassrooms.mdd.dto.comment.CreateCommentRequest;
import com.openclassrooms.mdd.dto.post.CreatePostRequest;
import com.openclassrooms.mdd.dto.post.PostDetailDto;
import com.openclassrooms.mdd.dto.post.PostDto;
import com.openclassrooms.mdd.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostDto> createPost(
            @Valid @RequestBody CreatePostRequest request
    ) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postService.createPost(request));
    }

    @GetMapping("/feed")
    public ResponseEntity<List<PostDto>> getFeed() {

        return ResponseEntity.ok(postService.getFeed());
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailDto> getPost(
            @PathVariable Long postId
    ) {

        return ResponseEntity.ok(
                postService.getPostById(postId)
        );
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<Void> addComment(
            @PathVariable Long postId,
            @Valid @RequestBody CreateCommentRequest request
    ) {

        postService.addComment(postId, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }
}
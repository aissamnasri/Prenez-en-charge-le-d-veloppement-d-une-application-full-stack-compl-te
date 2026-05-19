package com.openclassrooms.mdd.service;

import com.openclassrooms.mdd.dto.comment.CreateCommentRequest;
import com.openclassrooms.mdd.dto.post.CreatePostRequest;
import com.openclassrooms.mdd.dto.post.PostDetailDto;
import com.openclassrooms.mdd.dto.post.PostDto;

import java.util.List;

public interface PostService {

    PostDto createPost(CreatePostRequest request);

    List<PostDto> getFeed();

    PostDetailDto getPostById(Long postId);

    void addComment(Long postId, CreateCommentRequest request);
}
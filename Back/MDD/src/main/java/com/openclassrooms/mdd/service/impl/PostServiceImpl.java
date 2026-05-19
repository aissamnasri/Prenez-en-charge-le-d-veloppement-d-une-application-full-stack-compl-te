package com.openclassrooms.mdd.service.impl;

import com.openclassrooms.mdd.dto.comment.CommentDto;
import com.openclassrooms.mdd.dto.comment.CreateCommentRequest;
import com.openclassrooms.mdd.dto.post.CreatePostRequest;
import com.openclassrooms.mdd.dto.post.PostDetailDto;
import com.openclassrooms.mdd.dto.post.PostDto;
import com.openclassrooms.mdd.entity.Comment;
import com.openclassrooms.mdd.entity.Post;
import com.openclassrooms.mdd.entity.Subscription;
import com.openclassrooms.mdd.entity.Topic;
import com.openclassrooms.mdd.entity.User;
import com.openclassrooms.mdd.mapper.CommentMapper;
import com.openclassrooms.mdd.mapper.PostMapper;
import com.openclassrooms.mdd.repository.CommentRepository;
import com.openclassrooms.mdd.repository.PostRepository;
import com.openclassrooms.mdd.repository.TopicRepository;
import com.openclassrooms.mdd.repository.UserRepository;
import com.openclassrooms.mdd.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final TopicRepository topicRepository;

    private final UserRepository userRepository;

    private final CommentRepository commentRepository;

    private final PostMapper postMapper;

    private final CommentMapper commentMapper;

    @Override
    public PostDto createPost(CreatePostRequest request) {

        User user = getAuthenticatedUser();

        Topic topic = topicRepository.findById(request.getTopicId())
                .orElseThrow(() ->
                        new RuntimeException("Topic not found")
                );

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .author(user)
                .topic(topic)
                .build();

        postRepository.save(post);

        return postMapper.toDto(post);
    }

    @Override
    public List<PostDto> getFeed() {

        User user = getAuthenticatedUser();

        List<Topic> subscribedTopics = user.getSubscriptions()
                .stream()
                .map(Subscription::getTopic)
                .toList();

        return postRepository
                .findByTopicInOrderByCreatedAtDesc(subscribedTopics)
                .stream()
                .map(postMapper::toDto)
                .toList();
    }

    @Override
    public PostDetailDto getPostById(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() ->
                        new RuntimeException("Post not found")
                );

        List<CommentDto> comments = post.getComments()
                .stream()
                .map(commentMapper::toDto)
                .toList();

        return PostDetailDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor().getUsername())
                .topic(post.getTopic().getName())
                .createdAt(post.getCreatedAt())
                .comments(comments)
                .build();
    }

    @Override
    public void addComment(
            Long postId,
            CreateCommentRequest request
    ) {

        User user = getAuthenticatedUser();

        Post post = postRepository.findById(postId)
                .orElseThrow(() ->
                        new RuntimeException("Post not found")
                );

        Comment comment = Comment.builder()
                .content(request.getContent())
                .author(user)
                .post(post)
                .build();

        commentRepository.save(comment);
    }

    private User getAuthenticatedUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }
}
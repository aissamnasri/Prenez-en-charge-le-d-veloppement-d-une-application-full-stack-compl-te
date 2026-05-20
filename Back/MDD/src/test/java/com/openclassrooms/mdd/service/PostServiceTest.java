package com.openclassrooms.mdd.service;

import com.openclassrooms.mdd.dto.comment.CreateCommentRequest;
import com.openclassrooms.mdd.dto.post.CreatePostRequest;
import com.openclassrooms.mdd.dto.post.PostDto;
import com.openclassrooms.mdd.entity.*;

import com.openclassrooms.mdd.mapper.CommentMapper;
import com.openclassrooms.mdd.mapper.PostMapper;

import com.openclassrooms.mdd.repository.CommentRepository;
import com.openclassrooms.mdd.repository.PostRepository;
import com.openclassrooms.mdd.repository.TopicRepository;
import com.openclassrooms.mdd.repository.UserRepository;

import com.openclassrooms.mdd.service.impl.PostServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostMapper postMapper;

    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private PostServiceImpl postService;

    private User user;

    private Topic topic;

    private Post post;

    @BeforeEach
    void setUp() {

        user = User.builder()
                .id(1L)
                .username("john")
                .email("john@test.com")
                .build();

        topic = Topic.builder()
                .id(1L)
                .name("Java")
                .build();

        post = Post.builder()
                .id(1L)
                .title("Post title")
                .content("Post content")
                .author(user)
                .topic(topic)
                .build();

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        "john",
                        null
                )
        );
    }

    @Test
    void createPost_shouldCreatePost() {

        CreatePostRequest request = new CreatePostRequest();

        request.setTitle("Post title");
        request.setContent("Post content");
        request.setTopicId(1L);

        PostDto dto = PostDto.builder()
                .id(1L)
                .title("Post title")
                .content("Post content")
                .author("john")
                .topic("Java")
                .build();

        when(userRepository.findByUsername("john"))
                .thenReturn(Optional.of(user));

        when(topicRepository.findById(1L))
                .thenReturn(Optional.of(topic));

        when(postMapper.toDto(any(Post.class)))
                .thenReturn(dto);

        PostDto result = postService.createPost(request);

        assertNotNull(result);

        assertEquals("Post title", result.getTitle());

        verify(postRepository, times(1))
                .save(any(Post.class));
    }

    @Test
    void getFeed_shouldReturnPosts() {

        Subscription subscription = Subscription.builder()
                .topic(topic)
                .user(user)
                .build();

        user.setSubscriptions(List.of(subscription));

        PostDto dto = PostDto.builder()
                .id(1L)
                .title("Post title")
                .build();

        when(userRepository.findByUsername("john"))
                .thenReturn(Optional.of(user));

        when(postRepository.findByTopicInOrderByCreatedAtDesc(
                List.of(topic)))
                .thenReturn(List.of(post));

        when(postMapper.toDto(post))
                .thenReturn(dto);

        List<PostDto> result = postService.getFeed();

        assertEquals(1, result.size());

        assertEquals("Post title",
                result.getFirst().getTitle());
    }

    @Test
    void addComment_shouldCreateComment() {

        CreateCommentRequest request =
                new CreateCommentRequest();

        request.setContent("Nice post");

        when(userRepository.findByUsername("john"))
                .thenReturn(Optional.of(user));

        when(postRepository.findById(1L))
                .thenReturn(Optional.of(post));

        postService.addComment(1L, request);

        verify(commentRepository, times(1))
                .save(any(Comment.class));
    }
}
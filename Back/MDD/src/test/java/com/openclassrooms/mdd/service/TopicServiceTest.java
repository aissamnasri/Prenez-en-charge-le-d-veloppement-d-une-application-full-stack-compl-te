package com.openclassrooms.mdd.service;

import com.openclassrooms.mdd.dto.topic.TopicDto;
import com.openclassrooms.mdd.entity.Subscription;
import com.openclassrooms.mdd.entity.Topic;
import com.openclassrooms.mdd.entity.User;
import com.openclassrooms.mdd.mapper.TopicMapper;
import com.openclassrooms.mdd.repository.SubscriptionRepository;
import com.openclassrooms.mdd.repository.TopicRepository;
import com.openclassrooms.mdd.repository.UserRepository;
import com.openclassrooms.mdd.service.impl.TopicServiceImpl;

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
class TopicServiceTest {

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TopicMapper topicMapper;

    @InjectMocks
    private TopicServiceImpl topicService;

    private User user;

    private Topic topic;

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
                .description("Java description")
                .build();

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        "john",
                        null
                )
        );
    }

    @Test
    void getAllTopics_shouldReturnTopics() {

        TopicDto dto = TopicDto.builder()
                .id(1L)
                .name("Java")
                .description("Java description")
                .subscribed(true)
                .build();

        when(userRepository.findByUsername("john"))
                .thenReturn(Optional.of(user));

        when(topicRepository.findAll())
                .thenReturn(List.of(topic));

        when(topicMapper.toDto(topic))
                .thenReturn(dto);

        when(subscriptionRepository.existsByUserAndTopic(user, topic))
                .thenReturn(true);

        List<TopicDto> result = topicService.getAllTopics();

        assertEquals(1, result.size());

        assertEquals("Java", result.getFirst().getName());

        assertTrue(result.getFirst().isSubscribed());
    }

    @Test
    void subscribe_shouldCreateSubscription() {

        when(userRepository.findByUsername("john"))
                .thenReturn(Optional.of(user));

        when(topicRepository.findById(1L))
                .thenReturn(Optional.of(topic));

        when(subscriptionRepository.existsByUserAndTopic(user, topic))
                .thenReturn(false);

        topicService.subscribe(1L);

        verify(subscriptionRepository, times(1))
                .save(any(Subscription.class));
    }

    @Test
    void unsubscribe_shouldDeleteSubscription() {

        Subscription subscription = Subscription.builder()
                .id(1L)
                .user(user)
                .topic(topic)
                .build();

        when(userRepository.findByUsername("john"))
                .thenReturn(Optional.of(user));

        when(topicRepository.findById(1L))
                .thenReturn(Optional.of(topic));

        when(subscriptionRepository.findByUserAndTopic(user, topic))
                .thenReturn(Optional.of(subscription));

        topicService.unsubscribe(1L);

        verify(subscriptionRepository, times(1))
                .delete(subscription);
    }
}
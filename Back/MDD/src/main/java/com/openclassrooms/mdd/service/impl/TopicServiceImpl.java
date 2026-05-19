package com.openclassrooms.mdd.service.impl;

import com.openclassrooms.mdd.dto.topic.TopicDto;
import com.openclassrooms.mdd.entity.Subscription;
import com.openclassrooms.mdd.entity.Topic;
import com.openclassrooms.mdd.entity.User;
import com.openclassrooms.mdd.mapper.TopicMapper;
import com.openclassrooms.mdd.repository.SubscriptionRepository;
import com.openclassrooms.mdd.repository.TopicRepository;
import com.openclassrooms.mdd.repository.UserRepository;
import com.openclassrooms.mdd.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;

    private final SubscriptionRepository subscriptionRepository;

    private final UserRepository userRepository;

    private final TopicMapper topicMapper;

    @Override
    public List<TopicDto> getAllTopics() {

        User user = getAuthenticatedUser();

        return topicRepository.findAll()
                .stream()
                .map(topic -> {

                    TopicDto dto = topicMapper.toDto(topic);

                    boolean subscribed =
                            subscriptionRepository.existsByUserAndTopic(
                                    user,
                                    topic
                            );

                    return TopicDto.builder()
                            .id(dto.getId())
                            .name(dto.getName())
                            .description(dto.getDescription())
                            .subscribed(subscribed)
                            .build();
                })
                .toList();
    }

    @Override
    public void subscribe(Long topicId) {

        User user = getAuthenticatedUser();

        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() ->
                        new RuntimeException("Topic not found")
                );

        boolean alreadySubscribed =
                subscriptionRepository.existsByUserAndTopic(
                        user,
                        topic
                );

        if (alreadySubscribed) {
            throw new RuntimeException("Already subscribed");
        }

        Subscription subscription = Subscription.builder()
                .user(user)
                .topic(topic)
                .build();

        subscriptionRepository.save(subscription);
    }

    @Override
    public void unsubscribe(Long topicId) {

        User user = getAuthenticatedUser();

        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() ->
                        new RuntimeException("Topic not found")
                );

        Subscription subscription =
                subscriptionRepository.findByUserAndTopic(user, topic)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Subscription not found"
                                )
                        );

        subscriptionRepository.delete(subscription);
    }

    private User getAuthenticatedUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );
    }
}
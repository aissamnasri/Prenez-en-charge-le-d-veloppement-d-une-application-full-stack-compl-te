package com.openclassrooms.mdd.service;

import com.openclassrooms.mdd.dto.topic.TopicDto;

import java.util.List;

public interface TopicService {

    List<TopicDto> getAllTopics();

    void subscribe(Long topicId);

    void unsubscribe(Long topicId);
}
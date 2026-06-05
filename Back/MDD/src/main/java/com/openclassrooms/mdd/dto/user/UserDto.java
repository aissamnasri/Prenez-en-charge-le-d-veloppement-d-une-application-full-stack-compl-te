package com.openclassrooms.mdd.dto.user;

import com.openclassrooms.mdd.dto.topic.TopicDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserDto {

    private Long id;

    private String username;

    private String email;

    private List<TopicDto> subscriptions;
}
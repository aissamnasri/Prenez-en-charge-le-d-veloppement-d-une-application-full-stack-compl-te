package com.openclassrooms.mdd.dto.topic;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TopicDto {

    private Long id;

    private String name;

    private String description;

    private Boolean subscribed;
}
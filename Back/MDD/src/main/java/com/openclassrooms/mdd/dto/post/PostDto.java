package com.openclassrooms.mdd.dto.post;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostDto {

    private Long id;

    private String title;

    private String content;

    private String author;

    private String topic;

    private LocalDateTime createdAt;
}
package com.openclassrooms.mdd.dto.comment;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentDto {

    private Long id;

    private String content;

    private String author;

    private LocalDateTime createdAt;
}
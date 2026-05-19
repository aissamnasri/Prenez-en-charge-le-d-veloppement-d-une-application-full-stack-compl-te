package com.openclassrooms.mdd.dto.post;

import com.openclassrooms.mdd.dto.comment.CommentDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PostDetailDto {

    private Long id;

    private String title;

    private String content;

    private String author;

    private String topic;

    private LocalDateTime createdAt;

    private List<CommentDto> comments;
}
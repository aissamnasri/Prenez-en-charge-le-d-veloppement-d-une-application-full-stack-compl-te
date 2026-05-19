package com.openclassrooms.mdd.mapper;

import com.openclassrooms.mdd.dto.comment.CommentDto;
import com.openclassrooms.mdd.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "author", source = "author.username")
    CommentDto toDto(Comment comment);
}
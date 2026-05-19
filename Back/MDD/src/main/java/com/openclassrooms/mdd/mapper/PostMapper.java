package com.openclassrooms.mdd.mapper;

import com.openclassrooms.mdd.dto.post.PostDto;
import com.openclassrooms.mdd.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "author", source = "author.username")
    @Mapping(target = "topic", source = "topic.name")
    PostDto toDto(Post post);
}
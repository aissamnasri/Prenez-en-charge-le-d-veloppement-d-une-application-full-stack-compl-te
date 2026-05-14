package com.openclassrooms.mdd.mapper;

import com.openclassrooms.mdd.dto.topic.TopicDto;
import com.openclassrooms.mdd.entity.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TopicMapper {

    @Mapping(target = "subscribed", ignore = true)
    TopicDto toDto(Topic topic);
}
package com.openclassrooms.mdd.mapper;

import com.openclassrooms.mdd.dto.user.UserDto;
import com.openclassrooms.mdd.entity.Subscription;
import com.openclassrooms.mdd.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "subscriptions", expression = "java(mapSubscriptions(user.getSubscriptions()))")
    UserDto toDto(User user);

    default List<String> mapSubscriptions(List<Subscription> subscriptions) {

        return subscriptions.stream()
                .map(subscription -> subscription.getTopic().getName())
                .toList();
    }
}
package com.openclassrooms.mdd.dto.user;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserDto {

    private Long id;

    private String username;

    private String email;

    private List<String> subscriptions;
}
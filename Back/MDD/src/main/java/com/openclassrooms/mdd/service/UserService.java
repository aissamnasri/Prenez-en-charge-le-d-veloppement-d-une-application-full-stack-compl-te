package com.openclassrooms.mdd.service;

import com.openclassrooms.mdd.dto.user.UpdateUserRequest;
import com.openclassrooms.mdd.dto.user.UserDto;

public interface UserService {

    UserDto getCurrentUser();

    UserDto updateCurrentUser(UpdateUserRequest request);
}
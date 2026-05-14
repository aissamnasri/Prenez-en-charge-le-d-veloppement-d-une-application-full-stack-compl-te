package com.openclassrooms.mdd.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank
    private String emailOrUsername;

    @NotBlank
    private String password;
}
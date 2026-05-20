package com.openclassrooms.mdd.controller;

import com.openclassrooms.mdd.dto.user.UpdateUserRequest;
import com.openclassrooms.mdd.dto.user.UserDto;
import com.openclassrooms.mdd.service.UserService;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")

@Tag(
        name = "Users",
        description = "Gestion du profil utilisateur"
)

@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Récupérer l'utilisateur connecté",
            description = "Retourne les informations du profil utilisateur connecté"
    )

    @ApiResponses(value = {

            @ApiResponse(
                    responseCode = "200",
                    description = "Profil utilisateur récupéré",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = UserDto.class
                            )
                    )
            ),

            @ApiResponse(
                    responseCode = "401",
                    description = "Utilisateur non authentifié"
            )
    })

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser() {

        return ResponseEntity.ok(
                userService.getCurrentUser()
        );
    }

    @Operation(
            summary = "Modifier le profil utilisateur",
            description = "Met à jour les informations du profil utilisateur connecté"
    )

    @ApiResponses(value = {

            @ApiResponse(
                    responseCode = "200",
                    description = "Profil mis à jour avec succès",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = UserDto.class
                            )
                    )
            ),

            @ApiResponse(
                    responseCode = "400",
                    description = "Données invalides"
            ),

            @ApiResponse(
                    responseCode = "401",
                    description = "Utilisateur non authentifié"
            )
    })

    @PutMapping("/me")
    public ResponseEntity<UserDto> updateCurrentUser(

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Nouvelles informations utilisateur",
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "username": "newUsername",
                                              "email": "newemail@test.com",
                                              "password": "Password123!"
                                            }
                                            """
                            )
                    )
            )

            @Valid @RequestBody UpdateUserRequest request
    ) {

        return ResponseEntity.ok(
                userService.updateCurrentUser(request)
        );
    }
}
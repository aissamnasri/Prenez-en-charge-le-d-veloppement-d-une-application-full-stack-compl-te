package com.openclassrooms.mdd.controller;

import com.openclassrooms.mdd.dto.auth.AuthResponse;
import com.openclassrooms.mdd.dto.auth.LoginRequest;
import com.openclassrooms.mdd.dto.auth.RegisterRequest;
import com.openclassrooms.mdd.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")

@Tag(
        name = "Authentication",
        description = "Endpoints d'authentification"
)
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Inscription utilisateur",
            description = "Permet de créer un nouveau compte utilisateur"
    )

    @ApiResponses(value = {

            @ApiResponse(
                    responseCode = "201",
                    description = "Utilisateur créé avec succès",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = AuthResponse.class
                            )
                    )
            ),

            @ApiResponse(
                    responseCode = "400",
                    description = "Données invalides"
            )
    })

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Informations utilisateur",
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "username": "john",
                                              "email": "john@test.com",
                                              "password": "Password123!"
                                            }
                                            """
                            )
                    )
            )

            @Valid @RequestBody RegisterRequest request
    ) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.register(request));
    }

    @Operation(
            summary = "Connexion utilisateur",
            description = "Authentifie un utilisateur et retourne un JWT"
    )

    @ApiResponses(value = {

            @ApiResponse(
                    responseCode = "200",
                    description = "Connexion réussie",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = AuthResponse.class
                            )
                    )
            ),

            @ApiResponse(
                    responseCode = "401",
                    description = "Identifiants invalides"
            )
    })

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Credentials utilisateur",
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "emailOrUsername": "john@test.com",
                                              "password": "Password123!"
                                            }
                                            """
                            )
                    )
            )

            @Valid @RequestBody LoginRequest request
    ) {

        return ResponseEntity.ok(
                authService.login(request)
        );
    }
}
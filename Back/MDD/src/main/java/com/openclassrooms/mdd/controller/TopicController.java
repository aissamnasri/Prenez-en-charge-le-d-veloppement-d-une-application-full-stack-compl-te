package com.openclassrooms.mdd.controller;

import com.openclassrooms.mdd.dto.topic.TopicDto;
import com.openclassrooms.mdd.service.TopicService;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")

@Tag(
        name = "Topics",
        description = "Gestion des topics"
)

@SecurityRequirement(name = "bearerAuth")
public class TopicController {

    private final TopicService topicService;

    @Operation(
            summary = "Récupérer tous les topics",
            description = "Retourne la liste de tous les topics avec l'état d'abonnement de l'utilisateur"
    )

    @ApiResponses(value = {

            @ApiResponse(
                    responseCode = "200",
                    description = "Liste des topics récupérée avec succès",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = TopicDto.class
                            )
                    )
            ),

            @ApiResponse(
                    responseCode = "401",
                    description = "Utilisateur non authentifié"
            )
    })

    @GetMapping
    public ResponseEntity<List<TopicDto>> getAllTopics() {

        return ResponseEntity.ok(
                topicService.getAllTopics()
        );
    }

    @Operation(
            summary = "S'abonner à un topic",
            description = "Permet à l'utilisateur connecté de s'abonner à un topic"
    )

    @ApiResponses(value = {

            @ApiResponse(
                    responseCode = "200",
                    description = "Abonnement effectué avec succès"
            ),

            @ApiResponse(
                    responseCode = "400",
                    description = "Utilisateur déjà abonné"
            ),

            @ApiResponse(
                    responseCode = "404",
                    description = "Topic introuvable"
            ),

            @ApiResponse(
                    responseCode = "401",
                    description = "Utilisateur non authentifié"
            )
    })

    @PostMapping("/{topicId}/subscribe")
    public ResponseEntity<Void> subscribe(
            @PathVariable Long topicId
    ) {

        topicService.subscribe(topicId);

        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Se désabonner d'un topic",
            description = "Permet à l'utilisateur connecté de se désabonner d'un topic"
    )

    @ApiResponses(value = {

            @ApiResponse(
                    responseCode = "200",
                    description = "Désabonnement effectué avec succès"
            ),

            @ApiResponse(
                    responseCode = "404",
                    description = "Abonnement ou topic introuvable"
            ),

            @ApiResponse(
                    responseCode = "401",
                    description = "Utilisateur non authentifié"
            )
    })

    @DeleteMapping("/{topicId}/subscribe")
    public ResponseEntity<Void> unsubscribe(
            @PathVariable Long topicId
    ) {

        topicService.unsubscribe(topicId);

        return ResponseEntity.ok().build();
    }
}
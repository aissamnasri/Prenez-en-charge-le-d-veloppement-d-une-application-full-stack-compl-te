package com.openclassrooms.mdd.controller;

import com.openclassrooms.mdd.dto.comment.CreateCommentRequest;
import com.openclassrooms.mdd.dto.post.CreatePostRequest;
import com.openclassrooms.mdd.dto.post.PostDetailDto;
import com.openclassrooms.mdd.dto.post.PostDto;
import com.openclassrooms.mdd.service.PostService;

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

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")

@Tag(
        name = "Posts",
        description = "Gestion des posts"
)

@SecurityRequirement(name = "bearerAuth")
public class PostController {

    private final PostService postService;

    @Operation(
            summary = "Créer un post",
            description = "Permet de créer un nouveau post"
    )

    @ApiResponses(value = {

            @ApiResponse(
                    responseCode = "201",
                    description = "Post créé avec succès",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = PostDto.class
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

    @PostMapping
    public ResponseEntity<PostDto> createPost(

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Informations du post",
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "title": "My first post",
                                              "content": "This is my first post content",
                                              "topicId": 1
                                            }
                                            """
                            )
                    )
            )

            @Valid @RequestBody CreatePostRequest request
    ) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postService.createPost(request));
    }

    @Operation(
            summary = "Récupérer le feed utilisateur",
            description = "Retourne les posts des topics suivis par l'utilisateur"
    )

    @ApiResponses(value = {

            @ApiResponse(
                    responseCode = "200",
                    description = "Feed récupéré avec succès",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = PostDto.class
                            )
                    )
            ),

            @ApiResponse(
                    responseCode = "401",
                    description = "Utilisateur non authentifié"
            )
    })

    @GetMapping("/feed")
    public ResponseEntity<List<PostDto>> getFeed() {

        return ResponseEntity.ok(
                postService.getFeed()
        );
    }

    @Operation(
            summary = "Récupérer un post",
            description = "Retourne le détail d'un post avec ses commentaires"
    )

    @ApiResponses(value = {

            @ApiResponse(
                    responseCode = "200",
                    description = "Post récupéré avec succès",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = PostDetailDto.class
                            )
                    )
            ),

            @ApiResponse(
                    responseCode = "404",
                    description = "Post introuvable"
            ),

            @ApiResponse(
                    responseCode = "401",
                    description = "Utilisateur non authentifié"
            )
    })

    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailDto> getPost(
            @PathVariable Long postId
    ) {

        return ResponseEntity.ok(
                postService.getPostById(postId)
        );
    }

    @Operation(
            summary = "Ajouter un commentaire",
            description = "Ajoute un commentaire à un post"
    )

    @ApiResponses(value = {

            @ApiResponse(
                    responseCode = "201",
                    description = "Commentaire ajouté avec succès"
            ),

            @ApiResponse(
                    responseCode = "400",
                    description = "Données invalides"
            ),

            @ApiResponse(
                    responseCode = "404",
                    description = "Post introuvable"
            ),

            @ApiResponse(
                    responseCode = "401",
                    description = "Utilisateur non authentifié"
            )
    })

    @PostMapping("/{postId}/comments")
    public ResponseEntity<Void> addComment(

            @PathVariable Long postId,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Contenu du commentaire",
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "content": "Nice post!"
                                            }
                                            """
                            )
                    )
            )

            @Valid @RequestBody CreateCommentRequest request
    ) {

        postService.addComment(postId, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }
}
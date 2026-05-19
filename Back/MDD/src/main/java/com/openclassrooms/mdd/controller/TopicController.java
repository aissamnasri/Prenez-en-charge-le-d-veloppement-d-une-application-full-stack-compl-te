package com.openclassrooms.mdd.controller;

import com.openclassrooms.mdd.dto.topic.TopicDto;
import com.openclassrooms.mdd.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class TopicController {

    private final TopicService topicService;

    @GetMapping
    public ResponseEntity<List<TopicDto>> getAllTopics() {

        return ResponseEntity.ok(
                topicService.getAllTopics()
        );
    }

    @PostMapping("/{topicId}/subscribe")
    public ResponseEntity<Void> subscribe(
            @PathVariable Long topicId
    ) {

        topicService.subscribe(topicId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{topicId}/subscribe")
    public ResponseEntity<Void> unsubscribe(
            @PathVariable Long topicId
    ) {

        topicService.unsubscribe(topicId);

        return ResponseEntity.ok().build();
    }
}
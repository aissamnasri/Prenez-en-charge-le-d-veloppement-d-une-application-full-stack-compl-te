package com.openclassrooms.mdd.repository;

import com.openclassrooms.mdd.entity.Post;
import com.openclassrooms.mdd.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByTopicInOrderByCreatedAtDesc(List<Topic> topics);
}
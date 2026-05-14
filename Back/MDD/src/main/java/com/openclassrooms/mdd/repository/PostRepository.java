package com.openclassrooms.mdd.repository;

import com.openclassrooms.mdd.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
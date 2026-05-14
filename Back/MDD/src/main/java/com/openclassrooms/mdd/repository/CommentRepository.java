package com.openclassrooms.mdd.repository;

import com.openclassrooms.mdd.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
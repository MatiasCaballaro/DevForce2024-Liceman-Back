package com.liceman.application.training.domain.repository;

import com.liceman.application.training.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}

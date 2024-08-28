package com.sparta.jpascheduletask.repository;

import com.sparta.jpascheduletask.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}

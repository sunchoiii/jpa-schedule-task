package com.sparta.jpascheduletask.comment.repository;

import com.sparta.jpascheduletask.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}

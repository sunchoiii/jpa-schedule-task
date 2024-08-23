package com.sparta.jpascheduletask.comment.dto;

import com.sparta.jpascheduletask.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponseDto {
    private Long comment_id;
    private String username;
    private String contents;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;


    public CommentResponseDto(Comment comment) {
        this.comment_id = comment.getComment_id();
        this.username = comment.getUsername();
        this.contents = comment.getContents();
        this.createDate = comment.getCreateDate();
        this.updateDate = comment.getUpdateDate();
    }
}

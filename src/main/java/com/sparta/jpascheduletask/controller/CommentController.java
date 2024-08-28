package com.sparta.jpascheduletask.controller;

import com.sparta.jpascheduletask.dto.CommentRequestDto;
import com.sparta.jpascheduletask.dto.CommentResponseDto;
import com.sparta.jpascheduletask.entity.Comment;
import com.sparta.jpascheduletask.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    // 댓글 저장
    @PostMapping("/comment")
    public CommentResponseDto createComment (@RequestBody CommentRequestDto requestDto) {
        return commentService.createComment(requestDto);
    }

    // 댓글 단건 조회
    @GetMapping("/comment/{comment_id}")
    public Comment findByID (@PathVariable Long comment_id) {
        return commentService.findByID(comment_id);
    }

    // 댓글 전체 조회
    @GetMapping("/comment")
    public List<CommentResponseDto> findAll() {
        return commentService.findAll();
    }

    // 댓글 수정 (아이디 확인 후 작성자명, 댓글 내용 수정)
    @PutMapping("/comment/{comment_id}")
    public CommentResponseDto update (@PathVariable Long comment_id, @RequestBody CommentRequestDto requestDto) {
        return commentService.update(comment_id, requestDto);
    }

    // 댓글 삭제 (아이디 확인 후 삭제)
    @DeleteMapping("/comment/{comment_id}")
    public String delete(@PathVariable Long comment_id) {
        return commentService.delete(comment_id);
    }




}

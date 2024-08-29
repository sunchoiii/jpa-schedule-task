package com.sparta.jpascheduletask.service;

import com.sparta.jpascheduletask.dto.CommentRequestDto;
import com.sparta.jpascheduletask.dto.CommentResponseDto;
import com.sparta.jpascheduletask.entity.Comment;
import com.sparta.jpascheduletask.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    // 댓글 저장
    @Transactional
    public CommentResponseDto createComment(CommentRequestDto requestDto) {
        Comment comment = new Comment(requestDto);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    // 댓글 단건 조회
    @Transactional(readOnly = true)
    public CommentResponseDto findById(Long comment_id) {
        Comment comment = commentRepository.findById(comment_id).orElseThrow(() ->
                new IllegalArgumentException("선택한 댓글은 존재하지 않습니다.")
        );
        return new CommentResponseDto(comment);
    }

    // 댓글 전체 조회
    @Transactional(readOnly = true)
    public List<CommentResponseDto> findCommentList() {
        return commentRepository.findAll().stream().map(CommentResponseDto::new).toList();
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto update(Long comment_id, CommentRequestDto requestDto) {
        //해당 아이디의 댓글이 있는지 확인
        Comment comment = commentRepository.findById(comment_id).orElseThrow(() ->
                new IllegalArgumentException("선택한 댓글은 존재하지 않습니다.")
        );
        comment.update(requestDto);
        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    @Transactional
    public String delete(Long comment_id) {
        //해당 아이디의 댓글이 있는지 확인
        Comment comment = commentRepository.findById(comment_id).orElseThrow(() ->
                new IllegalArgumentException("선택한 댓글은 존재하지 않습니다.")
        );
        commentRepository.delete(comment);
        return "해당 댓글이 삭제되었습니다";

    }
}

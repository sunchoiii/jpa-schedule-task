package com.sparta.jpascheduletask.comment.service;

import com.sparta.jpascheduletask.comment.dto.CommentRequestDto;
import com.sparta.jpascheduletask.comment.dto.CommentResponseDto;
import com.sparta.jpascheduletask.comment.entity.Comment;
import com.sparta.jpascheduletask.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    // 댓글 저장
    public CommentResponseDto createComment(CommentRequestDto requestDto) {
        Comment comment = new Comment(requestDto);

        Comment saveComment = commentRepository.save(comment);

        CommentResponseDto responseDto = new CommentResponseDto(comment);
        return responseDto;
    }

    // 댓글 단건 조회
    public Comment findByID(Long comment_id) {
        return commentRepository.findById(comment_id).orElseThrow(() ->
                new IllegalArgumentException("선택한 댓글은 존재하지 않습니다.")
        );
    }

    // 댓글 전체 조회
    public List<CommentResponseDto> findAll() {
        return commentRepository.findAll().stream().map(CommentResponseDto::new).toList();
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto update(Long comment_id, CommentRequestDto requestDto) {
        //해당 아이디의 댓글이 있는지 확인
        Comment comment = findByID(comment_id);

        comment.update(requestDto);

        CommentResponseDto responseDto = new CommentResponseDto(comment);
        return responseDto;
    }

    // 댓글 삭제
    public String delete(Long comment_id) {
        //해당 아이디의 댓글이 있는지 확인
        Comment comment = findByID(comment_id);
        commentRepository.delete(comment);

        return "해당 댓글이 삭제되었습니다";

    }



}

package com.sparta.todoproject_jwt.controller;

import com.sparta.todoproject_jwt.dto.CommentRequestDto;
import com.sparta.todoproject_jwt.dto.CommentResponseDto;
import com.sparta.todoproject_jwt.security.UserDetailsImpl;
import com.sparta.todoproject_jwt.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/param")
    @Operation(summary = "댓글 조회", description = "할일 id 값을 전달받아 댓글목록을 조회한다")
    public List<CommentResponseDto> getCommentList(@RequestParam Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.getCommentList(id, userDetails.getUser());
    }

    @PostMapping("/param")
    @Operation(summary = "댓글 작성", description = "할일 id 값을 전달받아 댓글을 작성한다")
    public CommentResponseDto createCommentByTodoId(@RequestParam Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.createCommentByTodoId(id, userDetails.getUser(), requestDto);
    }

    @PutMapping("/param")
    @Operation(summary = "댓글 수정", description = "댓글 id 값을 전달받아 댓글을 수정한다")
    public CommentResponseDto updateCommentById(@RequestParam Long id, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.updateCommentById(id, userDetails.getUser(), requestDto);
    }

    @DeleteMapping("/param")
    @Operation(summary = "댓글 삭제", description = "댓글 id 값을 전달받아 댓글을 삭제한다")
    public String deleteCommentById(@RequestParam Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.deleteCommentById(id, userDetails.getUser());
    }
}

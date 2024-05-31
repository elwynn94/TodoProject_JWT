package com.sparta.todoproject_jwt.dto;

import com.sparta.todoproject_jwt.entity.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDto {
    private Long todoId;
    private Long id;
    private String username;
    private String comment;

    public CommentResponseDto(Comment comments) {
        this.id = comments.getId();
        this.todoId = comments.getTodo().getId();
        this.username = comments.getUser().getUsername();
        this.comment = comments.getComment();
    }
}

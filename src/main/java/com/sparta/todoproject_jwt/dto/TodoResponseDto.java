package com.sparta.todoproject_jwt.dto;

import com.sparta.todoproject_jwt.entity.Todo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TodoResponseDto {

    private Long id;
    private String title;
    private String content;
    private String username;
    private LocalDateTime dateCreated;
    private boolean iscompleted;

    public TodoResponseDto(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.content = todo.getContent();
        this.username = todo.getUser().getUsername();
        this.dateCreated = todo.getCreatedAt();
        this.iscompleted = todo.isCompleted();
    }
}

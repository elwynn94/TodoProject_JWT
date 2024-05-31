package com.sparta.todoproject_jwt.dto;

import com.sparta.todoproject_jwt.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpResponseDto {

    private Long id;
    private String username;

    public SignUpResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }
}

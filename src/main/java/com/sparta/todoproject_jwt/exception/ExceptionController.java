package com.sparta.todoproject_jwt.exception;

import com.sparta.todoproject_jwt.dto.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice

public class ExceptionController {

    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity<ExceptionDto> handleIllegalArgumentException(final IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionDto(400, ex.getMessage()));
    }

    @ExceptionHandler({ NullPointerException.class})
    public ResponseEntity<ExceptionDto> handleNullPointerException(final NullPointerException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionDto(400, "토큰이 유효하지 않습니다."));
    }
}

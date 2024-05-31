package com.sparta.todoproject_jwt.repository;

import com.sparta.todoproject_jwt.entity.Comment;
import com.sparta.todoproject_jwt.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByTodo(Todo todo);
}

package com.sparta.todoproject_jwt.service;

import com.sparta.todoproject_jwt.dto.CommentRequestDto;
import com.sparta.todoproject_jwt.dto.CommentResponseDto;
import com.sparta.todoproject_jwt.entity.Comment;
import com.sparta.todoproject_jwt.entity.Todo;
import com.sparta.todoproject_jwt.entity.User;
import com.sparta.todoproject_jwt.repository.CommentRepository;
import com.sparta.todoproject_jwt.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TodoRepository todoRepository;


    public CommentResponseDto createCommentByTodoId(Long id, User user, CommentRequestDto requestDto) {
        Todo todo = todoRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 id의 할일이 없습니다."));
        if(todo.isPrivate()&&!todo.getUser().getUsername().equals(user.getUsername()))
            throw new IllegalArgumentException("해당 할일에 댓글을 작성할 권한이 없습니다.");

        Comment comment = commentRepository.save(new Comment(user, todo, requestDto));
        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto updateCommentById(Long id, User user, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 id의 댓글이 없습니다."));
        if(!comment.getUser().getUsername().equals(user.getUsername()))
            throw new IllegalArgumentException("댓글 작성자만 수정할 수 있습니다.");

           comment.update(requestDto);
           return new CommentResponseDto(comment);

    }

    public String deleteCommentById(Long id, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 id의 댓글이 없습니다."));
        if(!comment.getUser().getUsername().equals(user.getUsername()))
            throw new IllegalArgumentException("댓글 작성자만 삭제할 수 있습니다.");

        commentRepository.deleteById(id);

        return "삭제되었습니다.";

    }

    public List<CommentResponseDto> getCommentList(Long id, User user) {
        Todo todo = todoRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 id의 할일이 없습니다."));
        if(todo.isPrivate()&&!todo.getUser().getUsername().equals(user.getUsername()))
            throw new IllegalArgumentException("해당 할일의 댓글을 열람할 권한이 없습니다.");

        List<Comment> commentList = commentRepository.findAllByTodo(todo);
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

        for(Comment comment : commentList){
            commentResponseDtoList.add(new CommentResponseDto(comment));
        }
        return commentResponseDtoList;
    }
}

package com.sparta.todoproject_jwt.service;

import com.sparta.todoproject_jwt.dto.TodoRequestDto;
import com.sparta.todoproject_jwt.dto.TodoResponseDto;
import com.sparta.todoproject_jwt.entity.Todo;
import com.sparta.todoproject_jwt.entity.User;
import com.sparta.todoproject_jwt.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoResponseDto createTodo(TodoRequestDto requestDto, User user) {
        Todo todo = todoRepository.save(new Todo(requestDto, user));
        return new TodoResponseDto(todo);
    }

    public TodoResponseDto getTodoById(Long id, User user) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("해당 id의 할일이 존재하지 않습니다.")
                );
        if(todo.isPrivate()&&!todo.getUser().getUsername().equals(user.getUsername()))
            throw new IllegalArgumentException("해당 할일을 열람할 권한이 없습니다.");

        return new TodoResponseDto(todo);
    }

    public List<TodoResponseDto> getTodoList(User user) {
        List<Todo> todoList = todoRepository.findAllByUser(user);
        List<TodoResponseDto> todoResponseDtoList = new ArrayList<>();

        for (Todo todo : todoList) {
            todoResponseDtoList.add(new TodoResponseDto(todo));
        }
        Collections.sort(todoResponseDtoList, (o1, o2) -> o2.getDateCreated().compareTo(o1.getDateCreated()));
        return todoResponseDtoList;
    }

    public List<TodoResponseDto> getTodoListByTitle(String title, User user) {
        List<Todo> todoList = todoRepository.findAllByUser(user);
        List<TodoResponseDto> todoResponseDtoList = new ArrayList<>();

        for(Todo todo : todoList){
            if(todo.getTitle().equals(title)){
                todoResponseDtoList.add(new TodoResponseDto(todo));
            }
        }
        return todoResponseDtoList;


    }

    @Transactional
    public TodoResponseDto updateTodoById(Long id, User user, TodoRequestDto requestDto) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("해당 id의 할일이 존재하지 않습니다.")
        );
        if(!todo.getUser().getUsername().equals(user.getUsername()))
            throw new IllegalArgumentException("할일 작성자만 수정할 수 있습니다.");


            todo.update(requestDto);
            return new TodoResponseDto(todo);


    }

    @Transactional
    public String updateTodoCompletion(Long id, User user) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("해당 id의 할일이 존재하지 않습니다.")
        );
        if(!todo.getUser().getUsername().equals(user.getUsername()))
            throw new IllegalArgumentException("할일 작성자만 수정할 수 있습니다.");


            todo.updateCompletion();
            return  todo.isCompleted() + "로 변경되었습니다";

    }

    @Transactional
    public String updateTodoDisclosure(Long id, User user){
        Todo todo = todoRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("해당 id의 할일이 존재하지 않습니다.")
        );
        if(!todo.getUser().getUsername().equals(user.getUsername()))
            throw new IllegalArgumentException("할일 작성자만 수정할 수 있습니다.");


            todo.updateDisclosure();
            return  todo.isPrivate() + "로 변경되었습니다";

    }


    public List<TodoResponseDto> getTodoListUncompleted(User user) {
        List<Todo> todoList = todoRepository.findAllByUser(user);
        List<TodoResponseDto> todoResponseDtoList = new ArrayList<>();

        for (Todo todo : todoList) {
            if(!todo.isCompleted())
            todoResponseDtoList.add(new TodoResponseDto(todo));
        }
        Collections.sort(todoResponseDtoList, (o1, o2) -> o2.getDateCreated().compareTo(o1.getDateCreated()));
        return todoResponseDtoList;
    }
}

package com.sparta.todoproject_jwt.controller;

import com.sparta.todoproject_jwt.dto.TodoRequestDto;
import com.sparta.todoproject_jwt.dto.TodoResponseDto;
import com.sparta.todoproject_jwt.security.UserDetailsImpl;
import com.sparta.todoproject_jwt.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    @Operation(summary = "할일 생성", description = "할일을 생성한다.")
    public TodoResponseDto createTodo(@RequestBody TodoRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return todoService.createTodo(requestDto, userDetails.getUser());
    }

    @GetMapping("/param")
    @Operation(summary = "할일 조회(id)", description = "id를 전달받아 할일을 조회한다.")
    public TodoResponseDto getTodoById(@RequestParam Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return todoService.getTodoById(id, userDetails.getUser());
    }

    @GetMapping("/search/param")
    @Operation(summary = "할일 조회(title)", description = "title를 전달받아 할일을 조회한다.")
    public List<TodoResponseDto> getTodoByTitle(@RequestParam String title, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return todoService.getTodoListByTitle(title, userDetails.getUser());
    }

    @GetMapping("/all")
    @Operation(summary = "할일 목록 모두 조회", description = "할일 리스트를 조회한다.")
    public List<TodoResponseDto> getTodoList(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return todoService.getTodoList(userDetails.getUser());
    }

    @GetMapping("/uncompleted")
    @Operation(summary = "완료되지 않은 할일 목록 조회", description = "완료되지 않은 할일 리스트를 조회한다.")
    public List<TodoResponseDto> getTodoListUncompleted(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return todoService.getTodoListUncompleted(userDetails.getUser());
    }

    @PutMapping("/param")
    @Operation(summary = "할일 수정", description = "할일 id 값을 전달받아 할일을 수정한다")
    public TodoResponseDto updateTodoById(@RequestParam Long id, @RequestBody TodoRequestDto requestDto,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return todoService.updateTodoById(id, userDetails.getUser(), requestDto);
    }

    @PutMapping("/completion/param")
    @Operation(summary = "할일 완료여부 수정", description = "할일 id 값을 전달받아 완료여부를 수정한다")
    public String updateTodoCompletion(@RequestParam Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return todoService.updateTodoCompletion(id, userDetails.getUser());
    }

    @PutMapping("/disclosure/param")
    @Operation(summary = "할일 공개여부 수정", description = "할일 id 값을 전달받아 공개여부를 수정한다")
    public String updateTodoDisclosure(@RequestParam Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return todoService.updateTodoDisclosure(id, userDetails.getUser());
    }
}

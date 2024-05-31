package com.sparta.todoproject_jwt.entity;

import com.sparta.todoproject_jwt.dto.TodoRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table
@NoArgsConstructor
public class Todo extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Column
    private boolean isCompleted;

    @Column
    private boolean isPrivate;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Todo(TodoRequestDto requestDto, User user) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContents();
        this.isCompleted = false;
        this.isPrivate = false;
        this.user = user;
    }


    public void update(TodoRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContents();
    }

    public void updateCompletion() {
        if(this.isCompleted)
            this.isCompleted = false;
        else
            this.isCompleted = true;
    }

    public void updateDisclosure() {
        if(this.isPrivate)
            this.isPrivate = false;
        else
            this.isPrivate = true;
    }
}

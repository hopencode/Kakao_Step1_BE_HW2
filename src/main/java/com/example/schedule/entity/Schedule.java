package com.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {

    private Long id;
    private String task;
    private String writer;
    private String email;
    private String password;
    private LocalDateTime createDateTime;
    private LocalDateTime updateDateTime;

    public Schedule(String task, String writer, String email,
                    String password, LocalDateTime createDateTime,
                    LocalDateTime updateDateTime) {

        this.task = task;
        this.writer = writer;
        this.email = email;
        this.password = password;
        this.createDateTime = createDateTime;
        this.updateDateTime = updateDateTime;
    }

    public void update(String task, LocalDateTime updateDateTime) {
        this.task = task;
        this.updateDateTime = updateDateTime;
    }

}

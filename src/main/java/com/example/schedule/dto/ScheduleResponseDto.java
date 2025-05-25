package com.example.schedule.dto;

import com.example.schedule.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {

    private Long id;
    private String task;
    private String writer;
    private String email;
    private LocalDateTime createDateTime;
    private LocalDateTime updateDateTime;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.task = schedule.getTask();
        this.writer = schedule.getWriter();
        this.email = schedule.getEmail();
        this.createDateTime = schedule.getCreateDateTime();
        this.updateDateTime = schedule.getUpdateDateTime();
    }

}

package com.example.schedule.controller;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }


    // Lv1. 일정 생성(일정 작성하기)
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@Valid @RequestBody ScheduleRequestDto dto) {

        return new ResponseEntity<>(scheduleService.saveSchedule(dto), HttpStatus.CREATED);
    }

    // Lv1 전체 일정 조회(등록된 일정 불러오기)
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getSchedules(
            @RequestParam(required = false) String email,   // Lv3 연관 관계 설정 (email을 통해 동명이인 구분, schedule.sql에서 작성자 - 일정 연결)
            @RequestParam(required = false) String date,
            @RequestParam(defaultValue = "0") int page,     // Lv4 페이지네이션 설정
            @RequestParam(defaultValue = "10") int size
    ) {

        return new ResponseEntity<>(scheduleService.findAllSchedules(email, date, page, size), HttpStatus.OK);
    }

    // Lv1 선택 일정 조회(선택한 일정 정보 불러오기)
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long id) {

        return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
    }

    // Lv2 선택한 일정 수정
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateScheduleById(
            @PathVariable Long id,
            @RequestBody ScheduleRequestDto dto
    ) {

        return new ResponseEntity<>(scheduleService.updateSchedule(id, dto.getTask(), dto.getPassword()), HttpStatus.OK);
    }

    // Lv2 선택한 일정 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScheduleById(
            @PathVariable Long id,
            @RequestParam String password
    ) {

        scheduleService.deleteSchedule(id, password);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}

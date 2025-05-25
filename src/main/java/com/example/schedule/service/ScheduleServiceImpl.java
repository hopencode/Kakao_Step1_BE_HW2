package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    // Lv1. 일정 생성(일정 작성하기)
    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {

        LocalDateTime currentDateTime = LocalDateTime.now();

        // 요청받은 데이터로 Schedule 객체 생성
        Schedule schedule = new Schedule(dto.getTask(), dto.getWriter(), dto.getEmail(),
                                        dto.getPassword(), currentDateTime, currentDateTime);

        return scheduleRepository.saveSchedule(schedule);
    }

    // Lv1 전체 일정 조회(등록된 일정 불러오기)
    @Override
    public List<ScheduleResponseDto> findAllSchedules(String email, String date, int page, int size) {

        return scheduleRepository.findAllSchedules(email, date, page, size);
    }

    // Lv1 선택 일정 조회(선택한 일정 정보 불러오기)
    @Override
    public ScheduleResponseDto findScheduleById(Long id) {

        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);
        /*
        Optional<Schedule> optionalSchedule = scheduleRepository.findScheduleById(id);

        if (optionalSchedule.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        return new ScheduleResponseDto(optionalSchedule.get());*/
        return new ScheduleResponseDto(schedule);
    }

    // Lv2 선택한 일정 수정
    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long id, String task, String password) {

        if (task == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task is required");
        }

        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);

        if (!schedule.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password does not match");
        }


        int updatedRow = scheduleRepository.updateSchedule(id, task);

        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        Schedule updatedSchedule = scheduleRepository.findScheduleByIdOrElseThrow(id);
        /*Optional<Schedule> optionalSchedule = scheduleRepository.findScheduleById(id);
        if (optionalSchedule.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        return new ScheduleResponseDto(optionalSchedule.get());*/
        return new ScheduleResponseDto(updatedSchedule);
    }

    // Lv2 선택한 일정 삭제
    @Override
    public void deleteSchedule(Long id, String password) {

        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);

        // 비밀번호 일치 확인
        // 비밀번호가 일치하지 않는 경우 에러
        if (!schedule.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password does not match");
        }

        int deletedRow = scheduleRepository.deleteSchedule(id);

        // 입력한 id로 조회되는 일정 존재 검사
        // 즉, 입력한 id에 해당하는 일정이 없는 경우 에러
        if (deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
    }
}

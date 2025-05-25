package com.example.schedule.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    @NotBlank(message = "할일은 필수 입력 항목입니다.")
    @Size(max = 200, message = "할일은 최대 200자까지 입력 가능합니다.")
    private String task;

    private String writer;

    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    private String password;

}

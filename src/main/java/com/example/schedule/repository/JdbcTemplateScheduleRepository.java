package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;


@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {


    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    // Lv1. 일정 생성(일정 작성하기)
    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("writer", schedule.getWriter());
        parameters.put("task", schedule.getTask());
        parameters.put("password", schedule.getPassword());
        parameters.put("created_at", schedule.getCreateDateTime());
        parameters.put("updated_at", schedule.getUpdateDateTime());
        parameters.put("writer_email", schedule.getEmail());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));


        return new ScheduleResponseDto(key.longValue(), schedule.getTask(), schedule.getWriter(),
                schedule.getEmail(), schedule.getCreateDateTime(), schedule.getUpdateDateTime());
    }

    // Lv1 전체 일정 조회(등록된 일정 불러오기)
    @Override
    public List<ScheduleResponseDto> findAllSchedules(String email, String date, int page, int size) {
        StringBuilder sql = new StringBuilder("SELECT * FROM schedule WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (email != null) {
            sql.append(" AND writer_email = ?");
            params.add(email);
        }
        if (date != null) {
            sql.append(" AND DATE(updated_at) = ?");
            params.add(date);
        }

        sql.append(" ORDER BY updated_at DESC LIMIT ? OFFSET ?");
        params.add(size);
        params.add(page * size);

        return jdbcTemplate.query(sql.toString(), scheduleRowMapper(), params.toArray());
    }

    // Lv1 선택 일정 조회(선택한 일정 정보 불러오기) Optional 버전인데 사용 안했음 - 그냥 다른 방법도 있었다 정도 학습용
    @Override
    public Optional<Schedule> findScheduleById(Long id) {

        List<Schedule> result = jdbcTemplate.query("SELECT * FROM schedule WHERE id = ?", scheduleRowMapperV2(), id);

        return result.stream().findAny();
    }

    // Lv1 선택 일정 조회(선택한 일정 정보 불러오기) <- 사용한 방법
    @Override
    public Schedule findScheduleByIdOrElseThrow(Long id) {

        List<Schedule> result = jdbcTemplate.query("SELECT * FROM schedule WHERE id = ?", scheduleRowMapperV2(), id);

        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    // Lv2 선택한 일정 수정
    @Override
    public int updateSchedule(Long id, String task) {
        LocalDateTime currentDateTime = LocalDateTime.now();

        return jdbcTemplate.update("UPDATE schedule SET task = ?, updated_at = ? WHERE id = ?", task, currentDateTime, id);
    }

    // Lv2 선택한 일정 삭제
    @Override
    public int deleteSchedule(Long id) {

        return jdbcTemplate.update("DELETE FROM schedule WHERE id = ?", id);
    }


    private RowMapper<ScheduleResponseDto> scheduleRowMapper() {

        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                        rs.getLong("id"),
                        rs.getString("task"),
                        rs.getString("writer"),
                        rs.getString("writer_email"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
            }
        };
    }

    private RowMapper<Schedule> scheduleRowMapperV2() {
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(
                        rs.getLong("id"),
                        rs.getString("task"),
                        rs.getString("writer"),
                        rs.getString("writer_email"),
                        rs.getString("password"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                );
            }
        };
    }
}

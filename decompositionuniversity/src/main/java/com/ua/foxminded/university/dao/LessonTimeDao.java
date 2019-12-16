package com.ua.foxminded.university.dao;

import com.ua.foxminded.university.domain.LessonTime;
import com.ua.foxminded.university.dao.mapper.LessonTimeMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.List;

@Repository
public class LessonTimeDao {

    private static final String ADD_TIME_QUERY = "INSERT INTO lesson_time (start_time, end_time) values (?,?) RETURNING id";
    private static final String GET_ALL_TIMES = "SELECT id, start_time, end_time FROM lesson_time";
    private static final String DELETE_TIME = "DELETE FROM lesson_time WHERE start_time = ?";

    private JdbcTemplate jdbcTemplate;
    private LessonTimeMapper lessonTimeMapper;

    public LessonTimeDao(JdbcTemplate jdbcTemplate, LessonTimeMapper lessonTimeMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.lessonTimeMapper = lessonTimeMapper;
    }

    public void add(LessonTime lessonTime) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        String[] fields = {"id"};
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_TIME_QUERY, fields);
            preparedStatement.setTime(1, Time.valueOf(lessonTime.getStartTime()));
            preparedStatement.setTime(2, Time.valueOf(lessonTime.getEndTime()));
            return preparedStatement;
        }, keyHolder);
        lessonTime.setId((Integer) keyHolder.getKeys().get("id"));
    }

    public List<LessonTime> getAll() {
        return jdbcTemplate.query(GET_ALL_TIMES, lessonTimeMapper);
    }

    public void delete(LessonTime lessonTime) {
        jdbcTemplate.update(DELETE_TIME, lessonTime.getStartTime());
    }
}
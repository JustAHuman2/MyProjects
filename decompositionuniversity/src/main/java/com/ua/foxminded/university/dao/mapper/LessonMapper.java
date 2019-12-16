package com.ua.foxminded.university.dao.mapper;

import com.ua.foxminded.university.domain.*;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LessonMapper implements RowMapper<Lesson> {

    @Override
    public Lesson mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Lesson(resultSet.getInt("id"), new Subject(resultSet.getString("subject_name")),
                new Teacher(resultSet.getString("teacher_name")), new Auditory(resultSet.getString("auditory_name")),
                new Group(resultSet.getString("group_name")), resultSet.getDate("lesson_date").toLocalDate(), new LessonTime(
                resultSet.getTime("start_time").toLocalTime(), resultSet.getTime("end_time").toLocalTime()));
    }
}


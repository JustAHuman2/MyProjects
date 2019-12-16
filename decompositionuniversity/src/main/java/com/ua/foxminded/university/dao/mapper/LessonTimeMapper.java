package com.ua.foxminded.university.dao.mapper;

import com.ua.foxminded.university.domain.LessonTime;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LessonTimeMapper implements RowMapper<LessonTime> {

    @Override
    public LessonTime mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new LessonTime(resultSet.getInt("id"), resultSet.getTime("start_time").toLocalTime(), resultSet.getTime("end_time").toLocalTime());
    }
}
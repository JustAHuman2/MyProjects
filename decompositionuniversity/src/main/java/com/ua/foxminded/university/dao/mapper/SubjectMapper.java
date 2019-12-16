package com.ua.foxminded.university.dao.mapper;

import com.ua.foxminded.university.domain.Subject;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectMapper implements RowMapper<Subject> {

    @Override
    public Subject mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Subject(resultSet.getInt("id"), resultSet.getString("subject_name"));
    }
}
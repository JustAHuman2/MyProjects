package com.ua.foxminded.university.dao.mapper;

import com.ua.foxminded.university.domain.Student;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Student(resultSet.getInt("id"), resultSet.getString("student_name"), resultSet.getInt("group_id"));
    }
}

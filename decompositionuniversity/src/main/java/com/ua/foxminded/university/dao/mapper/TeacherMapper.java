package com.ua.foxminded.university.dao.mapper;

import com.ua.foxminded.university.domain.Subject;
import com.ua.foxminded.university.domain.Teacher;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherMapper implements RowMapper<Teacher> {

    @Override
    public Teacher mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        List<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject(resultSet.getInt("id"), resultSet.getString("subject_name")));
        return new Teacher(resultSet.getInt("id"), resultSet.getString("teacher_name"), subjects);
    }
}
package com.ua.foxminded.university.dao;

import com.ua.foxminded.university.dao.mapper.StudentMapper;
import com.ua.foxminded.university.domain.Student;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class StudentDao {

    private static final String ADD_STUDENT_QUERY = "INSERT INTO students (student_name, group_id) values (?,?)";
    private static final String GET_ALL_STUDENTS = "SELECT id, student_name, group_id FROM students";
    private static final String DELETE_STUDENT = "DELETE FROM students WHERE student_name = ?";

    private JdbcTemplate jdbcTemplate;
    private StudentMapper studentMapper;

    public StudentDao(JdbcTemplate jdbcTemplate, StudentMapper studentMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.studentMapper = studentMapper;
    }

    public void add(Student student) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        String[] fields = {"id"};
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_STUDENT_QUERY, fields);
            preparedStatement.setString(1, student.getName());
            preparedStatement.setInt(2, student.getGroupId());
            return preparedStatement;
        }, keyHolder);
        student.setId((Integer) keyHolder.getKeys().get("id"));
    }

    public List<Student> getAll() {
        return jdbcTemplate.query(GET_ALL_STUDENTS, studentMapper);
    }

    public void delete(Student student) {
        jdbcTemplate.update(DELETE_STUDENT, student.getName());
    }
}
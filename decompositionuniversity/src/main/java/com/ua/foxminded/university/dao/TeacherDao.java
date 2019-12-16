package com.ua.foxminded.university.dao;

import com.ua.foxminded.university.domain.Teacher;
import com.ua.foxminded.university.dao.mapper.TeacherMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TeacherDao {

    private static final String ADD_TEACHER_QUERY = "INSERT INTO teachers (teacher_name, subject_id) values (?,?)";
    private static final String GET_ALL_TEACHERS = "SELECT teachers.id, teacher_name, subject_name FROM teachers, subjects WHERE teachers.subject_id = subjects.id";
    private static final String DELETE_TEACHER = "DELETE FROM teachers WHERE teacher_name = ?";

    private JdbcTemplate jdbcTemplate;
    private TeacherMapper teacherMapper;

    public TeacherDao(JdbcTemplate jdbcTemplate, TeacherMapper teacherMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.teacherMapper = teacherMapper;
    }

    public void add(Teacher teacher) {
        teacher.getSubjects().forEach(s -> jdbcTemplate.update(ADD_TEACHER_QUERY, teacher.getName(), s.getId()));
    }

    public List<Teacher> getAll() {
        return jdbcTemplate.query(GET_ALL_TEACHERS, teacherMapper);
    }

    public void delete(Teacher teacher) {
        jdbcTemplate.update(DELETE_TEACHER, teacher.getName());
    }
}
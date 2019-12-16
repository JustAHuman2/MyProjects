package com.ua.foxminded.university.dao;

import com.ua.foxminded.university.domain.Subject;
import com.ua.foxminded.university.dao.mapper.SubjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class SubjectDao {

    private static final String ADD_SUBJECT_QUERY = "INSERT INTO subjects (subject_name) values (?) returning id";
    private static final String GET_ALL_SUBJECTS = "SELECT id, subject_name FROM subjects";
    private static final String DELETE_SUBJECT = "DELETE FROM subjects WHERE subject_name = ?";

    private JdbcTemplate jdbcTemplate;
    private SubjectMapper subjectMapper;

    public SubjectDao(JdbcTemplate jdbcTemplate, SubjectMapper subjectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.subjectMapper = subjectMapper;
    }

    public void add(Subject subject) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        String[] fields = {"id"};
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_SUBJECT_QUERY, fields);
            preparedStatement.setString(1, subject.getName());
            return preparedStatement;
        }, keyHolder);
        subject.setId((Integer) keyHolder.getKeys().get("id"));
    }

    public List<Subject> getAll() {
        return jdbcTemplate.query(GET_ALL_SUBJECTS, subjectMapper);
    }

    public void delete(Subject subject) {
        jdbcTemplate.update(DELETE_SUBJECT, subject.getName());
    }
}
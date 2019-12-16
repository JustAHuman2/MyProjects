package com.ua.foxminded.university.dao;

import com.ua.foxminded.university.domain.Auditory;
import com.ua.foxminded.university.dao.mapper.AuditoryMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class AuditoryDao {

    private static final String ADD_AUDITORY_QUERY = "INSERT INTO auditories (auditory_name) VALUES (?)";
    private static final String GET_ALL_AUDITORIES = "SELECT id, auditory_name FROM auditories";
    private static final String DELETE_AUDITORY = "DELETE FROM auditories WHERE auditory_name = ?";

    private JdbcTemplate jdbcTemplate;
    private AuditoryMapper auditoryMapper;

    public AuditoryDao(JdbcTemplate jdbcTemplate, AuditoryMapper auditoryMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.auditoryMapper = auditoryMapper;
    }

    public void add(Auditory auditory) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        String[] fields = {"id"};
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_AUDITORY_QUERY, fields);
            preparedStatement.setString(1, auditory.getName());
            return preparedStatement;
        }, keyHolder);
        auditory.setId((Integer) keyHolder.getKeys().get("id"));
    }

    public List<Auditory> getAll() {
        return jdbcTemplate.query(GET_ALL_AUDITORIES, auditoryMapper);
    }

    public void delete(Auditory auditory) {
        jdbcTemplate.update(DELETE_AUDITORY, auditory.getName());
    }
}
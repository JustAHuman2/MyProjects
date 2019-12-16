package com.ua.foxminded.university.dao.mapper;

import com.ua.foxminded.university.domain.Auditory;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuditoryMapper implements RowMapper<Auditory> {

    @Override
    public Auditory mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Auditory(resultSet.getInt("id"), resultSet.getString("auditory_name"));
    }
}
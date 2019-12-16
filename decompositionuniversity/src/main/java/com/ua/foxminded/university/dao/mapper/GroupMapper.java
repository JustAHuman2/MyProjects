package com.ua.foxminded.university.dao.mapper;

import com.ua.foxminded.university.domain.Group;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupMapper implements RowMapper<Group> {

    @Override
    public Group mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Group(resultSet.getInt("id"), resultSet.getString("group_name"));
    }
}

package com.ua.foxminded.university.dao;

import java.sql.PreparedStatement;
import java.util.List;

import com.ua.foxminded.university.dao.mapper.GroupMapper;
import com.ua.foxminded.university.domain.Group;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class GroupDao {

    private static final String ADD_GROUP_QUERY = "INSERT INTO groups (group_name) VALUES (?) RETURNING id";
    private static final String GET_ALL_GROUPS = "SELECT id, group_name FROM groups";
    private static final String DELETE_GROUP = "DELETE FROM groups WHERE group_name = ?";

    private JdbcTemplate jdbcTemplate;
    private GroupMapper groupMapper;

    public GroupDao(JdbcTemplate jdbcTemplate, GroupMapper groupMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.groupMapper = groupMapper;
    }

    public void add(Group group) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        String[] fields = {"id"};
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_GROUP_QUERY, fields);
            preparedStatement.setString(1, group.getName());
            return preparedStatement;
        }, keyHolder);
        group.setId((Integer) keyHolder.getKeys().get("id"));
    }

    public List<Group> getAll() {
        return jdbcTemplate.query(GET_ALL_GROUPS, groupMapper);
    }

    public void delete(Group group) {
        jdbcTemplate.update(DELETE_GROUP, group.getName());
    }
}
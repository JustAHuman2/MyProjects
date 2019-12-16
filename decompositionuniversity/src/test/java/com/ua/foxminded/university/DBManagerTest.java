package com.ua.foxminded.university;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class DBManagerTest {

    private static final String SQL_GET_ALL = "SELECT tablename FROM pg_catalog.pg_tables where schemaname = 'public' ORDER BY tablename";
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DBManager dbManager;

    @Test
    @Rollback
    public void givenSchema_whenCreateTables_thenTables() {
        List<String> expected = asList("auditories", "groups", "lesson_time", "lessons", "students", "subjects", "teachers");

        dbManager.createTables("schema.sql");

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(SQL_GET_ALL);
        List<String> actual = new ArrayList<>();
        while (rowSet.next()) {
            actual.add(rowSet.getString("tablename"));
        }
        assertEquals(expected, actual);
    }
}
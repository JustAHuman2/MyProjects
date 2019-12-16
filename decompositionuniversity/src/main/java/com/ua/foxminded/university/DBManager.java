package com.ua.foxminded.university;

import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DBManager {

    private JdbcTemplate jdbcTemplate;

    public DBManager(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createTables(String fileName) {
        try {
            jdbcTemplate.update(readFile(fileName).collect(Collectors.joining("")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Stream<String> readFile(String fileName) throws IOException {
        return Files.lines(new File(ClassLoader.getSystemClassLoader().getResource(fileName).getFile()).toPath());
    }
}

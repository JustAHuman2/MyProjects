package com.ua.foxminded.university.dao;

import com.ua.foxminded.university.TestConfig;
import com.ua.foxminded.university.domain.Subject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class SubjectDaoTest {

    private final String ADD_SUBJECT = "INSERT INTO subjects (subject_name) VALUES (?)";
    @Autowired
    private SubjectDao subjectDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() throws IOException {
        jdbcTemplate.execute(Files.lines(new File(ClassLoader.getSystemClassLoader()
                .getResource("schema.sql").getFile()).toPath()).collect(Collectors.joining("")));
    }

    @Test
    @Rollback
    public void givenEmptyTable_whenAdd_thenSubjectAdded() {
        int expected = 3;

        asList("Math", "Latin", "English").stream().map(Subject::new).forEach(subjectDao::add);

        int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "subjects");
        assertEquals(expected, actual);
    }

    @Test
    @Rollback
    public void givenEmptyTable_whenGetAll_thenEmptyList() {
        List<Subject> expected = new ArrayList<>();

        List<Subject> actual = subjectDao.getAll();

        assertEquals(expected, actual);
    }

    @Test
    @Rollback
    public void givenExistingSubjects_whenGetAll_thenSubjects() {
        List<Subject> expected = asList("Math", "Latin", "English").stream()
                .map(Subject::new).peek(sub -> jdbcTemplate.update(ADD_SUBJECT, sub.getName())).collect(toList());

        List<Subject> actual = subjectDao.getAll();

        assertEquals(expected, actual);
    }

    @Test
    @Rollback
    public void givenExistingSubject_whenDelete_thenDeleted() {
        int expected = 0;
        Subject subject = new Subject("Physics");
        jdbcTemplate.update(ADD_SUBJECT, subject.getName());

        subjectDao.delete(subject);

        int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "subjects");
        assertEquals(expected, actual);
    }

    @Test
    @Rollback
    public void givenEmptyTable_whenDelete_thenEmptyTable() {
        int expected = 0;

        subjectDao.delete(new Subject("Latin"));

        int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "subjects");
        assertEquals(expected, actual);
    }
}
package com.ua.foxminded.university.dao;

import com.ua.foxminded.university.TestConfig;
import com.ua.foxminded.university.domain.Student;
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
public class StudentDaoTest {

    private final String ADD_STUDENT = "INSERT INTO students (student_name, group_id) values (?,?)";
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() throws IOException {
        jdbcTemplate.execute(Files.lines(new File(ClassLoader.getSystemClassLoader()
                .getResource("schema.sql").getFile()).toPath()).collect(Collectors.joining("")));

    }

    @Test
    @Rollback
    public void givenEmptyTable_whenAdd_thenStudentsAdded() {
        int expected = 2;
        Student studentOne = new Student("studentOne", 1);
        Student studentTwo = new Student("studentTwo", 2);

        asList(studentOne, studentTwo).forEach(studentDao::add);

        int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "students");
        assertEquals(expected, actual);
    }

    @Test
    @Rollback
    public void givenEmptyTable_whenGetAll_thenEmptyList() {
        List<Student> expected = new ArrayList<>();

        List<Student> actual = studentDao.getAll();

        assertEquals(expected, actual);
    }

    @Test
    @Rollback
    public void givenExistingStudents_whenGetAll_thenStudents() {
        Student studentOne = new Student("studentOne", 1);
        Student studentTwo = new Student("studentTwo", 2);
        List<Student> expected = asList(studentOne, studentTwo).stream()
                .peek(s -> jdbcTemplate.update(ADD_STUDENT, s.getName(), s.getGroupId())).collect(toList());

        List<Student> actual = studentDao.getAll();

        assertEquals(expected, actual);
    }

    @Test
    @Rollback
    public void givenExistingStudent_whenDelete_thenDeleted() {
        int expected = 0;
        Student studentOne = new Student("studentOne", 1);
        jdbcTemplate.update(ADD_STUDENT, "studentOne", 1);

        studentDao.delete(studentOne);

        int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "students");
        assertEquals(expected, actual);
    }

    @Test
    @Rollback
    public void givenEmptyTable_whenDelete_thenEmptyTable() {
        int expected = 0;

        studentDao.delete(new Student("studentOne"));

        int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "students");
        assertEquals(expected, actual);
    }
}
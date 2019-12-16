package com.ua.foxminded.university.dao;

import com.ua.foxminded.university.TestConfig;
import com.ua.foxminded.university.domain.Subject;
import com.ua.foxminded.university.domain.Teacher;
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
public class TeacherDaoTest {

    private static final String ADD_TEACHER = "INSERT INTO teachers (teacher_name, subject_id) values (?,?)";
    private static final String ADD_SUBJECT = "INSERT INTO subjects (subject_name) values (?)";
    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private SubjectDao subjectDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private Subject subjectOne;
    private Subject subjectTwo;

    @Before
    public void setUp() throws IOException {
        jdbcTemplate.execute(Files.lines(new File(ClassLoader.getSystemClassLoader()
                .getResource("schema.sql").getFile()).toPath()).collect(Collectors.joining("")));
        subjectOne = new Subject(1, "Math");
        subjectTwo = new Subject(2, "Latin");
    }

    @Test
    @Rollback
    public void givenEmptyTable_whenAdd_thenTeachersAdded() {
        int expected = 2;
        asList(subjectOne, subjectTwo).forEach(subjectDao::add);

        asList(new Teacher("teacherOne", asList(subjectOne)), new Teacher("teacherOne", asList(subjectTwo))).forEach(teacherDao::add);

        int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "teachers");
        assertEquals(expected, actual);
    }

    @Test
    @Rollback
    public void givenEmptyTable_whenGetAll_thenEmptyList() {
        List<Teacher> expected = new ArrayList<>();

        List<Teacher> actual = teacherDao.getAll();

        assertEquals(expected, actual);
    }

    @Test
    @Rollback
    public void givenExistingTeachers_whenGetAll_thenTeachers() {
        asList(subjectOne, subjectTwo).forEach(s -> jdbcTemplate.update(ADD_SUBJECT, s.getName()));
        List<Teacher> expected = asList(new Teacher("teacherOne", asList(subjectOne)), new Teacher("teacherOne", asList(subjectTwo)))
                .stream().peek(t -> jdbcTemplate.update(ADD_TEACHER, t.getName(), t.getSubjects().get(0).getId())).collect(toList());

        List<Teacher> actual = teacherDao.getAll();

        assertEquals(expected, actual);
    }

    @Test
    @Rollback
    public void givenExistingTeacher_whenDelete_thenDeleted() {
        int expected = 0;
        jdbcTemplate.update(ADD_SUBJECT, subjectOne.getName());
        Teacher teacher = new Teacher("teacherOne", asList(subjectOne));
        jdbcTemplate.update(ADD_TEACHER, teacher.getName(), teacher.getSubjects().get(0).getId());

        teacherDao.delete(teacher);

        int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "teachers");
        assertEquals(expected, actual);
    }

    @Test
    @Rollback
    public void givenEmptyTable_whenDelete_thenEmptyTable() {
        int expected = 0;

        teacherDao.delete(new Teacher("TeacherName"));

        int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "teachers");
        assertEquals(expected, actual);
    }
}
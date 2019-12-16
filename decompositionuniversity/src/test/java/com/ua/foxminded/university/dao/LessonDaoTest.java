package com.ua.foxminded.university.dao;

import com.ua.foxminded.university.TestConfig;
import com.ua.foxminded.university.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class LessonDaoTest {

    private static final String ADD_STUDENT_QUERY = "INSERT INTO lessons (subject_id, teacher_id, auditory_id, group_id, lesson_date, lesson_time_id) VALUES (?,?,?,?,?,?)";
    private static final String ADD_SUBJECT = "INSERT INTO subjects (subject_name) values (?)";
    private static final String ADD_AUDITORY = "INSERT INTO auditories (auditory_name) VALUES (?)";
    private static final String ADD_TEACHER = "INSERT INTO teachers (teacher_name, subject_id) values (?,?)";
    private static final String ADD_GROUP = "INSERT INTO groups (group_name) VALUES (?)";
    private static final String ADD_LESSON = "INSERT INTO lessons (subject_id, teacher_id, auditory_id, group_id, lesson_date, lesson_time_id) VALUES (?,?,?,?,?,?)";
    private static final String ADD_LESSON_TIME = "INSERT INTO lesson_time (start_time, end_time) values (?,?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private LessonDao lessonDao;
    private LocalDate dayOne;
    private LocalDate dayTwo;
    private Auditory auditoryOne;
    private Auditory auditoryTwo;
    private LessonTime lessonTimeOne;
    private LessonTime lessonTimeTwo;
    private Subject subjectOne;
    private Subject subjectTwo;
    private Teacher teacherOne;
    private Teacher teacherTwo;
    private Group groupOne;
    private Group groupTwo;

    @Before
    public void setUp() throws IOException {
        jdbcTemplate.execute(Files.lines(new File(ClassLoader.getSystemClassLoader()
                .getResource("schema.sql").getFile()).toPath()).collect(Collectors.joining("")));
        dayOne = LocalDate.of(2019, 11, 1);
        dayTwo = LocalDate.of(2019, 11, 2);
        auditoryOne = new Auditory(1, "A");
        auditoryTwo = new Auditory(2, "B");
        lessonTimeOne = new LessonTime(1, LocalTime.of(8, 30), LocalTime.of(10, 00));
        lessonTimeTwo = new LessonTime(2, LocalTime.of(10, 15), LocalTime.of(11, 45));
        subjectOne = new Subject(1, "Anatomy");
        subjectTwo = new Subject(2, "Biology");
        teacherOne = new Teacher(1, "TeacherOne", asList(subjectOne));
        teacherTwo = new Teacher(2, "TeacherTwo", asList(subjectTwo));
        groupOne = new Group(1, "groupOne");
        groupTwo = new Group(2, "groupTwo");
        asList(subjectOne, subjectTwo).forEach(s -> jdbcTemplate.update(ADD_SUBJECT, s.getName()));
        asList(auditoryOne, auditoryTwo).forEach(a -> jdbcTemplate.update(ADD_AUDITORY, a.getName()));
        asList(groupOne, groupTwo).forEach(g -> jdbcTemplate.update(ADD_GROUP, g.getName()));
        asList(teacherOne, teacherTwo).forEach(t -> t.getSubjects().forEach(s -> jdbcTemplate.update(ADD_TEACHER, t.getName(), s.getId())));
        asList(lessonTimeOne, lessonTimeTwo).forEach(lt -> jdbcTemplate.update(ADD_LESSON_TIME, lt.getStartTime(), lt.getEndTime()));
    }

    @Test
    @Rollback
    public void givenEmptyTable_whenAdd_thenLessonsAdded() {
        int expected = 2;
        Lesson lessonOne = new Lesson(1, subjectOne, teacherOne, auditoryOne, groupOne, dayOne, lessonTimeOne);
        Lesson lessonTwo = new Lesson(2, subjectTwo, teacherTwo, auditoryTwo, groupTwo, dayTwo, lessonTimeTwo);
        asList(lessonOne, lessonTwo).forEach(lessonDao::add);

        int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "lessons");

        assertEquals(expected, actual);
    }

    @Test
    @Rollback
    public void givenEmptyTable_whenGetAll_thenEmptyList() {
        List<Lesson> expected = new ArrayList<>();

        List<Lesson> actual = lessonDao.getAll();

        assertEquals(expected, actual);
    }

    @Test
    @Rollback
    public void givenExistingLesson_whenGetAll_thenLessons() {
        Lesson lessonOne = new Lesson(1, subjectOne, teacherOne, auditoryOne, groupOne, dayOne, lessonTimeOne);
        Lesson lessonTwo = new Lesson(2, subjectTwo, teacherTwo, auditoryTwo, groupTwo, dayTwo, lessonTimeTwo);
        List<Lesson> expected = asList(lessonOne, lessonTwo);
        expected.forEach(l -> jdbcTemplate.update(ADD_LESSON, l.getSubject().getId(), l.getTeacher().getId(),
                l.getAuditory().getId(), l.getGroup().getId(), l.getDate(), l.getLessonTime().getId()));

        List<Lesson> actual = lessonDao.getAll();

        assertEquals(expected, actual);
    }

    @Test
    @Rollback
    public void givenExistingLesson_whenDelete_thenDeleted() {
        List<LessonTime> expected = new ArrayList<>();
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        String[] fields = {"id"};
        Integer lessonId;
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_STUDENT_QUERY, fields);
            preparedStatement.setInt(1, subjectOne.getId());
            preparedStatement.setInt(2, teacherOne.getId());
            preparedStatement.setInt(3, auditoryOne.getId());
            preparedStatement.setInt(4, groupOne.getId());
            preparedStatement.setDate(5, Date.valueOf(dayOne));
            preparedStatement.setInt(6, lessonTimeOne.getId());
            return preparedStatement;
        }, keyHolder);
        lessonId = (Integer) keyHolder.getKeys().get("id");

        lessonDao.delete(lessonId);

        List<Lesson> actual = lessonDao.getAll();
        assertEquals(expected, actual);
    }

    @Test
    @Rollback
    public void givenEmptyTable_whenDelete_thenEmptyTable() {
        List<Group> expected = new ArrayList<>();

        lessonDao.delete(1);

        List<Lesson> actual = lessonDao.getAll();
        assertEquals(expected, actual);
    }
}
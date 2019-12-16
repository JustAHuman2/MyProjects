package com.ua.foxminded.university.dao;

import com.ua.foxminded.university.TestConfig;
import com.ua.foxminded.university.domain.LessonTime;
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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class LessonTimeDaoTest {

    private final String ADD_LESSON_TIME = "INSERT INTO lesson_time (start_time, end_time) values (?,?)";
    @Autowired
    private LessonTimeDao lessonTimeDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private LessonTime lessonTimeOne;
    private LessonTime lessonTimeTwo;

    @Before
    public void setUp() throws IOException {
        jdbcTemplate.execute(Files.lines(new File(ClassLoader.getSystemClassLoader()
                .getResource("schema.sql").getFile()).toPath()).collect(Collectors.joining("")));
        lessonTimeOne = new LessonTime(LocalTime.of(8, 30), LocalTime.of(10, 00));
        lessonTimeTwo = new LessonTime(LocalTime.of(10, 15), LocalTime.of(11, 45));
    }

    @Test
    @Rollback
    public void givenEmptyTable_whenAdd_thenLessonTimeAdded() {
        int expected = 2;

        asList(lessonTimeOne, lessonTimeTwo).forEach(lessonTimeDao::add);

        int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "lesson_time");
        assertEquals(expected, actual);
    }

    @Test
    @Rollback
    public void givenEmptyTable_whenGetAll_thenEmptyList() {
        List<LessonTime> expected = new ArrayList<>();

        List<LessonTime> actual = lessonTimeDao.getAll();

        assertEquals(expected, actual);
    }

    @Test
    @Rollback
    public void givenExistingLessonTimes_whenGetAll_thenLessonTimes() {
        List<LessonTime> expected = asList(lessonTimeOne, lessonTimeTwo).stream()
                .peek(lt -> jdbcTemplate.update(ADD_LESSON_TIME, lt.getStartTime(), lt.getEndTime())).collect(toList());

        List<LessonTime> actual = lessonTimeDao.getAll();

        assertEquals(expected, actual);
    }

    @Test
    @Rollback
    public void givenExistingLessonTime_whenDelete_thenDeleted() {
        int expected = 0;
        jdbcTemplate.update(ADD_LESSON_TIME, lessonTimeOne.getStartTime(), lessonTimeOne.getEndTime());

        lessonTimeDao.delete(lessonTimeOne);

        int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "lesson_time");
        assertEquals(expected, actual);
    }

    @Test
    @Rollback
    public void givenEmptyTable_whenDelete_thenEmptyTable() {
        int expected = 0;

        lessonTimeDao.delete(lessonTimeOne);

        int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "lesson_time");
        assertEquals(expected, actual);
    }
}
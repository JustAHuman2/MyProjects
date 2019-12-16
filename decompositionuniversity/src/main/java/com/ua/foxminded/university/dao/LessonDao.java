package com.ua.foxminded.university.dao;

import com.ua.foxminded.university.dao.mapper.LessonMapper;
import com.ua.foxminded.university.domain.Group;
import com.ua.foxminded.university.domain.Lesson;
import com.ua.foxminded.university.domain.Teacher;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;

@Repository
public class LessonDao {

    private static final String ADD_LESSON_QUERY = "INSERT INTO lessons (subject_id, teacher_id, auditory_id, group_id, lesson_date, lesson_time_id) VALUES (?,?,?,?,?,?) RETURNING id";
    private static final String GET_GROUP_LESSONS = "SELECT lessons.id, subject_name, teacher_name, auditory_name, group_name, lesson_date, start_time, end_time FROM subjects, teachers, auditories, groups, lessons, lesson_time WHERE group_name = ? AND lesson_date >= ? AND lesson_date < ?";
    private static final String GET_TEACHER_LESSONS = "SELECT lessons.id, subject_name, teacher_name, auditory_name, group_name, lesson_date, start_time, end_time FROM subjects, teachers, auditories, groups, lessons, lesson_time WHERE teacher_name = ? AND lesson_date >= ? AND lesson_date < ?";
    private static final String GET_ALL_LESSONS = "SELECT lessons.id, subject_name, teacher_name, auditory_name, group_name, lesson_date, start_time, end_time FROM subjects, teachers, auditories, groups, lessons, lesson_time WHERE  lessons.subject_id = subjects.id AND teacher_id = teachers.id AND auditory_id = auditories.id AND group_id = groups.id AND lesson_time_id = lesson_time.id";
    private static final String DELETE_LESSON = "DELETE FROM lessons WHERE id = ?";

    private JdbcTemplate jdbcTemplate;
    private LessonMapper lessonMapper;

    public LessonDao(JdbcTemplate jdbcTemplate, LessonMapper lessonMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.lessonMapper = lessonMapper;
    }

    public void add(Lesson lesson) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        String[] fields = {"id"};
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_LESSON_QUERY, fields);
            preparedStatement.setInt(1, lesson.getSubject().getId());
            preparedStatement.setInt(2, lesson.getTeacher().getId());
            preparedStatement.setInt(3, lesson.getAuditory().getId());
            preparedStatement.setInt(4, lesson.getGroup().getId());
            preparedStatement.setDate(5, Date.valueOf(lesson.getDate()));
            preparedStatement.setInt(6, lesson.getLessonTime().getId());
            return preparedStatement;
        }, keyHolder);
        lesson.setId((Integer) keyHolder.getKeys().get("id"));
    }

    public List<Lesson> getGroupLessons(Group group, LocalDate beginDate, LocalDate endDate) {
        return jdbcTemplate.query(GET_GROUP_LESSONS, lessonMapper, group.getName(), beginDate, endDate);
    }

    public List<Lesson> getTeacherLessons(Teacher teacher, LocalDate beginDate, LocalDate endDate) {
        return jdbcTemplate.query(GET_TEACHER_LESSONS, lessonMapper, teacher, beginDate, endDate);
    }

    public List<Lesson> getAll() {
        return jdbcTemplate.query(GET_ALL_LESSONS, lessonMapper);
    }

    public void delete(int id) {
        jdbcTemplate.update(DELETE_LESSON, id);
    }
}
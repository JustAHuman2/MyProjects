package com.ua.foxminded.university.service;

import com.ua.foxminded.university.dao.*;
import com.ua.foxminded.university.domain.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class LessonService {

    private LessonDao lessonDao;
    private LessonTimeDao lessonTimeDao;
    private AuditoryDao auditoryDao;
    private TeacherDao teacherDao;
    private GroupDao groupDao;
    private TeacherService teacherService;
    private GroupService groupService;

    public LessonService(LessonDao lessonDao, LessonTimeDao lessonTimeDao, AuditoryDao auditoryDao, TeacherDao teacherDao,
                         GroupDao groupDao, TeacherService teacherService, GroupService groupService) {
        this.lessonDao = lessonDao;
        this.lessonTimeDao = lessonTimeDao;
        this.auditoryDao = auditoryDao;
        this.teacherDao = teacherDao;
        this.groupDao = groupDao;
        this.teacherService = teacherService;
        this.groupService = groupService;
    }

    public void generateSchedule(LocalDate beginDate, LocalDate endDate) {
        List<Lesson> lessons = getSchedule(
                getAcademicDays(beginDate, endDate), lessonTimeDao.getAll(),
                auditoryDao.getAll());
        lessons = teacherService.setTeachersAndSubjects(lessons, teacherDao.getAll(), auditoryDao.getAll());
        lessons = groupService.setGroups(lessons, groupDao.getAll(), auditoryDao.getAll());
        lessons.forEach(lessonDao::add);
    }

    private List<Lesson> getSchedule(List<LocalDate> academicDays, List<LessonTime> daySchedule, List<Auditory> auditories) {
        List<Lesson> schedule = new ArrayList<>();
        for (LocalDate day : academicDays) {
            for (LessonTime lessonTime : daySchedule) {
                for (Auditory auditory : auditories) {
                    schedule.add(new Lesson(day, lessonTime, auditory));
                }
            }
        }
        return schedule;
    }

    private List<LocalDate> getAcademicDays(LocalDate startDate, LocalDate endDate) {
        return IntStream.iterate(0, i -> i + 1).limit(ChronoUnit.DAYS.between(startDate, endDate))
                .mapToObj(startDate::plusDays).filter(d -> d.getDayOfWeek().name() != "SUNDAY")
                .collect(Collectors.toList());
    }

    public List<Lesson> getGroupLessons(Group group, LocalDate beginDate, LocalDate endDate) {
        return lessonDao.getGroupLessons(group, beginDate, endDate);
    }

    public List<Lesson> getTeacherLessons(Teacher teacher, LocalDate beginDate, LocalDate endDate) {
        return lessonDao.getTeacherLessons(teacher, beginDate, endDate);
    }

    public void add(Lesson lesson) {
        lessonDao.add(lesson);
    }

    public List<Lesson> getAll() {
        return lessonDao.getAll();
    }

    public void delete(int id) {
        lessonDao.delete(id);
    }
}

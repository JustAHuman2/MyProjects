package com.ua.foxminded.university.service;

import com.ua.foxminded.university.dao.LessonTimeDao;
import com.ua.foxminded.university.domain.LessonTime;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class LessonTimeService {

    private LessonTimeDao lessonTimeDao;

    public LessonTimeService(LessonTimeDao lessonTimeDao) {
        this.lessonTimeDao = lessonTimeDao;
    }

    public List<LocalDate> getAcademicDays(LocalDate startDate, LocalDate endDate) {
        return IntStream.iterate(0, i -> i + 1).limit(ChronoUnit.DAYS.between(startDate, endDate))
                .mapToObj(startDate::plusDays).filter(d -> d.getDayOfWeek().name() != "SUNDAY")
                .collect(Collectors.toList());
    }

    public void generateLessonTimes() {
        Arrays.asList(new LessonTime(LocalTime.of(8, 30), LocalTime.of(10, 00)),
                new LessonTime(LocalTime.of(10, 15), LocalTime.of(11, 45)),
                new LessonTime(LocalTime.of(12, 00), LocalTime.of(13, 30)),
                new LessonTime(LocalTime.of(14, 15), LocalTime.of(15, 45)),
                new LessonTime(LocalTime.of(16, 00), LocalTime.of(17, 30)),
                new LessonTime(LocalTime.of(17, 45), LocalTime.of(19, 15))).forEach(lessonTimeDao::add);
    }

    public void add(LessonTime lessonTime) {
        lessonTimeDao.add(lessonTime);
    }

    public List<LessonTime> getAll() {
        return lessonTimeDao.getAll();
    }

    public void delete(LessonTime lessonTime) {
        lessonTimeDao.delete(lessonTime);
    }
}
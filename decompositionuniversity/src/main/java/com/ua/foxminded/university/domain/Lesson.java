package com.ua.foxminded.university.domain;

import java.time.LocalDate;

public class Lesson {

    private int id;
    private Subject subject;
    private Teacher teacher;
    private Auditory auditory;
    private Group group;
    private LocalDate date;
    private LessonTime lessonTime;

    public Lesson(int id, Subject subject, Teacher teacher, Auditory auditory, Group group, LocalDate date, LessonTime lessonTime) {
        this.id = id;
        this.subject = subject;
        this.teacher = teacher;
        this.auditory = auditory;
        this.group = group;
        this.date = date;
        this.lessonTime = lessonTime;
    }

    public Lesson(LocalDate date, LessonTime lessonTime, Auditory auditory) {
        this.date = date;
        this.lessonTime = lessonTime;
        this.auditory = auditory;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setAuditory(Auditory auditory) {
        this.auditory = auditory;
    }

    public Auditory getAuditory() {
        return auditory;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public LessonTime getLessonTime() {
        return lessonTime;
    }

    public void setLessonTime(LessonTime lessonTime) {
        this.lessonTime = lessonTime;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", subject=" + subject +
                ", teacher=" + teacher +
                ", auditory=" + auditory +
                ", group=" + group +
                ", date=" + date +
                ", lessonTime=" + lessonTime +
                '}';
    }

    @Override
    public int hashCode() {
        return ((auditory == null) ? 0 : auditory.hashCode()) + ((date == null) ? 0 : date.hashCode())
                + ((group == null) ? 0 : group.hashCode()) + ((lessonTime == null) ? 0 : lessonTime.hashCode())
                + ((subject == null) ? 0 : subject.hashCode()) + ((teacher == null) ? 0 : teacher.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Lesson) || (this.hashCode() != obj.hashCode()))
            return false;
        if (this == obj)
            return true;
        Lesson thatLesson = (Lesson) obj;
        if (((this.getAuditory().equals(thatLesson.getAuditory()))
                && (this.date.equals(thatLesson.getDate())) && (this.lessonTime.equals(thatLesson.getLessonTime())))) {
            return true;
        }
        return false;
    }
}
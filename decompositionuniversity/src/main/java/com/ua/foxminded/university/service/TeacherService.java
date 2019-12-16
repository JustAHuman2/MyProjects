package com.ua.foxminded.university.service;

import com.ua.foxminded.university.dao.SubjectDao;
import com.ua.foxminded.university.dao.TeacherDao;
import com.ua.foxminded.university.domain.Auditory;
import com.ua.foxminded.university.domain.Lesson;
import com.ua.foxminded.university.domain.Subject;
import com.ua.foxminded.university.domain.Teacher;

import java.util.List;

import static java.util.Arrays.asList;

@Service
public class TeacherService {

    private TeacherDao teacherDao;
    private SubjectDao subjectDao;

    public TeacherService(TeacherDao teacherDao, SubjectDao subjectDao) {
        this.teacherDao = teacherDao;
        this.subjectDao = subjectDao;
    }

    public void generateTeachers() {
        List<Teacher> teachers = asList(new Teacher("Bob Dean", asList(new Subject("Math"),
                new Subject("Geometry"))), new Teacher("Ric Berns", asList(new Subject("Anatomy"),
                new Subject("Biology"))), new Teacher("John Ant", asList(new Subject("English"),
                new Subject("Latin"))), new Teacher("Dan Brown", asList(new Subject("Physic"))));
        teachers.stream().peek(t -> t.getSubjects().stream().forEach(subjectDao::add)).forEach(teacherDao::add);
    }

    public List<Lesson> setTeachersAndSubjects(List<Lesson> lessons, List<Teacher> teachers, List<Auditory> auditories) {
        for (Teacher teacher : teachers) {
            for (int i = 0; i < lessons.size(); i++) {
                Lesson lesson = lessons.get(i);
                if (lesson.getTeacher() == null) {
                    lesson.setTeacher(teacher);
                    lesson.setSubject(teacher.getSubjects().get(0));
                    int index = auditories.size() - i % auditories.size();
                    i = i + index - 1;
                }
            }
        }
        return lessons;
    }

    public void add(Teacher teacher) {
        teacherDao.add(teacher);
    }

    public List<Teacher> getAll() {
        return teacherDao.getAll();
    }

    public void delete(Teacher teacher) {
        teacherDao.delete(teacher);
    }
}
package com.ua.foxminded.university.service;

import com.ua.foxminded.university.dao.StudentDao;
import com.ua.foxminded.university.domain.Student;

import java.util.List;

import static java.util.Arrays.asList;

@Service
public class StudentService {

    private StudentDao studentDao;
    private GroupService groupService;

    public StudentService(StudentDao studentDao, GroupService groupService) {
        this.studentDao = studentDao;
        this.groupService = groupService;
    }

    public void generateStudents() {
        asList("Andrew Arns", "Dan Berns", "John Black", "Jim White", "Andie Kit", "Joe Cole", "Andy Jans", "Mike Adrie", "Duke Pierse",
                "Ann Coen", "Marie Cara", "Liza Mon", "Dan Bean", "Schon Wu", "Mae Ann", "Gin Wurst", "Darie Ant", "Ben Moon")
                .stream().map(Student::new).map(groupService::assignToGroup).forEach(studentDao::add);
    }

    public void add(Student student) {
        studentDao.add(student);
    }

    public List<Student> getAll() {
        return studentDao.getAll();
    }

    public void delete(Student student) {
        studentDao.delete(student);
    }
}

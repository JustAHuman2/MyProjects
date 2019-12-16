package com.ua.foxminded.university;

import com.ua.foxminded.university.domain.*;
import com.ua.foxminded.university.service.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CommandExecutor {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private SubjectService subjectService;
    private TeacherService teacherService;
    private GroupService groupService;
    private StudentService studentService;
    private AuditoryService auditoryService;
    private LessonService lessonService;
    private LessonTimeService lessonTimeService;

    public CommandExecutor(SubjectService subjectService, TeacherService teacherService, GroupService groupService, StudentService studentService, AuditoryService auditoryService, LessonService lessonService, LessonTimeService lessonTimeService) {
        this.subjectService = subjectService;
        this.teacherService = teacherService;
        this.groupService = groupService;
        this.studentService = studentService;
        this.auditoryService = auditoryService;
        this.lessonService = lessonService;
        this.lessonTimeService = lessonTimeService;
    }

    public CommandExecutor() {
    }

    public String executeCommand(int number, String line, String dates) {
        switch (number) {
            case 2:
                return lessonService.getGroupLessons(new Group(line), LocalDate.parse(dates.substring(0, dates.indexOf(' ')), formatter)
                        , LocalDate.parse(dates.substring(dates.indexOf(' ') + 1), formatter)).toString();
            case 3:
                return lessonService.getTeacherLessons(new Teacher(line), LocalDate.parse(dates.substring(0, dates.indexOf(' ')), formatter)
                        , LocalDate.parse(dates.substring(dates.indexOf(' ') + 1), formatter)).toString();
            default:
                return "Wrong command";
        }
    }

    public String executeCommand(int number, String line) {
        switch (number) {
            case 1:
                return getList(line);
            case 4:
                teacherService.add(new Teacher(line));
                return "Teacher added";
            case 5:
                teacherService.delete(new Teacher(line));
                return "Teacher deleted";
            case 6:
                studentService.add(new Student(line));
                return "Student added";
            case 7:
                studentService.delete(new Student(line));
                return "Student deleted";
            case 8:
                groupService.add(new Group(line));
                return "Group added";
            case 9:
                groupService.delete(new Group(line));
                return "Group deleted";
            case 10:
                auditoryService.add(new Auditory(line));
                return "Auditory added";
            case 11:
                auditoryService.delete(new Auditory(line));
                return "Auditory deleted";
            case 12:
                lessonTimeService.add(createLessonTime(line));
                return "LessonTime added";
            case 13:
                lessonTimeService.delete(createLessonTime(line));
                return "LessonTime deleted";
            case 14:
                lessonService.delete(Integer.parseInt(line));
                return "lesson deleted";
            default:
                return "Wrong command";
        }
    }

    private LessonTime createLessonTime(String line) {
        LocalTime startTime = LocalTime.parse(line);
        return new LessonTime(startTime, startTime.plusHours(1).plusMinutes(30));
    }

    private String getList(String line) {
        switch (line) {
            case "1":
                return subjectService.getAll().toString();
            case "2":
                return teacherService.getAll().toString();
            case "3":
                return groupService.getAll().toString();
            case "4":
                return studentService.getAll().toString();
            case "5":
                return lessonTimeService.getAll().toString();
            case "6":
                return auditoryService.getAll().toString();
            case "7":
                return lessonService.getAll().toString();
            default:
                return "Wrong command";
        }
    }
}
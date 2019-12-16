package com.ua.foxminded.university;

import java.time.LocalDate;
import java.util.Scanner;

import com.ua.foxminded.university.service.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        context.getBean(DBManager.class).createTables("schema.sql");
        context.getBean(AuditoryService.class).generateAuditories();
        context.getBean(TeacherService.class).generateTeachers();
        context.getBean(GroupService.class).generateGroups();
        context.getBean(StudentService.class).generateStudents();
        context.getBean(LessonTimeService.class).generateLessonTimes();
        context.getBean(LessonService.class).generateSchedule(LocalDate.of(2019, 9, 01), LocalDate.of(2019, 9, 3));
        Menu menu = new Menu();
        CommandExecutor executor = context.getBean(CommandExecutor.class);
        int number = 0;
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println(menu.showMenu());
                number = scanner.nextInt();
                if (number == 0) {
                    break;
                }
                System.out.println(menu.showSubMenu(number));
                if ((number == 2) || (number == 3)) {
                    String input = scanner.next();
                    System.out.println("Enter dates:");
                    System.out.println(executor.executeCommand(number, input, scanner.next()));
                }
                System.out.println(executor.executeCommand(number, scanner.next()));
            }
        }
    }
}
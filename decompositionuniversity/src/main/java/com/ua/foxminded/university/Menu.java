package com.ua.foxminded.university;

import static java.lang.System.lineSeparator;

public class Menu {

    public String showMenu() {
        StringBuilder menu = new StringBuilder("Enter command:").append(lineSeparator());
        menu.append("1 Get List").append(lineSeparator());
        menu.append("2 Get group schedule").append(lineSeparator());
        menu.append("3 Get teachers schedule").append(lineSeparator());
        menu.append("4 Add teacher").append(lineSeparator());
        menu.append("5 Delete teacher").append(lineSeparator());
        menu.append("6 Add student").append(lineSeparator());
        menu.append("7 Delete student").append(lineSeparator());
        menu.append("8 Add group").append(lineSeparator());
        menu.append("9 Delete group").append(lineSeparator());
        menu.append("10 Add auditory").append(lineSeparator());
        menu.append("11 Delete auditory").append(lineSeparator());
        menu.append("12 Add lessonTime").append(lineSeparator());
        menu.append("13 Delete lessonTime").append(lineSeparator());
        menu.append("14 Delete lesson by id").append(lineSeparator());
        menu.append("0 Exit");
        return menu.toString();
    }

    public String showSubMenu(int number) {
        switch (number) {
            case 1:
                StringBuilder result = new StringBuilder("1 Subjects").append(lineSeparator());
                result.append("2 Teachers").append(lineSeparator());
                result.append("3 Groups").append(lineSeparator());
                result.append("4 Students").append(lineSeparator());
                result.append("5 lessonTimes").append(lineSeparator());
                result.append("6 Auditories").append(lineSeparator());
                result.append("7 Lessons").append(lineSeparator());
                return result.toString();
            case 2:
            case 8:
            case 9:
                return "Enter group name:";
            case 3:
            case 5:
                return "Enter teacher name:";
            case 4:
                return "Enter teacher name and subjects:";
            case 6:
            case 7:
                return "Enter student name:";
            case 10:
            case 11:
                return "Enter auditory name:";
            case 12:
            case 13:
                return "Enter start time (hh:mm):";
            case 14:
                return "Enter id:";
            default:
                return "Wrong command";
        }
    }
}
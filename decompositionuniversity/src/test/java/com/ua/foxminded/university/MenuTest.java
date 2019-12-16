package com.ua.foxminded.university;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static java.lang.System.lineSeparator;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class MenuTest {

    private Menu menu;

    @Before
    public void setUp() {
        menu = new Menu();
    }

    @Test
    public void whenShowMenu_thenMenu() {
        StringBuilder result = new StringBuilder("Enter command:").append(lineSeparator());
        result.append("1 Get List").append(lineSeparator());
        result.append("2 Get group schedule").append(lineSeparator());
        result.append("3 Get teachers schedule").append(lineSeparator());
        result.append("4 Add teacher").append(lineSeparator());
        result.append("5 Delete teacher").append(lineSeparator());
        result.append("6 Add student").append(lineSeparator());
        result.append("7 Delete student").append(lineSeparator());
        result.append("8 Add group").append(lineSeparator());
        result.append("9 Delete group").append(lineSeparator());
        result.append("10 Add auditory").append(lineSeparator());
        result.append("11 Delete auditory").append(lineSeparator());
        result.append("12 Add lessonTime").append(lineSeparator());
        result.append("13 Delete lessonTime").append(lineSeparator());
        result.append("14 Delete lesson by id").append(lineSeparator());
        result.append("0 Exit");
        String expected = result.toString();

        String actual = menu.showMenu();

        assertEquals(expected, actual);
    }

    @Test
    public void givenNumber1_whenShowSubMenu_thenMenu() {
        StringBuilder result = new StringBuilder("1 Subjects").append(lineSeparator());
        result.append("2 Teachers").append(lineSeparator());
        result.append("3 Groups").append(lineSeparator());
        result.append("4 Students").append(lineSeparator());
        result.append("5 lessonTimes").append(lineSeparator());
        result.append("6 Auditories").append(lineSeparator());
        result.append("7 Lessons").append(lineSeparator());
        String expected = result.toString();

        String actual = menu.showSubMenu(1);

        assertEquals(expected, actual);
    }

    @Test
    public void givenNumber2_whenShowSubMenu_thenEnterGroupName() {
        String expected = "Enter group name:";

        String actual = menu.showSubMenu(2);

        assertEquals(expected, actual);
    }

    @Test
    public void givenNumber3_whenShowSubMenu_thenEnterTeacherName() {
        String expected = "Enter teacher name:";

        String actual = menu.showSubMenu(3);

        assertEquals(expected, actual);
    }

    @Test
    public void givenNumber4_whenShowSubMenu_thenEnterTeacherNameAndSubjects() {
        String expected = "Enter teacher name and subjects:";

        String actual = menu.showSubMenu(4);

        assertEquals(expected, actual);
    }

    @Test
    public void givenNumber5_whenShowSubMenu_thenEnterTeacherName() {
        String expected = "Enter teacher name:";

        String actual = menu.showSubMenu(5);

        assertEquals(expected, actual);
    }

    @Test
    public void givenNumber6_whenShowSubMenu_thenEnterStudentName() {
        String expected = "Enter student name:";

        String actual = menu.showSubMenu(6);

        assertEquals(expected, actual);
    }

    @Test
    public void givenNumber7_whenShowSubMenu_thenEnterStudentName() {
        String expected = "Enter student name:";

        String actual = menu.showSubMenu(7);

        assertEquals(expected, actual);
    }

    @Test
    public void givenNumber8_whenShowSubMenu_thenEnterGroupName() {
        String expected = "Enter group name:";

        String actual = menu.showSubMenu(8);

        assertEquals(expected, actual);
    }

    @Test
    public void givenNumber9_whenShowSubMenu_thenEnterGroupName() {
        String expected = "Enter group name:";

        String actual = menu.showSubMenu(9);

        assertEquals(expected, actual);
    }

    @Test
    public void givenNumber10_whenShowSubMenu_thenEnterAuditoryName() {
        String expected = "Enter auditory name:";

        String actual = menu.showSubMenu(10);

        assertEquals(expected, actual);
    }

    @Test
    public void givenNumber11_whenShowSubMenu_thenEnterAuditoryName() {
        String expected = "Enter auditory name:";

        String actual = menu.showSubMenu(11);

        assertEquals(expected, actual);
    }

    @Test
    public void givenNumber12_whenShowSubMenu_thenEnterAuditoryName() {
        String expected = "Enter start time (hh:mm):";

        String actual = menu.showSubMenu(12);

        assertEquals(expected, actual);
    }

    @Test
    public void givenNumber13_whenShowSubMenu_thenEnterAuditoryName() {
        String expected = "Enter start time (hh:mm):";

        String actual = menu.showSubMenu(13);

        assertEquals(expected, actual);
    }

    @Test
    public void givenNumber14_whenShowSubMenu_thenEnterAuditoryName() {
        String expected = "Enter id:";

        String actual = menu.showSubMenu(14);

        assertEquals(expected, actual);
    }

    @Test
    public void givenUnrecognizedNumber_whenShowSubMenu_thenWrongCommand() {
        String expected = "Wrong command";

        String actual = menu.showSubMenu(0);

        assertEquals(expected, actual);
    }
}
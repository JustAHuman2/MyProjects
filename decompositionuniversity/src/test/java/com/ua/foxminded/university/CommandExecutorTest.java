package com.ua.foxminded.university;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.ua.foxminded.university.domain.*;
import com.ua.foxminded.university.service.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@RunWith(MockitoJUnitRunner.class)
public class CommandExecutorTest {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    private LocalDate beginDate;
    private LocalDate endDate;

    @Before
    public void setUp() {
        beginDate = LocalDate.parse("2019/09/01", formatter);
        endDate = LocalDate.parse("2019/12/30", formatter);
    }

    @Mock
    private AuditoryService auditoryService;

    @Mock
    private GroupService groupService;

    @Mock
    private LessonService lessonService;

    @Mock
    private LessonTimeService lessonTimeService;

    @Mock
    private StudentService studentService;

    @Mock
    private SubjectService subjectService;

    @Mock
    private TeacherService teacherService;

    @InjectMocks
    private CommandExecutor commandExecutor = new CommandExecutor();

    @Test
    public void givenNumbers1And1_whenExecuteCommand_thenDelegated() {
        commandExecutor.executeCommand(1, "1");
        verify(subjectService, times(1)).getAll();
    }

    @Test
    public void givenNumbers1And2_whenExecuteCommand_thenDelegated() {
        commandExecutor.executeCommand(1, "2");
        verify(teacherService, times(1)).getAll();
    }

    @Test
    public void givenNumber1And3_whenExecuteCommand_thenDelegated() {
        commandExecutor.executeCommand(1, "3");
        verify(groupService, times(1)).getAll();
    }

    @Test
    public void givenNumber1And4_whenExecuteCommand_thenDelegated() {
        commandExecutor.executeCommand(1, "4");
        verify(studentService, times(1)).getAll();
    }

    @Test
    public void givenNumber1And5_whenExecuteCommand_thenDelegated() {
        commandExecutor.executeCommand(1, "5");
        verify(lessonTimeService, times(1)).getAll();
    }

    @Test
    public void givenNumber1And6_whenExecuteCommand_thenDelegated() {
        commandExecutor.executeCommand(1, "6");
        verify(auditoryService, times(1)).getAll();
    }

    @Test
    public void givenNumber1And7_whenExecuteCommand_thenDelegated() {
        commandExecutor.executeCommand(1, "7");
        verify(lessonService, times(1)).getAll();
    }

    @Test
    public void givenNum1AndUnrecognizedSymbol_whenExecuteCommand_thenWrongCommand() {
        String expected = "Wrong command";

        String actual = commandExecutor.executeCommand(1, "0");

        assertEquals(expected, actual);
    }


    @Test
    public void givenNumber2AndInput_whenExecuteCommand_thenDelegated() {

        commandExecutor.executeCommand(2, "groupName", "2019/09/01 2019/12/30");

        verify(lessonService, times(1)).getGroupLessons(new Group("groupName"),
                LocalDate.of(2019, 9, 1), LocalDate.of(2019, 12, 30));
    }

    @Test
    public void givenNumber3AndInput_whenExecuteCommand_thenDelegated() {

        commandExecutor.executeCommand(3, "teacherName", "2019/09/01 2019/12/30");

        verify(lessonService, times(1)).getTeacherLessons(new Teacher("teacherName"),
                LocalDate.of(2019, 9, 1), LocalDate.of(2019, 12, 30));
    }

    @Test
    public void givenNumber4_whenExecuteCommand_thenDelegated() {

        commandExecutor.executeCommand(4, "teacherName");

        verify(teacherService, times(1)).add(new Teacher("teacherName"));
    }

    @Test
    public void givenNumber5_whenExecuteCommand_thenDelegated() {

        commandExecutor.executeCommand(5, "teacherName");

        verify(teacherService, times(1)).delete(new Teacher("teacherName"));
    }

    @Test
    public void givenNumber6_whenExecuteCommand_thenDelegated() {

        commandExecutor.executeCommand(6, "studentName");

        verify(studentService, times(1)).add(new Student("studentName"));
    }

    @Test
    public void givenNumber7_whenExecuteCommand_thenDelegated() {

        commandExecutor.executeCommand(7, "studentName");

        verify(studentService, times(1)).delete(new Student("studentName"));
    }

    @Test
    public void givenNumber8_whenExecuteCommand_thenDelegated() {

        commandExecutor.executeCommand(8, "groupName");

        verify(groupService, times(1)).add(new Group("groupName"));
    }

    @Test
    public void givenNumber9_whenExecuteCommand_thenDelegated() {

        commandExecutor.executeCommand(9, "groupName");

        verify(groupService, times(1)).delete(new Group("groupName"));
    }

    @Test
    public void givenNumber10_whenExecuteCommand_thenDelegated() {

        commandExecutor.executeCommand(10, "auditoryName");

        verify(auditoryService, times(1)).add(new Auditory("auditoryName"));
    }

    @Test
    public void givenNumber11_whenExecuteCommand_thenDelegated() {

        commandExecutor.executeCommand(11, "auditoryName");

        verify(auditoryService, times(1)).delete(new Auditory("auditoryName"));
    }

    @Test
    public void givenNumber12_whenExecuteCommand_thenDelegated() {
        String input = "14:00";

        commandExecutor.executeCommand(12, input);

        verify(lessonTimeService, times(1)).add(new LessonTime(LocalTime.parse(input), LocalTime.parse(input).plusHours(1).plusMinutes(30)));
    }

    @Test
    public void givenNumber13_whenExecuteCommand_thenDelegated() {
        String input = "10:00";

        commandExecutor.executeCommand(13, input);

        verify(lessonTimeService, times(1)).delete(new LessonTime(LocalTime.parse(input), LocalTime.parse(input).plusHours(1).plusMinutes(30)));
    }

    @Test
    public void givenNumber14_whenExecuteCommand_thenDelegated() {

        commandExecutor.executeCommand(14, "1");

        verify(lessonService, times(1)).delete(Integer.parseInt("1"));
    }

    @Test
    public void givenUnrecognizedSymbol_whenExecuteCommand_thenWrongCommand() {
        String expected = "Wrong command";

        String actual = commandExecutor.executeCommand(0, "0");

        assertEquals(expected, actual);
    }
}
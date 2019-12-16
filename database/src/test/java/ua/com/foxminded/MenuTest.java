package ua.com.foxminded;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import ua.com.foxminded.dao.CourseDao;
import ua.com.foxminded.dao.GroupDao;
import ua.com.foxminded.dao.StudentDao;
import ua.com.foxminded.domain.Student;

@RunWith(MockitoJUnitRunner.class)
public class MenuTest {

	private ConnectionProvider connectionProvider;

	@Mock
	private GroupDao groupDao;

	@Mock
	private StudentDao studentDao;

	@Mock
	private CourseDao courseDao;

	@InjectMocks
	private Menu menu = new Menu(connectionProvider);

	@Test
	public void givenNumber1_whenShowMenu_thenEnterStudentNumber() {
		String expected = "Enter student number:";

		String actual = menu.showMenu(1);

		assertEquals(expected, actual);
	}

	@Test
	public void givenNumber2_whenShowMenu_thenEnterCourseName() {
		String expected = "Enter course name:";

		String actual = menu.showMenu(2);

		assertEquals(expected, actual);
	}

	@Test
	public void givenNumber3_whenShowMenu_thenEnterStudentFirstNameAndLastName() {
		String expected = "Enter student first name and last name:";

		String actual = menu.showMenu(3);

		assertEquals(expected, actual);
	}

	@Test
	public void givenNumber4_whenShowMenu_thenEnterStudentId() {
		String expected = "Enter student ID:";

		String actual = menu.showMenu(4);

		assertEquals(expected, actual);
	}

	@Test
	public void givenNumber5_whenShowMenu_thenEnterStudentIdAndCourseId() {
		String expected = "Enter student ID and course ID:";

		String actual = menu.showMenu(5);

		assertEquals(expected, actual);
	}

	@Test
	public void givenNumber6_whenShowMenu_thenEnterStudentIdAndCourseId() {
		String expected = "Enter student ID and course ID:";

		String actual = menu.showMenu(6);

		assertEquals(expected, actual);
	}

	@Test
	public void givenUnrecognizedNumber_whenShowMenu_thenWrongCommand() {
		String expected = "Wrong command";

		String actual = menu.showMenu(7);

		assertEquals(expected, actual);
	}

	@Test
	public void givenNumber1_whenExecuteCommand_thenDelegated() {

		menu.executeCommand(1, "5", connectionProvider);

		verify(groupDao).getGroupsByStudentsCount(5);
	}

	@Test
	public void givenNumber2_whenExecuteCommand_thenDelegated() {

		menu.executeCommand(2, "7", connectionProvider);

		verify(studentDao, times(1)).getStudentsFromCourse("7");
	}

	@Test
	public void givenNumber3_whenExecuteCommand_thenDelegated() {

		menu.executeCommand(3, "Firstname Lastname", connectionProvider);

		verify(studentDao, times(1)).create(new Student("Firstname", "Lastname"));
	}

	@Test
	public void givenNumber4_whenExecuteCommand_thenDelegated() {

		menu.executeCommand(4, "9", connectionProvider);

		verify(studentDao, times(1)).deleteStudent(9);
	}

	@Test
	public void givenNumber5_whenExecuteCommand_thenDelegated() {

		menu.executeCommand(5, "7 9", connectionProvider);

		verify(courseDao, times(1)).addStudent(7, 9);
	}

	@Test
	public void givenNumber6_whenExecuteCommand_thenDelegated() {

		menu.executeCommand(6, "3 2", connectionProvider);

		verify(studentDao, times(1)).deleteStudentFromCourse(3, 2);
	}
}
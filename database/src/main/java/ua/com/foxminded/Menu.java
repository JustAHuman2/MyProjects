package ua.com.foxminded;

import static java.lang.System.lineSeparator;

import ua.com.foxminded.dao.CourseDao;
import ua.com.foxminded.dao.GroupDao;
import ua.com.foxminded.dao.StudentDao;
import ua.com.foxminded.domain.Student;

public class Menu {

	private StudentDao studentDao;
	private GroupDao groupDao;
	private CourseDao courseDao;

	public Menu(ConnectionProvider connectionProvider) {
		studentDao = new StudentDao(connectionProvider);
		groupDao = new GroupDao(connectionProvider);
		courseDao = new CourseDao(connectionProvider);
	}

	public static final String MENU = "Enter command:" + lineSeparator()
			+ "1 Find all groups with less or equal student count" + lineSeparator()
			+ "2 Find all students related to course with given name" + lineSeparator() + "3 Add new student"
			+ lineSeparator() + "4 Delete student by student ID" + lineSeparator() + "5 Add student to course"
			+ lineSeparator() + "6 Delete student from course" + lineSeparator() + "7 Exit";

	public String showMenu(int number) {
		switch (number) {
		case 1:
			return "Enter student number:";
		case 2:
			return "Enter course name:";
		case 3:
			return "Enter student first name and last name:";
		case 4:
			return "Enter student ID:";
		case 5:
		case 6:
			return "Enter student ID and course ID:";
		default:
			return "Wrong command";
		}
	}

	public String executeCommand(int number, String line, ConnectionProvider connectionProvider) {
		switch (number) {
		case 1:
			return groupDao.getGroupsByStudentsCount(Integer.parseInt(line)).toString();
		case 2:
			return studentDao.getStudentsFromCourse(line).toString();
		case 3:
			Student student  = new Student(line.substring(0, line.indexOf(" ")), line.substring(line.indexOf(" ") + 1));
			studentDao.create(student);
			return "New student added with id:" + student.getId();
		case 4:
			studentDao.deleteStudent(Integer.parseInt(line));
			return "Student deleted";
		case 5:
			courseDao.addStudent(parseStudentId(line), parseCourseId(line));
			return "Student added";
		case 6:
			studentDao.deleteStudentFromCourse(parseStudentId(line), parseCourseId(line));
			return "Student deleted";
		}
		return "Wrong command";
	}

	private int parseStudentId(String line) {
		return Integer.parseInt(line.substring(0, line.indexOf(" ")));
	}

	private int parseCourseId(String line) {
		return Integer.parseInt(line.substring(line.indexOf(" ") + 1));
	}
}
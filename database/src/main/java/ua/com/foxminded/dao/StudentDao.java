package ua.com.foxminded.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import ua.com.foxminded.ConnectionProvider;
import ua.com.foxminded.domain.Student;

public class StudentDao {

	private static final String SQL_ADD_STUDENT = "INSERT INTO students (group_id, first_name, last_name) VALUES (?,?,?) RETURNING student_id";
	private static final String SQL_GET_STUDENTS_FROM_COURSE = "Select student_courses.student_id, first_name, last_name FROM courses JOIN student_courses ON courses.course_id = student_courses.course_id JOIN students ON student_courses.student_id = students.student_id WHERE course_name = ?";
	private static final String ID = "student_id";
	private static final String NAME = "first_name";
	private static final String LAST_NAME = "last_name";
	private static final String SQL_DELETE_FROM_STUDENTS = "DELETE FROM students WHERE student_id = ?";
	private static final String SQL_DELETE_FROM_COURSE = "DELETE FROM student_courses WHERE student_id = ? AND course_id = ?";

	private ConnectionProvider connectionProvider;

	public StudentDao(ConnectionProvider connectionProvider) {
		this.connectionProvider = connectionProvider;
	}

	public void create(Student student) {
		try (Connection connection = connectionProvider.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_STUDENT)) {
			preparedStatement.setObject(1, student.getGroupId(), Types.INTEGER);
			preparedStatement.setString(2, student.getFirstName());
			preparedStatement.setString(3, student.getLastName());
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				student.setId(resultSet.getInt(ID));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Student> getStudentsFromCourse(String courseName) {
		List<Student> result = new ArrayList<>();
		try (Connection connection = connectionProvider.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_STUDENTS_FROM_COURSE)) {
			preparedStatement.setString(1, courseName);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				result.add(
						new Student(resultSet.getInt(ID), resultSet.getString(NAME), resultSet.getString(LAST_NAME)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void deleteStudent(int id) {
		try (Connection connection = connectionProvider.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_FROM_STUDENTS)) {
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteStudentFromCourse(int studentId, int courseId) {
		try (Connection connection = connectionProvider.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_FROM_COURSE)) {
			preparedStatement.setInt(1, studentId);
			preparedStatement.setInt(2, courseId);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
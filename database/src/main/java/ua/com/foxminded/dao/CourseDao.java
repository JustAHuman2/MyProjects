package ua.com.foxminded.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import ua.com.foxminded.ConnectionProvider;
import ua.com.foxminded.domain.Course;

public class CourseDao {

	private static final String SQL_ADD_COURSE = "INSERT INTO courses (course_name, course_description) VALUES (?,?) RETURNING course_id";
	private static final String SQL_ADD_STUDENT_TO_COURSE = "INSERT INTO student_courses (student_id, course_id) VALUES (?,?)";
	private static final String ID = "course_id";

	private ConnectionProvider connectionProvider;

	public CourseDao(ConnectionProvider connectionProvider) {
		this.connectionProvider = connectionProvider;
	}

	public void create(Course course) {
		try (Connection connection = connectionProvider.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_COURSE)) {
			preparedStatement.setString(1, course.getName());
			preparedStatement.setString(2, course.getDescription());
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				course.setId(resultSet.getInt(ID));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addStudent(int studentId, int courseId) {
		try (Connection connection = connectionProvider.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_STUDENT_TO_COURSE)) {
			preparedStatement.setInt(1, studentId);
			preparedStatement.setInt(2, courseId);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
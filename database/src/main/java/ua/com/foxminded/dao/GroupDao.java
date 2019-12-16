package ua.com.foxminded.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import ua.com.foxminded.ConnectionProvider;
import ua.com.foxminded.domain.Group;

public class GroupDao {

	private static final String SQL_ADD_GROUP = "INSERT INTO groups (group_name) VALUES (?) RETURNING group_id";
	private static final String SQL_SELECT_LESS_OR_EQUAL = "SELECT groups.group_id, groups.group_name FROM students JOIN groups ON students.group_id = groups.group_id GROUP BY groups.group_id, groups.group_name HAVING COUNT (groups.group_name) <= ?";
	private static final String ID = "group_id";
	private static final String NAME = "group_name";

	private ConnectionProvider connectionProvider;

	public GroupDao(ConnectionProvider connectionProvider) {
		this.connectionProvider = connectionProvider;
	}

	public Group create(Group group) {
		try (Connection connection = connectionProvider.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_GROUP)) {
			preparedStatement.setString(1, group.getName());
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				group.setId(resultSet.getInt(ID));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return group;
	}

	public List<Group> getGroupsByStudentsCount(int number) {
		List<Group> result = new ArrayList<>();
		try (Connection connection = connectionProvider.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_LESS_OR_EQUAL)) {
			preparedStatement.setInt(1, number);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				result.add(new Group(resultSet.getInt(ID), resultSet.getString(NAME)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
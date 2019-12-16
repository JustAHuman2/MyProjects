package ua.com.foxminded;

import java.sql.Connection;
import java.sql.Statement;

public class DBManager {

	private static final String SQL_DROP_DB = "DROP DATABASE university";

	private ConnectionProvider connectionProvider;

	public DBManager(ConnectionProvider connectionProvider) {
		this.connectionProvider = connectionProvider;
	}

	public void executeSQL(String sqlStatement) {
		try (Connection connection = connectionProvider.getConnection();
				Statement statement = connection.createStatement()) {
			statement.executeUpdate(sqlStatement);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void dropDB() {
		executeSQL(SQL_DROP_DB);
	}
}
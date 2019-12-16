package ua.com.foxminded;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionProvider {

	private static final String CONFIG = "config.properties";

	private String url;
	private String username;
	private String password;

	public ConnectionProvider() {
		Properties properties = new Properties();
		try {
			properties.load(ClassLoader.getSystemClassLoader().getResourceAsStream(CONFIG));
			this.url = properties.getProperty("db.url");
			this.username = properties.getProperty("db.username");
			this.password = properties.getProperty("db.password");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}
}
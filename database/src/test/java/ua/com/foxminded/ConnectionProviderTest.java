package ua.com.foxminded;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.Test;

public class ConnectionProviderTest {

	@Test
	public void givenConnectionProvider_whenGetConnection_thenNotNull() throws SQLException {
		ConnectionProvider connectionProvider = new ConnectionProvider();

		Connection connection = connectionProvider.getConnection();

		assertNotNull(connection);
	}
}
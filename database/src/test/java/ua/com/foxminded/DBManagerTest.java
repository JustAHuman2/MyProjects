package ua.com.foxminded;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.dbunit.DBTestCase;
import org.dbunit.DatabaseUnitException;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;
import org.postgresql.util.PSQLException;

public class DBManagerTest extends DBTestCase {

	private DBManager dbManager;
	private ConnectionProvider connectionProvider;
	private IDatabaseConnection iDatabaseConnection;

	public DBManagerTest(String name) {
		super(name);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL,
				"jdbc:postgresql://localhost:5434/");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "postgres");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "1234");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.postgresql.Driver");
	}

	@Before
	public void setUp() throws Exception {
		connectionProvider = new ConnectionProvider();
		dbManager = new DBManager(connectionProvider);
		iDatabaseConnection = getConnection();
	}

	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder()
				.build(new FileInputStream(getClass().getClassLoader().getResource("dataSet.xml").getFile()));
	}

	@Test
	public void testGivenStudents_whenExecuteSQLDelete_thenStudentsDeleted() throws Exception {
		DatabaseOperation.CLEAN_INSERT.execute(getConnection(), getDataSet());

		dbManager.executeSQL("delete from students");

		IDataSet actualDataSet = iDatabaseConnection.createDataSet();
		ITable actualTable = actualDataSet.getTable("students");
		int actual = actualTable.getRowCount();
		int expected = 0;
		assertEquals(actual, expected);
	}

	@Test(expected = PSQLException.class)
	public void testGivenDB_whenDropDB_thenDroppedDB() throws DatabaseUnitException, SQLException, Exception {
		DatabaseOperation.CLEAN_INSERT.execute(getConnection(), getDataSet());
	
		dbManager.dropDB();
		
		dbManager.executeSQL("\\connect database university;");
	}
}
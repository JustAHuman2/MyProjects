package ua.com.foxminded.dao;

import java.io.FileInputStream;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;
import ua.com.foxminded.ConnectionProvider;
import ua.com.foxminded.domain.Course;

public class CourseDaoTest extends DBTestCase {

	private CourseDao courseDao;
	private ConnectionProvider connectionProvider;
	private IDatabaseConnection iDatabaseConnection;
	
	public CourseDaoTest(String name) {
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
		courseDao = new CourseDao(connectionProvider);
		iDatabaseConnection = getConnection();
	}

	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder()
				.build(new FileInputStream(getClass().getClassLoader().getResource("dataSet.xml").getFile()));
	}

	@Test
	public void testGivenNewCourse_whenCreateCourse_thenCourseCreated() throws Exception {
		DatabaseOperation.DELETE_ALL.execute(getConnection(), getDataSet());

		courseDao.create(new Course("Math", "description"));

		IDataSet actualDataSet = iDatabaseConnection.createDataSet();
		ITable actualTable = actualDataSet.getTable("courses");
		String expected = "Math";
		String actual = (String) actualTable.getValue(0, "course_name");
		assertEquals(expected, actual);
	}

	@Test
	public void testGivenStudent_whenAddStudentToCourse_thenStudentAdded() throws Exception {
		DatabaseOperation.CLEAN_INSERT.execute(getConnection(), getDataSet());

		courseDao.addStudent(1, 2);

		IDataSet actualDataSet = iDatabaseConnection.createDataSet();
		ITable actualTable = actualDataSet.getTable("student_courses");
		int actual = actualTable.getRowCount();
		int expected = 2;
		assertEquals(actual, expected);
		actual = (int) actualTable.getValue(1, "course_id");
		assertEquals(actual, expected);
	}
}
package ua.com.foxminded.dao;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;
import ua.com.foxminded.ConnectionProvider;
import ua.com.foxminded.domain.Student;

public class StudentDaoTest extends DBTestCase {

	private StudentDao studentDao;
	private ConnectionProvider connectionProvider;
	private IDatabaseConnection iDatabaseConnection;

	public StudentDaoTest(String name) {
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
		studentDao = new StudentDao(connectionProvider);
		iDatabaseConnection = getConnection();
	}

	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(
				new FileInputStream(getClass().getClassLoader().getResource("studentDaoTestDataSet.xml").getFile()));
	}
	
	@Test
	public void testGivenTableWithStudents_whenDeleteStudents_thenEmptyTable() throws Exception {
		DatabaseOperation.CLEAN_INSERT.execute(getConnection(), getDataSet());

		studentDao.deleteStudent(1);
		studentDao.deleteStudent(2);
		studentDao.deleteStudent(3);
		studentDao.deleteStudent(4);
		studentDao.deleteStudent(5);

		IDataSet actualDataSet = getConnection().createDataSet();
		int actual = actualDataSet.getTable("students").getRowCount();
		int expected = 0;
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGivenNewStudent_whenCreateStudent_thenStudentCreated() throws Exception {

		studentDao.create(new Student("Abcd", "Defgh"));
		
		IDataSet actualDataSet = getConnection().createDataSet();
		int actual = actualDataSet.getTable("students").getRowCount();
		int expected = 1;
		assertEquals(expected, actual);
	}

	@Test
	public void testCourseWithStudents_whenGetStudentsFromCourse_thenStudents() throws Exception {
		DatabaseOperation.CLEAN_INSERT.execute(getConnection(), getDataSet());
	
		List<Student> actual = studentDao.getStudentsFromCourse("Algebra");

		List<Student> expected = new ArrayList<>();
		expected.add(new Student(1,"StudentOne","One"));
		expected.add(new Student(2,"StudentTwo","Two"));
		expected.add(new Student(3,"StudentThree","Three"));
		assertEquals(expected, actual);
	}

	@Test
	public void testGivenNewCourse_whenGetStudentsFromCourse_thenEmptyList() throws Exception {
		DatabaseOperation.CLEAN_INSERT.execute(getConnection(), getDataSet());

		List<Student> actual = studentDao.getStudentsFromCourse("Math");

		List<Student> expected = new ArrayList<>();
		assertEquals(expected, actual);
	}

	@Test
	public void testGivenNewStudent_whenDeleteStudentFromCourse_thenEmptyTable() throws Exception {
		DatabaseOperation.DELETE_ALL.execute(getConnection(), getDataSet());
		studentDao.create(new Student(1, "Abcd", "Defg"));

		studentDao.deleteStudentFromCourse(1, 1);

		IDataSet actualDataSet = getConnection().createDataSet();
		int actual = actualDataSet.getTable("student_courses").getRowCount();
		int expected = 0;
		assertEquals(expected, actual);
	}
}
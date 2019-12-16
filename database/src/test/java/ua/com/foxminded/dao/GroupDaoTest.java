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
import ua.com.foxminded.domain.Group;

public class GroupDaoTest extends DBTestCase {

	private GroupDao groupDao;
	private ConnectionProvider connectionProvider;
	private IDatabaseConnection iDatabaseConnection;
	
	public GroupDaoTest(String name) {
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
		groupDao = new GroupDao(connectionProvider);
		iDatabaseConnection = getConnection();
	}

	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder()
				.build(new FileInputStream(getClass().getClassLoader().getResource("groupDaoTestDataSet.xml").getFile()));
	}

	@Test
	public void testGivenNewGroup_whenCreateGroup_thenGroupCreated() throws Exception {
		DatabaseOperation.DELETE_ALL.execute(getConnection(), getDataSet());
		GroupDao groupDao = new GroupDao(new ConnectionProvider());

		groupDao.create(new Group("One"));

		IDataSet actualDataSet = getConnection().createDataSet();	
		String expected = "One";
		String actual = (String) actualDataSet.getTable("groups").getValue(0, "group_name");
		assertEquals(expected, actual);

	}

	@Test   
	public void testGivenEmptyList_whenGetGroupsByStudentsCountZero_thenEmptyList() throws Exception {
		DatabaseOperation.CLEAN_INSERT.execute(getConnection(), getDataSet());
		List<Group> expected = new ArrayList<>();

		List<Group> actual = groupDao.getGroupsByStudentsCount(0);

		assertEquals(expected, actual);
	}
	
	@Test     
	public void testGivenEmptyList_whenGetGroupsByStudentsCount_thenListOfGroups() throws Exception {
		DatabaseOperation.CLEAN_INSERT.execute(getConnection(), getDataSet());	
		
		List<Group> actual = groupDao.getGroupsByStudentsCount(2);
		
		List<Group> expected = new ArrayList<>();
		expected.add(new Group(1, "First"));
		expected.add(new Group(2, "Second"));
		assertEquals(expected, actual);
	}
}
package ua.com.foxminded;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import ua.com.foxminded.dao.CourseDao;
import ua.com.foxminded.dao.GroupDao;
import ua.com.foxminded.dao.StudentDao;
import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;

public class Main {

	public static void main(String[] args) {
		ConnectionProvider connectionProvider = new ConnectionProvider();
		TableContentGenerator generator = new TableContentGenerator();
		DBManager dbManager = new DBManager(connectionProvider);
		try {
			dbManager.executeSQL(generator.readFile("dbAndUserCreation.sql").collect(Collectors.joining("")));
			dbManager.executeSQL(generator.readFile("schema.sql").collect(Collectors.joining("")));
			GroupDao groupDao = new GroupDao(connectionProvider);
			List<Group> groups = generator.generateGroups("groups.txt").stream().map(g -> groupDao.create(g))
					.collect(toList());
			CourseDao courseDao = new CourseDao(connectionProvider);
			List<Course> courses = generator.generateCourses("courses.txt").stream().peek(c -> courseDao.create(c))
					.collect(toList());
			StudentDao studentDao = new StudentDao(connectionProvider);
			generator.generateStudents("studentFirstNames.txt", "studentLastNames.txt", groups, courses, 200).stream()
					.peek(s -> studentDao.create(s))
					.forEach(s -> s.getCourses().forEach(c -> courseDao.addStudent(s.getId(), c.getId())));
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			Menu menu = new Menu(connectionProvider);
			Scanner scanner = new Scanner(System.in);
			int number = 0;
			while (true) {
				System.out.println(Menu.MENU);
				number = scanner.nextInt();
				if (number == 7) {
					dbManager.dropDB();
					break;
				}
				System.out.println(menu.showMenu(number));
				String input;
				input = reader.readLine();
				System.out.println(menu.executeCommand(number, input, connectionProvider));
			}
			scanner.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
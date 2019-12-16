package ua.com.foxminded;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;

public class TableContentGeneratorTest {

	private TableContentGenerator tableContentGenerator;

	@Before
	public void setup() {
		tableContentGenerator = new TableContentGenerator();
	}

	@Test(expected = NullPointerException.class)
	public void givenNull_whenGenerateCourses_thenNPE() throws IOException {
		tableContentGenerator.generateCourses(null);
	}

	@Test
	public void givenFileName_whenGenerateCourses_thenCourses() throws IOException {
		Course courseOne = new Course("Algebra", "study of mathematical symbols");
		Course courseTwo = new Course("Anatomy", "study of the structure of organisms");
		Course courseThree = new Course("Astronomy", "natural science");
		List<Course> expected = Arrays.asList(courseOne, courseTwo, courseThree);

		List<Course> actual = tableContentGenerator.generateCourses("courses.txt");

		assertEquals(expected, actual);
	}

	@Test(expected = NullPointerException.class)
	public void givenNull_whenGenerateGroups_thenNPE() throws IOException {
		tableContentGenerator.generateGroups(null);
	}

	@Test
	public void givenFileName_whenGenerateGroups_thenGroups() throws IOException {
		Group groupOne = new Group(1, "DR-02");
		Group groupTwo = new Group(2, "SV-34");
		Group groupThree = new Group(3, "LH-45");
		List<Group> expected = Arrays.asList(groupOne, groupTwo, groupThree);

		List<Group> actual = tableContentGenerator.generateGroups("groups.txt");
		AtomicInteger id = new AtomicInteger();
		actual.forEach(g -> g.setId(id.incrementAndGet()));

		assertEquals(expected, actual);
	}

	@Test(expected = NullPointerException.class)
	public void givenNull_whenGenerateStudents_thenNPE() throws IOException {
		tableContentGenerator.generateStudents(null, null, null, null, 0);
	}

	@Test
	public void givenFileNamesGroupsCourses_whenGenerateStudents_thenStudents() throws IOException {
		Group groupOne = new Group("DR-02");
		List<Group> groups = Arrays.asList(groupOne);
		Course courseOne = new Course("Algebra", "study of mathematical symbols");
		List<Course> courses = Arrays.asList(courseOne);
		List<Student> expected = Arrays.asList(new Student(1, "Daniel", "Megos"));

		List<Student> actual = tableContentGenerator.generateStudents("firstNames.txt", "lastNames.txt", groups,
				courses, 1);

		actual.get(0).setId(1);
		assertEquals(expected, actual);
	}

	@Test(expected = NullPointerException.class)
	public void givenNull_whenReadFile_thenNPE() throws IOException {
		tableContentGenerator.readFile(null);
	}

	@Test
	public void givenFileName_whenReadFile_thenText() throws IOException {
		String expected = "DR-02 SV-34 LH-45";

		String actual = tableContentGenerator.readFile("groups.txt").collect(Collectors.joining(" "));

		assertEquals(expected, actual);
	}
}
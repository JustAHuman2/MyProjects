package ua.com.foxminded;

import static java.util.concurrent.ThreadLocalRandom.current;
import static java.util.stream.Collectors.*;
import static java.nio.file.Files.lines;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import ua.com.foxminded.domain.Course;
import ua.com.foxminded.domain.Group;
import ua.com.foxminded.domain.Student;

public class TableContentGenerator {

	public List<Course> generateCourses(String courses) throws IOException {
		return readFile(courses)
				.map(line -> new Course(line.substring(0, line.indexOf(' ')), line.substring(line.indexOf(' ')).trim()))
				.collect(toList());
	}

	public List<Group> generateGroups(String groups) throws IOException {
		return readFile(groups).map(line -> new Group(line)).collect(toList());
	}

	public List<Student> generateStudents(String namesLines, String lastNameLines, List<Group> groups,
			List<Course> courses, int studentsNumber) throws IOException {
		String[] names = readFile(namesLines).toArray(String[]::new);
		String[] lastNames = readFile(lastNameLines).toArray(String[]::new);
		List<Student> students = IntStream.range(0, studentsNumber)
				.mapToObj(i -> new Student(names[current().nextInt(names.length)],
						lastNames[current().nextInt(lastNames.length)]))
				.map(s -> assignToGroup(assignCourses(s, courses), groups)).collect(toList());
		return students;
	}

	public Stream<String> readFile(String fileName) throws IOException {
		return lines(new File(ClassLoader.getSystemClassLoader().getResource(fileName).getFile()).toPath());
	}

	private Student assignCourses(Student student, List<Course> courses) {
		IntStream.rangeClosed(0, current().nextInt(3))
				.forEach(i -> addCourse(courses.get(current().nextInt(courses.size())), student));
		return student;
	}

	private void addCourse(Course course, Student student) {
		List<Course> courses = student.getCourses();
		if (!courses.contains(course)) {
			courses.add(course);
		}
	}

	private Student assignToGroup(Student student, List<Group> groups) {
		Group group = groups.get(current().nextInt(groups.size()));
		if (group.getStudents().size() <= 30) {
			group.addStudent(student);
			student.setGroupId(group.getId());
		} else {
			assignToGroup(student, groups);
		}
		return student;
	}
}
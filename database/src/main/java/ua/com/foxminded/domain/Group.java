package ua.com.foxminded.domain;

import java.util.ArrayList;
import java.util.List;

public class Group {

	private Integer id;
	private String name;
	private List<Student> students = new ArrayList<>();

	public Group(String name) {
		this.name = name;
	}

	public Group(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void addStudent(Student student) {
		students.add(student);
	}

	public List<Student> getStudents() {
		return students;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Group) || (this.hashCode() != obj.hashCode()))
			return false;
		if (this == obj)
			return true;
		Group thatGroup = (Group) obj;
		if ((this.getId().equals(thatGroup.getId())) && (this.getName().equals(thatGroup.getName()))) {
			return true;
		}
		return true;
	}

	@Override
	public int hashCode() {
		return (int) (this.id * 31 + this.name.codePointCount(0, name.length() - 1));
	}

	@Override
	public String toString() {
		return "Group id:" + id + ", name:" + name + System.lineSeparator();
	}
}
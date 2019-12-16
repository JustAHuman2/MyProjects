package ua.com.foxminded.domain;

import java.util.ArrayList;
import java.util.List;

public class Student {

	private String firstName;
	private String lastName;
	private int id;
	private Integer groupId;
	private final List<Course> courses = new ArrayList<>(10);

	public Student(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Student(int id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public List<Course> getCourses() {
		return courses;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Student) || (this.hashCode() != obj.hashCode()))
			return false;
		if (this == obj)
			return true;
		Student thatStudent = (Student) obj;
		return (this.getId() == (thatStudent.getId()) && (this.getFirstName().equals(thatStudent.getFirstName())
				&& (this.getLastName().equals(thatStudent.getLastName()))));
	}

	@Override
	public int hashCode() {
		return (int) (this.id * 31 + this.firstName.length() * 13 + this.lastName.length() * 17);
	}

	@Override
	public String toString() {
		return "id:" + id + " " + firstName + " " + lastName + " " + "group:" + groupId + "courses" + courses
				+ System.lineSeparator();
	}
}
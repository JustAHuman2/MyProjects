package ua.com.foxminded.domain;

public class Course {

	private int id;
	private String name;
	private String description;

	public Course(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return id + " " + name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Course) || (this.hashCode() != obj.hashCode()))
			return false;
		if (this == obj)
			return true;
		Course thatCourse = (Course) obj;
		if ((this.getId() == (thatCourse.getId())) && (this.getName().equals(thatCourse.getName())
				&& (this.getDescription().equals(thatCourse.getDescription())))) {
			return true;
		}
		return true;
	}

	@Override
	public int hashCode() {
		return (int) (this.id * 31 + this.name.length() * 7 + this.description.length() * 13);
	}
}
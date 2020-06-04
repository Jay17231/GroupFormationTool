package csci5408.catme.dto;

import csci5408.catme.domain.Course;

public class CourseSummary {
	private int id;

	private String name;

	public CourseSummary(int id, String name) {
		this.id = id;
		this.name = name;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "CourseSummary{" + "id=" + id + ", name='" + name + '\'' + '}';
	}

	public static CourseSummary from(Course course) {
		return new CourseSummary(course.getId(), course.getName());
	}

	public static Course to(CourseSummary course) {
		return new Course(course.getId(), course.getName());
	}
}

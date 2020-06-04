package csci5408.catme.domain;

public class Course {
	private int id;

	private String name;

	public Course(int i, String name) {
		this.id = i;
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
		return "Course{" + "id=" + id + ", name='" + name + '\'' + '}';
	}
}

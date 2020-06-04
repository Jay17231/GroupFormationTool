package csci5408.catme.dao;

import java.util.List;

import csci5408.catme.domain.Course;

public interface CourseDao extends Dao<Course, String> {
	List<Course> findCoursesByUserId(Long id);

	@Override
	List<Course> findAll();

	Course findCoursesById(int id);

}

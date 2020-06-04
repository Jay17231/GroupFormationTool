package csci5408.catme.dao;

import csci5408.catme.domain.Course;

import java.util.List;

public interface CourseDao extends Dao<Course, Long> {
	List<Course> findCoursesByUserId(Long id);

	@Override
	List<Course> findAll();

	Course findCoursesById(Long id);

}

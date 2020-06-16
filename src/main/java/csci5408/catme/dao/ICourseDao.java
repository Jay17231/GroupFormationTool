package csci5408.catme.dao;

import csci5408.catme.domain.Course;

import java.util.List;

public interface ICourseDao extends IDao<Course, Long> {
	List<Course> findCoursesByUserId(Long id);

	@Override
	List<Course> findAll();

	Course findCoursesById(Long id);

}

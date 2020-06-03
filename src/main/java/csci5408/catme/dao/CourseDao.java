package csci5408.catme.dao;

import csci5408.catme.domain.Course;
import csci5408.catme.domain.User;

import java.util.List;

public interface CourseDao extends Dao<Course, String> {
    List<Course> findCoursesByUserId(Long id);




}

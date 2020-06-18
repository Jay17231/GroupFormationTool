package csci5408.catme.service;

import java.util.List;
import java.util.Optional;

import csci5408.catme.domain.Course;
import csci5408.catme.domain.Role;
import csci5408.catme.dto.CourseRole;
import csci5408.catme.dto.CourseSummary;
import csci5408.catme.dto.UserSummary;

public interface IEnrollmentService {

	boolean enrollUser(CourseSummary c, UserSummary u, Role role);

	Role getRole(UserSummary user, CourseSummary summary);

	List<CourseRole> getUserCoursesAndRoles(UserSummary userSummary);

	List<CourseSummary> getEnrolledCourses(UserSummary userSummary);

	CourseRole getCourseRole(UserSummary userSummary, CourseSummary summary, boolean fetchPermissions);

	Optional<Course> getCourseById(Long courseId);

}

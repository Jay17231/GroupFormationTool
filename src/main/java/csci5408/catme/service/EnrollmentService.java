package csci5408.catme.service;

import csci5408.catme.domain.Role;
import csci5408.catme.dto.CourseRole;
import csci5408.catme.dto.CourseSummary;
import csci5408.catme.dto.UserSummary;

import java.util.List;

public interface EnrollmentService {

	boolean enrollUser(CourseSummary c, UserSummary u, Role role);

	Role getRole(UserSummary user, CourseSummary summary);

	List<CourseRole> getUserCoursesAndRoles(UserSummary userSummary);

	List<CourseSummary> getEnrolledCourses(UserSummary userSummary);

	CourseRole getCourseRole(UserSummary userSummary, CourseSummary summary, boolean fetchPermissions);

}

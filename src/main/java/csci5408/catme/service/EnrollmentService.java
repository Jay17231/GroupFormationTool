package csci5408.catme.service;

import csci5408.catme.domain.Role;
import csci5408.catme.dto.CourseRole;
import csci5408.catme.dto.CourseSummary;
import csci5408.catme.dto.UserSummary;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EnrollmentService {

	boolean enrollUser(CourseSummary c, UserSummary u, Role role);

	boolean enrollStudent(CourseSummary c, UserSummary u);

	boolean enrollStudents(CourseSummary c, List<UserSummary> u);

	boolean enrollStudents(CourseSummary c, MultipartFile file);

	Role getRole(UserSummary user, CourseSummary summary);

	List<CourseRole> getUserCoursesAndRoles(UserSummary userSummary);

	List<CourseSummary> getEnrolledCourses(UserSummary userSummary);

	CourseRole getCourseRole(UserSummary userSummary, CourseSummary summary, boolean fetchPermissions);

}

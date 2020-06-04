package csci5408.catme.service.impl;

import csci5408.catme.dao.CourseDao;
import csci5408.catme.dao.EnrollmentDao;
import csci5408.catme.dao.RoleDao;
import csci5408.catme.dao.UserDao;
import csci5408.catme.domain.Course;
import csci5408.catme.domain.Role;
import csci5408.catme.domain.User;
import csci5408.catme.dto.CourseRole;
import csci5408.catme.dto.CourseSummary;
import csci5408.catme.dto.UserSummary;
import csci5408.catme.service.EnrollmentService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

	final UserDao userDao;

	final CourseDao courseDao;

	final EnrollmentDao enrollmentDao;

	final RoleDao roleDao;

	public EnrollmentServiceImpl(UserDao dao, CourseDao courseDao, EnrollmentDao enrollmentDao, RoleDao roleDao) {
		this.userDao = dao;
		this.courseDao = courseDao;
		this.enrollmentDao = enrollmentDao;
		this.roleDao = roleDao;
	}

	@Override
	public boolean enrollStudent(CourseSummary c, UserSummary u) {
		User user = userDao.findByEmail(u.getEmailId());
		if (user == null) {
			// @TODO: Create User Jay
		}
		assert c != null;
		assert c.getId() != null;
		return false;
	}

	@Override
	public boolean enrollStudents(CourseSummary c, List<UserSummary> users) {
		for (UserSummary u : users) {
			enrollStudent(c, u);
		}
		return true;
	}

	@Override
	public boolean enrollStudents(CourseSummary c, MultipartFile file) {
		return false;
	}

	@Override
	public Role getRole(UserSummary user, CourseSummary courseSummary) {
		return enrollmentDao.findRole(user.getId(), courseSummary.getId());
	}

	@Override
	public List<CourseRole> getUserCoursesAndRoles(UserSummary userSummary) {
		List<CourseSummary> summaries = this.getEnrolledCourses(userSummary);
		List<CourseRole> courseRoles = new ArrayList<>();
		for (CourseSummary summary:
			 summaries) {
			Role role = enrollmentDao.findRole(userSummary.getId(), summary.getId());
			CourseRole cr = new CourseRole();
			cr.setCourseId(summary.getId());
			cr.setRoleId(role.getId());
			cr.setCourseName(summary.getCourseName());
			cr.setRoleName(role.getName());
			courseRoles.add(cr);
		}
		return courseRoles;
	}

	@Override
	public List<CourseSummary> getEnrolledCourses(UserSummary userSummary) {
		List<Course> enrolledCourses =  courseDao.findCoursesByUserId(userSummary.getId());
		return CourseSummary.from(enrolledCourses);
	}
}

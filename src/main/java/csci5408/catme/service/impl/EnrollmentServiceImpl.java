package csci5408.catme.service.impl;

import csci5408.catme.dao.*;
import csci5408.catme.domain.*;
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

	final OperationDao operationDao;

	public EnrollmentServiceImpl(UserDao dao, CourseDao courseDao, EnrollmentDao enrollmentDao, RoleDao roleDao,
								 OperationDao operationDao) {
		this.userDao = dao;
		this.courseDao = courseDao;
		this.enrollmentDao = enrollmentDao;
		this.roleDao = roleDao;
		this.operationDao = operationDao;
	}

	@Override
	public boolean enrollUser(CourseSummary c, UserSummary u, Role role) {
		try {
			Role r = enrollmentDao.findRole(u.getId(), c.getId());
			Long taRoleId = roleDao.getRoleIdByName("TA");
			if(r != null) {
				Enrollment e = enrollmentDao.findEnrollment(u.getId(), c.getId());
				e.setRoleId(taRoleId);
				enrollmentDao.update(e);
				return true;
			} else {
				Enrollment e = new Enrollment();
				e.setRoleId(taRoleId);
				e.setCourseId(c.getId());
				e.setUserId(u.getId());
				enrollmentDao.save(e);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean enrollStudent(CourseSummary c, UserSummary u) {
		User user = userDao.findByEmail(u.getEmailId());
		if (user == null) {
			// @TODO: Create User Jay
		}
		assert c != null;
		assert c.getId() != 0;
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
			courseRoles.add(getCourseRole(userSummary, summary, false));
		}
		return courseRoles;
	}

	@Override
	public List<CourseSummary> getEnrolledCourses(UserSummary userSummary) {
		List<Course> enrolledCourses =  courseDao.findCoursesByUserId(userSummary.getId());
		return CourseSummary.from(enrolledCourses);
	}

	@Override
	public CourseRole getCourseRole(UserSummary userSummary, CourseSummary summary, boolean fetchPermissions) {
		Role role = enrollmentDao.findRole(userSummary.getId(), summary.getId());
		if(role == null) {
			role = new Role();
		}
		CourseRole cr = new CourseRole();
		cr.setCourseId(summary.getId());
		cr.setRoleId(role.getId());
		cr.setCourseName(summary.getCourseName());
		cr.setRoleName(role.getName());
		if(fetchPermissions) {
			List<Operation> op = operationDao.findAllByRoleId(role.getId());
			cr.setPermissions(op);
		}
		return cr;
	}
}

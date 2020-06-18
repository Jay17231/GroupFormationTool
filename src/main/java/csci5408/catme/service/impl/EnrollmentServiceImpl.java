package csci5408.catme.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import csci5408.catme.dao.ICourseDao;
import csci5408.catme.dao.IEnrollmentDao;
import csci5408.catme.dao.IOperationDao;
import csci5408.catme.dao.IRoleDao;
import csci5408.catme.dao.IUserDao;
import csci5408.catme.domain.Course;
import csci5408.catme.domain.Enrollment;
import csci5408.catme.domain.Operation;
import csci5408.catme.domain.Role;
import csci5408.catme.dto.CourseRole;
import csci5408.catme.dto.CourseSummary;
import csci5408.catme.dto.UserSummary;
import csci5408.catme.service.IEnrollmentService;

@Service
public class EnrollmentServiceImpl implements IEnrollmentService {

	final IUserDao userDao;

	final ICourseDao courseDao;

	final IEnrollmentDao enrollmentDao;

	final IRoleDao roleDao;

	final IOperationDao operationDao;

	public EnrollmentServiceImpl(IUserDao dao, ICourseDao courseDao, IEnrollmentDao enrollmentDao, IRoleDao roleDao,
			IOperationDao operationDao) {
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
			if (r != null) {
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
	public Role getRole(UserSummary user, CourseSummary courseSummary) {
		return enrollmentDao.findRole(user.getId(), courseSummary.getId());
	}

	@Override
	public List<CourseRole> getUserCoursesAndRoles(UserSummary userSummary) {
		List<CourseSummary> summaries = this.getEnrolledCourses(userSummary);
		List<CourseRole> courseRoles = new ArrayList<>();
		for (CourseSummary summary : summaries) {
			courseRoles.add(getCourseRole(userSummary, summary, false));
		}
		return courseRoles;
	}

	@Override
	public List<CourseSummary> getEnrolledCourses(UserSummary userSummary) {
		List<Course> enrolledCourses = courseDao.findCoursesByUserId(userSummary.getId());
		return CourseSummary.from(enrolledCourses);
	}

	@Override
	public CourseRole getCourseRole(UserSummary userSummary, CourseSummary summary, boolean fetchPermissions) {
		Role role = enrollmentDao.findRole(userSummary.getId(), summary.getId());
		if (role == null) {
			role = new Role();
		}
		CourseRole cr = new CourseRole();
		cr.setCourseId(summary.getId());
		cr.setRoleId(role.getId());
		cr.setCourseName(summary.getCourseName());
		cr.setRoleName(role.getName());
		if (fetchPermissions) {
			List<Operation> op = operationDao.findAllByRoleId(role.getId());
			cr.setPermissions(op);
		}
		return cr;
	}

	@Override
	public Optional<Course> getCourseById(Long courseId) {
		Optional<Course> courseOptional = courseDao.findById(courseId);
		return courseOptional;
	}
}

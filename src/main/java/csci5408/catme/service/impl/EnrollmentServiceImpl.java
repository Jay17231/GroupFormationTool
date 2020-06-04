package csci5408.catme.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import csci5408.catme.dao.CourseDao;
import csci5408.catme.dao.EnrollmentDao;
import csci5408.catme.dao.RoleDao;
import csci5408.catme.dao.UserDao;
import csci5408.catme.domain.User;
import csci5408.catme.dto.CourseSummary;
import csci5408.catme.dto.UserSummary;
import csci5408.catme.service.EnrollmentService;

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
	public String getRole(UserSummary user) {
		String userRole = "";
		Long userId = user.getId();
		userRole = enrollmentDao.findRole(userId);
		return userRole;
	}
}

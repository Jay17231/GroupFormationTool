package csci5408.catme.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import csci5408.catme.dao.ICourseDao;
import csci5408.catme.dao.IEnrollmentDao;
import csci5408.catme.dao.IOperationDao;
import csci5408.catme.dao.IRoleDao;
import csci5408.catme.dao.IUserDao;
import csci5408.catme.domain.Course;
import csci5408.catme.domain.Enrollment;
import csci5408.catme.domain.Role;
import csci5408.catme.dto.CourseSummary;
import csci5408.catme.dto.UserSummary;

public class EnrollmentServiceImplTest {

	final IUserDao userDao;

	final ICourseDao courseDao;

	final IEnrollmentDao enrollmentDao;

	final IRoleDao roleDao;

	final IOperationDao operationDao;

	EnrollmentServiceImpl enrollmentService;

	public EnrollmentServiceImplTest() {
		this.userDao = mock(IUserDao.class);
		this.courseDao = mock(ICourseDao.class);
		this.enrollmentDao = mock(IEnrollmentDao.class);
		this.roleDao = mock(IRoleDao.class);
		this.operationDao = mock(IOperationDao.class);
	}

	@BeforeEach
	public void setup() {
		enrollmentService = new EnrollmentServiceImpl(userDao, courseDao, enrollmentDao, roleDao, operationDao);
	}

	@Test
	public void enrollUserTest_RoleExist() {
		when(enrollmentDao.findRole(1L, 2L)).thenReturn(new Role());
		when(roleDao.getRoleIdByName("TA")).thenReturn(3L);
		Enrollment e = new Enrollment();
		e.setUserId(1L);
		e.setCourseId(2L);
		e.setRoleId(3L);
		when(enrollmentDao.findEnrollment(1L, 2L)).thenReturn(e);
		CourseSummary summary = new CourseSummary(2L, "A");
		UserSummary userSummary = new UserSummary();
		userSummary.setId(1L);
		Role r = new Role();
		r.setName("TA");
		assertTrue(enrollmentService.enrollUser(summary, userSummary, r));
	}

	@Test
	public void enrollUserTest_RoleNotExist() {
		when(enrollmentDao.findRole(1L, 2L)).thenReturn(null);
		Enrollment e = new Enrollment();
		e.setUserId(1L);
		e.setCourseId(2L);
		e.setRoleId(3L);

		CourseSummary summary = new CourseSummary(2L, "A");
		UserSummary userSummary = new UserSummary();
		userSummary.setId(1L);
		Role r = new Role();
		r.setName("TA");
		assertTrue(enrollmentService.enrollUser(summary, userSummary, r));
	}

	@Test
	public void enrollUserTest_EnrollmentNotFond() {
		when(enrollmentDao.findRole(1L, 2L)).thenReturn(new Role());
		when(roleDao.getRoleIdByName("TA")).thenReturn(3L);
		Enrollment e = new Enrollment();
		e.setUserId(1L);
		e.setCourseId(2L);
		e.setRoleId(3L);
		when(enrollmentDao.findEnrollment(1L, 2L)).thenReturn(null);
		CourseSummary summary = new CourseSummary(2L, "A");
		UserSummary userSummary = new UserSummary();
		userSummary.setId(1L);
		Role r = new Role();
		r.setName("TA");
		assertFalse(enrollmentService.enrollUser(summary, userSummary, r));
	}

	@Test
	public void getRoleTest() {
		when(enrollmentDao.findRole(1L, 2L)).thenReturn(new Role());
		UserSummary summary = new UserSummary();
		summary.setId(1L);
		CourseSummary summary1 = new CourseSummary(2L, "ABC");
		assertNotNull(enrollmentService.getRole(summary, summary1));
	}

	@Test
	public void getCourseRoleTest() {
		ArrayList<Course> courses = new ArrayList<>();
		courses.add(new Course(1L, "A"));
		UserSummary summary = new UserSummary();
		summary.setId(1L);
		CourseSummary summary1 = new CourseSummary(1L, "A");
		when(courseDao.findCoursesByUserId(1L)).thenReturn(courses);
		when(enrollmentDao.findRole(1L, 1L)).thenReturn(null);
		assertNotNull(enrollmentService.getCourseRole(summary, summary1, true));
	}

	@Test
	public void getEnrolledCoursesTest() {
		ArrayList<Course> courses = new ArrayList<>();
		courses.add(new Course(1L, "A"));
		when(courseDao.findCoursesByUserId(1L)).thenReturn(courses);
		UserSummary summary = new UserSummary();
		summary.setId(1L);
		assertNotNull(enrollmentService.getEnrolledCourses(summary));
	}

	@Test
	public void getUserCoursesAndRolesTest() {
		UserSummary summary = new UserSummary();
		summary.setId(1L);
		ArrayList<Course> courses = new ArrayList<>();
		courses.add(new Course(1L, "A"));
		when(courseDao.findCoursesByUserId(1L)).thenReturn(courses);
		UserSummary summary1 = new UserSummary();
		summary.setId(1L);
		assertNotNull(enrollmentService.getEnrolledCourses(summary1));
		assertNotNull(enrollmentService.getUserCoursesAndRoles(summary));
	}

	@Test
	public void getCourseByIdTest() {

		Course course = new Course();
		course.setId(1L);
		course.setName("A");
		when(courseDao.findById(1L)).thenReturn(Optional.of(course));
		when(enrollmentService.getCourseById(1L)).thenReturn(Optional.of(course));
		Optional<Course> courseFromDao = courseDao.findById(1L);
		System.out.println(courseFromDao.get().getName());
		Optional<Course> courseFromService = enrollmentService.getCourseById(1L);
		assertFalse(courseFromService.isEmpty());
		assertEquals(courseFromDao.get().getName(), courseFromService.get().getName());
	}
}

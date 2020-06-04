package csci5408.catme.service.impl;

import csci5408.catme.dao.*;
import csci5408.catme.domain.Enrollment;
import csci5408.catme.domain.Role;
import csci5408.catme.dto.CourseSummary;
import csci5408.catme.dto.UserSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EnrollmentServiceImplTest {

    final UserDao userDao;

    final CourseDao courseDao;

    final EnrollmentDao enrollmentDao;

    final RoleDao roleDao;

    final OperationDao operationDao;

    EnrollmentServiceImpl enrollmentService;

    public EnrollmentServiceImplTest() {
        this.userDao = mock(UserDao.class);
        this.courseDao = mock(CourseDao.class);
        this.enrollmentDao = mock(EnrollmentDao.class);
        this.roleDao = mock(RoleDao.class);
        this.operationDao = mock(OperationDao.class);
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
}

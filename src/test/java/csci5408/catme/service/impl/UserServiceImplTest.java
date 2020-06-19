package csci5408.catme.service.impl;

import csci5408.catme.dao.IUserDao;
import csci5408.catme.domain.User;
import csci5408.catme.dto.UserSummary;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    private final IUserDao userDao;
    private final UserServiceImpl userService;

    public UserServiceImplTest() {
        userDao = mock(IUserDao.class);
        userService = new UserServiceImpl(userDao);
    }

    @Test
    public void getUserByIdTest_UserNotExist() {
        when(userDao.findById(1L)).thenReturn(Optional.empty());
        assertNull(userService.getUserById(1L));
    }

    @Test
    public void getUserByIdTest_UserExist() {
        User u = new User(2L, "A", "V", "B00", true, "A@V.C");
        when(userDao.findById(2L)).thenReturn(Optional.of(u));
        UserSummary summary = userService.getUserById(2L);
        assertNotNull(summary);
        assertEquals(summary.getEmailId(), u.getEmailId());
        assertEquals(summary.getAdmin(), u.isAdmin());
        assertEquals(summary.getFirstName(), u.getFirstName());
        assertEquals(summary.getLastName(), u.getLastName());
        assertEquals(summary.getStudentId(), u.getStudentId());
        assertEquals(summary.getId(), u.getId());
    }

    @Test
    public void getUserByEmailIdTest_UserNotExist() {
        when(userDao.findByEmail("A@B.c")).thenReturn(null);
        assertNull(userService.getUserByEmailId("A@B.C"));
    }

    @Test
    public void getUserByEmailIdTest_UserExist() {
        User u = new User(2L, "A", "V", "B00", true, "A@V.C");
        u.setPassword("XYZ");
        when(userDao.findByEmail("A@B.C")).thenReturn(u);

        UserSummary summary = userService.getUserByEmailId("A@B.C");
        assertNotNull(summary);
        assertEquals(summary.getEmailId(), u.getEmailId());
        assertEquals(summary.getAdmin(), u.isAdmin());
        assertEquals(summary.getFirstName(), u.getFirstName());
        assertEquals(summary.getLastName(), u.getLastName());
        assertEquals(summary.getStudentId(), u.getStudentId());
        assertEquals(summary.getId(), u.getId());
    }

    @Test
    public void createUserTest() {
        UserSummary u = new UserSummary();
        u.setEmailId("av@gmail.com");
        u.setFirstName("A");
        u.setLastName("V");
        u.setAdmin(true);
        u.setStudentId(null);
        when(userDao.save(any(User.class))).thenReturn(new User(1L, "A", "V",
                null, true, "av@gmail.com"));

        assertNotNull(userService.createUser(u, "PASS"));
    }
}

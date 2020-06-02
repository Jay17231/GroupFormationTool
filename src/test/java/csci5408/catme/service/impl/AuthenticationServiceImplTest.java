package csci5408.catme.service.impl;

import csci5408.catme.authentication.ISessionStore;
import csci5408.catme.dao.UserDao;
import csci5408.catme.dto.UserSummary;
import csci5408.catme.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthenticationServiceImplTest {

    private AuthenticationManager authenticationManager;
    private UserDao userDao;
    private ISessionStore sessionStore;
    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AuthenticationServiceImpl authenticationService;

    public AuthenticationServiceImplTest() {
        authenticationManager = mock(AuthenticationManager.class);
        userDao = mock(UserDao.class);
        sessionStore = mock(ISessionStore.class);
        userService = mock(UserService.class);
        bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);
    }

    @BeforeEach
    public void setup() {
        authenticationService = new AuthenticationServiceImpl(authenticationManager, sessionStore, userService, bCryptPasswordEncoder, userDao);
    }

    @Test
    public void signUpTest_UserExist() {
        UserSummary userSummary = new UserSummary();
        userSummary.setEmailId("jon@doe.com");
        when(userService.getUserByEmailId("jon@doe.com")).thenReturn(userSummary);
        assertThrows(RuntimeException.class, () -> authenticationService.signUp(userSummary, "Yo!"));
    }

    @Test
    public void signUpTest_UserNotExist() {
        String userEmail = "jon@doe.com";
        String password = "Yo!";
        String encodedPassword = "!Yo";
        UserSummary userSummary = new UserSummary();
        userSummary.setEmailId(userEmail);
        when(userService.getUserByEmailId(userEmail)).thenReturn(null);
        when(bCryptPasswordEncoder.encode(password)).thenReturn(encodedPassword);
        UserSummary createdUser = new UserSummary();
        createdUser.setEmailId(userEmail);
        createdUser.setId(1L);
        when(userService.createUser(userSummary, encodedPassword)).thenReturn(createdUser);
        UserSummary newUser = authenticationService.signUp(userSummary, password);
        assertEquals(newUser.getEmailId(), createdUser.getEmailId());
        assertNotNull(newUser.getId());
    }
}

package csci5408.catme.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import csci5408.catme.authentication.ISessionStore;
import csci5408.catme.dao.UserDao;
import csci5408.catme.domain.User;
import csci5408.catme.dto.UserSummary;
import csci5408.catme.service.UserService;

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
		authenticationService = new AuthenticationServiceImpl(authenticationManager, sessionStore, userService,
				bCryptPasswordEncoder, userDao);
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

	@Test
	public void changePasswordTest_UserNotExist() {
		UserSummary summary = new UserSummary();
		summary.setEmailId("a@b.c");

		when(bCryptPasswordEncoder.encode("A")).thenReturn("a");
		when(userDao.findByEmail(summary.getEmailId())).thenReturn(null);
		assertThrows(UsernameNotFoundException.class, () -> authenticationService.changePassword(summary, "A"));
	}

	@Test
	public void changePasswordTest_UserExist() {
		UserSummary summary = new UserSummary();
		summary.setEmailId("a@b.c");

		User u = new User(1L, "A", "B", "C", false, "a@b.c");
		u.setPassword("A");

		when(bCryptPasswordEncoder.encode("A")).thenReturn("a");
		when(userDao.findByEmail(summary.getEmailId())).thenReturn(u);

		User updated = new User(1L, "A", "B", "C", false, "a@b.c");
		updated.setPassword("a");

		when(userDao.update(u)).thenReturn(updated);
		authenticationService.changePassword(summary, "A");
	}

	@Test
	public void loginTest() {
		assertFalse(authenticationService.isAuthenticated());
		assertNull(authenticationService.getLoggedInUser());

		String emailId = "a@b.c";
		String password = "abc";

		UserSummary summary = new UserSummary();
		summary.setEmailId(emailId);
		summary.setAdmin(true);
		HttpServletResponse response = new MockHttpServletResponse();

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(emailId, password);
		UsernamePasswordAuthenticationToken authenticatedToken = new UsernamePasswordAuthenticationToken(summary,
				password, new ArrayList<>());

		when(authenticationManager.authenticate(token)).thenReturn(authenticatedToken);
		assertTrue(authenticationService.login(summary.getEmailId(), "abc", null));

		when(authenticationManager.authenticate(token)).thenReturn(authenticatedToken);
		when(sessionStore.setSession(summary)).thenReturn("ABC");
		assertTrue(authenticationService.login(summary.getEmailId(), "abc", response));
		assertTrue(authenticationService.isAuthenticated());
		assertNotNull(authenticationService.getLoggedInUser());
	}

	@Test
	public void generatePassword() {

		int minPasswordLength = 10;
		int maxPasswordLength = 20;
		String newPassword = authenticationService.resetPassword();
		assertNotNull(newPassword);
		assertTrue(minPasswordLength <= newPassword.length() && maxPasswordLength >= newPassword.length());

	}

	@Test
	public void isAdminTest_True() {
		String email = "aman@g.com";
		User u = new User(1L, "A", "V", "B", true, email);
		when(userDao.findByEmail(email)).thenReturn(u);
		assertTrue(authenticationService.isAdmin(email, ""));
	}

	@Test
	public void isAdminTest_False() {
		String email = "aman@g.com";
		User u = new User(1L, "A", "V", "B", false, email);
		when(userDao.findByEmail(email)).thenReturn(u);
		assertFalse(authenticationService.isAdmin(email, ""));
	}

}
package csci5408.catme.service.impl;

import csci5408.catme.authentication.ISessionStore;
import csci5408.catme.dao.UserDao;
import csci5408.catme.domain.User;
import csci5408.catme.dto.UserSummary;
import csci5408.catme.service.AuthenticationService;
import csci5408.catme.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;

import static csci5408.catme.authentication.AuthConfig.AUTH_COOKIE_NAME;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	final AuthenticationManager authenticationManager;

	final ISessionStore ISessionStore;

	final UserService userService;

	final BCryptPasswordEncoder bCryptPasswordEncoder;

	final UserDao userDao;

	public AuthenticationServiceImpl(AuthenticationManager authenticationManager, ISessionStore ISessionStore,
			UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder, UserDao userDao) {
		this.authenticationManager = authenticationManager;
		this.ISessionStore = ISessionStore;
		this.userService = userService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userDao = userDao;
	}

	@Override
	public boolean login(String email, String password, HttpServletResponse response) {
		UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(email, password);

		SecurityContext sc = SecurityContextHolder.getContext();

		try {
			Authentication auth = authenticationManager.authenticate(authReq);
			sc.setAuthentication(auth);
			if (this.isAuthenticated()) {
				String cookieString = ISessionStore.setSession((UserSummary) auth.getPrincipal());
				response.addCookie(new Cookie(AUTH_COOKIE_NAME, cookieString));
			}
		}  catch (NullPointerException ex) {
			System.out.println("Code probably called from CommandLineRunner");
		} catch (Exception ex) {
			return false;
		}

		return sc.getAuthentication().isAuthenticated();
	}

	@Override
	public UserSummary signUp(UserSummary user, String password) {
		UserSummary existingUser = userService.getUserByEmailId(user.getEmailId());
		if (existingUser != null) {
			// User Exists
			throw new RuntimeException("User Already Exists");
		}
		String encodedPassword = bCryptPasswordEncoder.encode(password);
		return userService.createUser(user, encodedPassword);
	}

	@Override
	public boolean isAuthenticated() {
		SecurityContext sc = SecurityContextHolder.getContext();
		Authentication authentication = sc.getAuthentication();
		if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
			return false;
		}
		return authentication.isAuthenticated();
	}

	@Override
	public String resetPassword(int passlength) {

		String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowerCase = "abcdefghijklmnopqrstuvwxyz";
		String specialCharacters = "!@#$";
		String numbers = "1234567890";
		String combinedChars = upperCase + lowerCase + specialCharacters + numbers;
		Random random = new Random();
		String newPassword = "";
		char[] passwordArr = new char[passlength];

		passwordArr[0] = lowerCase.charAt(random.nextInt(lowerCase.length()));
		passwordArr[1] = upperCase.charAt(random.nextInt(upperCase.length()));
		passwordArr[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
		passwordArr[3] = numbers.charAt(random.nextInt(numbers.length()));

		for (int i = 4; i < passlength; i++) {
			passwordArr[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
		}
		newPassword = new String(passwordArr);
		return newPassword;
	}

	@Override
	public void changePassword(UserSummary user, String password) {
		String encodedPassword = bCryptPasswordEncoder.encode(password);
		User u = userDao.findByEmail(user.getEmailId());
		if(u == null) {
			throw new UsernameNotFoundException(user.getEmailId());
		}
		u.setPassword(encodedPassword);
		userDao.update(u);
	}

	public boolean isAdmin(String email, String password) {
		User u = userDao.findByEmail(email);
		if (u.isAdmin()) {
			return true;
		} else {
			return false;
		}
	}

	public UserSummary getLoggedInUser() {
		if(this.isAuthenticated()) {
			SecurityContext context = SecurityContextHolder.getContext();
			return (UserSummary) context.getAuthentication().getPrincipal();
		}
		return null;
	}
}

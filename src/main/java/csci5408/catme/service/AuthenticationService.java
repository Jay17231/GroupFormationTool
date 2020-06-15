package csci5408.catme.service;

import javax.servlet.http.HttpServletResponse;

import csci5408.catme.dto.UserSummary;

public interface AuthenticationService {

	boolean login(String email, String password, HttpServletResponse response);

	UserSummary signUp(UserSummary user, String password);

	boolean isAuthenticated();

	/**
	 * @deprecated in favour of {@link #getLoggedInUser()}
	 * @author Krupa Ptel
	 * @param email
	 * @param password
	 * @return
	 */
	boolean isAdmin(String email, String password);

	String resetPassword();

	void changePassword(UserSummary user, String password);

	UserSummary getLoggedInUser();
}

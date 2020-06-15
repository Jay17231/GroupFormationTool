package csci5408.catme.service;

import csci5408.catme.dto.UserSummary;

import javax.servlet.http.HttpServletResponse;

public interface IAuthenticationService {

	boolean login(String email, String password, HttpServletResponse response);

	UserSummary signUp(UserSummary user, String password);

	boolean isAuthenticated();

	/**
	 * @param email
	 * @param password
	 * @return
	 * @author Krupa Patel
	 * @deprecated in favour of {@link #getLoggedInUser()}
	 */
	boolean isAdmin(String email, String password);

	String resetPassword();

	void changePassword(UserSummary user, String password);

	UserSummary getLoggedInUser();
}

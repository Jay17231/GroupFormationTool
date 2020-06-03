package csci5408.catme.service;

import javax.servlet.http.HttpServletResponse;

import csci5408.catme.dto.UserSummary;

public interface AuthenticationService {

	boolean login(String email, String password, HttpServletResponse response);

	UserSummary signUp(UserSummary user, String password);

	boolean isAuthenticated();

	String resetPassword(int passlength);

	void changePassword(UserSummary user, String password);
}

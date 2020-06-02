package csci5408.catme.service;

import csci5408.catme.domain.User;
import csci5408.catme.dto.UserSummary;

import javax.servlet.http.HttpServletResponse;

public interface AuthenticationService {

    boolean login(String email, String password, HttpServletResponse response);
    UserSummary signUp(UserSummary user, String password);
    boolean isAuthenticated();

    void changePassword(UserSummary user, String password);
}

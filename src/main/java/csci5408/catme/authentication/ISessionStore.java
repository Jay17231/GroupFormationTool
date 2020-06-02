package csci5408.catme.authentication;

import csci5408.catme.dto.UserSummary;

public interface ISessionStore {
    UserSummary getSession(String key);
    String setSession(UserSummary user);
}

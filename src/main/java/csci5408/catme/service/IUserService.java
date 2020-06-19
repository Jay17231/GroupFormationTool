package csci5408.catme.service;

import csci5408.catme.dto.UserSummary;

public interface IUserService {
    UserSummary getUserById(Long id);

    UserSummary getUserByEmailId(String id);

    UserSummary createUser(UserSummary summary, String password);
}

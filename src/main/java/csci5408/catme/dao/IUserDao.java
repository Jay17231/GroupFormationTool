package csci5408.catme.dao;

import csci5408.catme.domain.User;

public interface IUserDao extends IDao<User, Long> {
    User findByEmail(String email);
}

package csci5408.catme.dao;

import csci5408.catme.domain.User;

public interface UserDao extends Dao<User, Long> {
    User findByEmail(String email);
}

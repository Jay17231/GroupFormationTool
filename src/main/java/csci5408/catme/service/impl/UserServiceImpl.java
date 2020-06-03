package csci5408.catme.service.impl;

import csci5408.catme.dao.UserDao;
import csci5408.catme.domain.User;
import csci5408.catme.dto.UserSummary;
import csci5408.catme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    final
    UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserSummary getUserById(Long id) {
        Optional<User> user = userDao.findById(id);
        return user.map(UserSummary::from).orElse(null);
    }

    @Override
    public UserSummary getUserByEmailId(String emailId) {
        User u = userDao.findByEmail(emailId);
        if(u != null) {
            return UserSummary.from(u);
        }
        return null;
    }

    @Override
    public UserSummary createUser(UserSummary summary, String password) {
        User u = UserSummary.to(summary);
        u.setPassword(password);
        u.setStudentId("B00111111");
        u.setAdmin(true);
        u = userDao.save(u);
        return UserSummary.from(u);
    }
}

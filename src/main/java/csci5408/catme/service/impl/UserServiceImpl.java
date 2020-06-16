package csci5408.catme.service.impl;

import csci5408.catme.dao.IUserDao;
import csci5408.catme.domain.User;
import csci5408.catme.dto.UserSummary;
import csci5408.catme.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    final
    IUserDao userDao;

    public UserServiceImpl(IUserDao userDao) {
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
        u = userDao.save(u);
        return UserSummary.from(u);
    }
}

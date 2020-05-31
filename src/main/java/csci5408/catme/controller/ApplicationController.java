package csci5408.catme.controller;

import csci5408.catme.dao.UserDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Aman Vishnani (aman.vishnani@dal.ca)
 */
@Controller
public class ApplicationController {

    private final UserDao userDao;

    public ApplicationController(UserDao userDao) {

        this.userDao = userDao;
    }

    @GetMapping
    public String greetings() {
        userDao.findAll();
        return "index";
    }
}

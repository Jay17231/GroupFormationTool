package csci5408.catme;

import csci5408.catme.dao.UserDao;
import csci5408.catme.domain.User;
import csci5408.catme.dto.UserSummary;
import csci5408.catme.service.AuthenticationService;
import csci5408.catme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CatmeApplication implements CommandLineRunner {

    public CatmeApplication() {
    }

    public static void main(String[] args) {
        SpringApplication.run(CatmeApplication.class, args);
    }

    @Override
    public void run(String... args) {

    }
}

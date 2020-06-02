package csci5408.catme;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
<<<<<<< HEAD
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
=======
>>>>>>> develop

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

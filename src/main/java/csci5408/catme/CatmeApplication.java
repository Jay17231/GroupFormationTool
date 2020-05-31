package csci5408.catme;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CatmeApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CatmeApplication.class, args);
    }

    @Override
    public void run(String... args) {
    }
}

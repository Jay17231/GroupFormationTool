package csci5408.catme.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class CourseController {

    @GetMapping("/courses")
    public String greetings() {
        return "courses";
    }
}

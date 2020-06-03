package csci5408.catme.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CourseController {

	@GetMapping("/courses")
	public String courseGreeting() {
		return "courses";
	}
}

package csci5408.catme.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import csci5408.catme.domain.Course;
import csci5408.catme.domain.Role;
import csci5408.catme.dto.CourseSummary;
import csci5408.catme.dto.UserSummary;
import csci5408.catme.service.IEnrollmentService;
import csci5408.catme.service.IUserService;

/**
 * @author Jay Gajjar (jy386888@dal.ca)
 */

@Controller
public class AssignTAController {

	final IUserService user;
	final IEnrollmentService enrollmentService;

	public AssignTAController(IUserService user, IEnrollmentService enrollmentService) {
		this.user = user;
		this.enrollmentService = enrollmentService;
	}

	@PostMapping("/assign-ta")
	public String assignTA(@RequestParam("emailta") String emailId, @RequestParam("courseid") Long courseId,
			Model model) {

		UserSummary userSummary = user.getUserByEmailId(emailId);

		if (userSummary == null) {
			model.addAttribute("message", "No such user exists. Please try a different email");
			model.addAttribute("status", false);
			return "assign-ta-details";
		}
		Optional<Course> course = enrollmentService.getCourseById(courseId);
		if (!course.isPresent()) {
			model.addAttribute("message", "Course Not found. Please try again");
			model.addAttribute("status", false);
			return "assign-ta-details";
		}
		CourseSummary courseSummary = CourseSummary.from(course.get());
		Role taRole = new Role();
		taRole.setName("TA");
		model.addAttribute("status", true);
		model.addAttribute("name", userSummary.getFirstName() + " " + userSummary.getLastName());
		model.addAttribute("studentId", userSummary.getStudentId());
		model.addAttribute("email", emailId);
		enrollmentService.enrollUser(courseSummary, userSummary, taRole);

		return "assign-ta-details";
	}

}

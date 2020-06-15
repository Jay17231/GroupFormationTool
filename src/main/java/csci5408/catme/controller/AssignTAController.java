package csci5408.catme.controller;

import csci5408.catme.dao.ICourseDao;
import csci5408.catme.dao.IEnrollmentDao;
import csci5408.catme.domain.Course;
import csci5408.catme.domain.Role;
import csci5408.catme.dto.CourseSummary;
import csci5408.catme.dto.UserSummary;
import csci5408.catme.service.IEnrollmentService;
import csci5408.catme.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

/**
 * @author Jay Gajjar (jy386888@dal.ca)
 */

@Controller
public class AssignTAController {

	final IUserService user;
	final IEnrollmentDao enrollmentDao;
	final IEnrollmentService enrollmentService;
	final ICourseDao courseDao;

	public AssignTAController(IUserService user, IEnrollmentDao enrollmentDao, IEnrollmentService enrollmentService,
							  ICourseDao courseDao) {
		this.user = user;
		this.enrollmentDao = enrollmentDao;
		this.enrollmentService = enrollmentService;
		this.courseDao = courseDao;
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
		Optional<Course> course = courseDao.findById(courseId);
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

//		if (role.getName().compareToIgnoreCase("Student") != 0) {
//			model.addAttribute("message", "User is not a student. Please try a student's email address");
//			model.addAttribute("status", false);
//			return "assign-ta-details";
//		}
//
//		boolean madeta = enrollmentDao.makeTA(userId);
//
//		if (!madeta) {
//			model.addAttribute("message", "Something went wrong");
//			model.addAttribute("status", false);
//			return "assign-ta-details";
//		}
//		model.addAttribute("message", "Role Changed to TA Successfully!");
//		model.addAttribute("status", madeta);
//		model.addAttribute("name", userSummary.getFirstName() + " " + userSummary.getLastName());
//		model.addAttribute("studentId", userSummary.getStudentId());
//		model.addAttribute("email", emailId);
//
		return "assign-ta-details";
	}

}

package csci5408.catme.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import csci5408.catme.dao.EnrollmentDao;
import csci5408.catme.domain.Role;
import csci5408.catme.dto.UserSummary;
import csci5408.catme.service.UserService;

/**
 * @author Jay Gajjar (jy386888@dal.ca)
 */

@Controller
public class AssignTAController {

	final UserService user;
	final EnrollmentDao enrollmentDao;

	public AssignTAController(UserService user, EnrollmentDao enrollmentDao) {
		this.user = user;
		this.enrollmentDao = enrollmentDao;
	}

	@PostMapping("/assign-ta")
	public String assignTA(@RequestParam("emailta") String emailid, @RequestParam("courseid") Long courseid,
			Model model) {

		UserSummary userSummary = user.getUserByEmailId(emailid);

		if (userSummary == null) {
			model.addAttribute("message", "No such user exists. Please try a different email");
			model.addAttribute("status", false);
			return "assign-ta-details";
		}

		Long userId = userSummary.getId();

		Role role = enrollmentDao.findRole(userId);

		if (role.getName().compareToIgnoreCase("Student") != 0) {
			model.addAttribute("message", "User is not a student. Please try a student's email address");
			model.addAttribute("status", false);
			return "assign-ta-details";
		}

		boolean madeta = enrollmentDao.makeTA(userId);

		if (!madeta) {
			model.addAttribute("message", "Something went wrong");
			model.addAttribute("status", false);
			return "assign-ta-details";
		}
		model.addAttribute("message", "Role Changed to TA Successfully!");
		model.addAttribute("status", madeta);
		model.addAttribute("name", userSummary.getFirstName() + " " + userSummary.getLastName());
		model.addAttribute("studentId", userSummary.getStudentId());
		model.addAttribute("email", emailid);

		return "assign-ta-details";
	}

}

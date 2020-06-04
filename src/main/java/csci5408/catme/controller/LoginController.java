package csci5408.catme.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import csci5408.catme.dao.CourseDao;
import csci5408.catme.domain.Course;
import csci5408.catme.dto.UserSummary;
import csci5408.catme.service.AuthenticationService;
import csci5408.catme.service.EnrollmentService;
import csci5408.catme.service.UserService;

/**
 * @author krupa
 *
 */

@Controller
public class LoginController {

	final UserService userService;
	final EnrollmentService enrollService;
	final AuthenticationService authenticationService;
	final CourseDao courseDao;

	public LoginController(UserService userService, EnrollmentService enrollService,
			AuthenticationService authenticationService, CourseDao courseDao) {
		this.authenticationService = authenticationService;
		this.userService = userService;
		this.enrollService = enrollService;
		this.courseDao = courseDao;
	}

	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("signup", new UserSummary());
		return "signup"; // extension depends on view resolver.
	}

	@PostMapping("/signup")
	public String signupPost(@ModelAttribute UserSummary signup, HttpServletRequest request) {
		UserSummary userSummary = authenticationService.signUp(signup, request.getParameter("password").toString());
		return "login"; // extension depends on view resolver.
	}

	@GetMapping("/forgotpassword")
	public String forgotpassword() {
		return "ForgotPassword.html"; // extension depends on view resolver.
	}

	@GetMapping("/login")
	public String login() {
		return "login.html"; // extension depends on view resolver.
	}

	@PostMapping("/login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {

		Course userCourse;
		ModelAndView mView = new ModelAndView();

		if (authenticationService.login(request.getParameter("email"), request.getParameter("password"), response)) {

			if (authenticationService.isAdmin(request.getParameter("email"), request.getParameter("password"))) {
				mView = new ModelAndView("adminDashboard");
				return mView;
			} else {
				mView = new ModelAndView("home");
				String emailString = request.getParameter("email");
				UserSummary userSummary = userService.getUserByEmailId(emailString);
				Long userId = userSummary.getId();
				userCourse = courseDao.findCoursesByUserId(userId).get(0);

				String courseName = userCourse.getCourseName();
				String name = userSummary.getFirstName() + " " + userSummary.getLastName();
				String role = enrollService.getRole(userSummary);

				mView.addObject("course", courseName);
				mView.addObject("courseid", userCourse.getId());
				mView.addObject("status", false);
				if (role.compareTo("Instructor") == 0) {
					mView.addObject("status", true);
				}

				mView.addObject("Role", role);
				mView.addObject("name", name);
				return mView;
			}
		} else {
			mView = new ModelAndView("login");
			return mView; // extension depends on view resolver.
		}

	}
}

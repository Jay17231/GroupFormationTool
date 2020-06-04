/**
 * 
 */
package csci5408.catme.controller;

import csci5408.catme.dto.UserSummary;
import csci5408.catme.service.AuthenticationService;
import csci5408.catme.service.EnrollmentService;
import csci5408.catme.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author krupa
 *
 */

@Controller
public class LoginController {

	final UserService userService;
	final EnrollmentService enrollService;
	final AuthenticationService authenticationService;

	public LoginController(UserService userService, EnrollmentService enrollService,
			AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
		this.userService = userService;
		this.enrollService = enrollService;
	}

	@GetMapping("/signup")
	public String signUp(Model model) {
		model.addAttribute("signup", new UserSummary());
		return "signup"; // extension depends on view resolver.
	}

	@PostMapping("/signup")
	public String signupPost(@ModelAttribute UserSummary signup, HttpServletRequest request) {
		UserSummary userSummary = authenticationService.signUp(signup, request.getParameter("password").toString());
		return "redirect:login"; // extension depends on view resolver.
	}

	@GetMapping("/forgotpassword")
	public String forgotpassword() {
		return "ForgotPassword.html"; // extension depends on view resolver.
	}

	@GetMapping("/login")
	public String login() {
		if(authenticationService.isAuthenticated()) {
			return "redirect:";
		}
		return "login.html"; // extension depends on view resolver.
	}

	@PostMapping("/login")
	public ModelAndView loginPost(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mView = new ModelAndView();
		boolean authState = authenticationService.login(
				request.getParameter("email"),
				request.getParameter("password"),
				response);

		if(!authState) {
			return new ModelAndView("redirect:login");
		}

		if (authenticationService.isAdmin(request.getParameter("email"), request.getParameter("password"))) {
			mView = new ModelAndView("adminDashboard");
			return mView;
		} else {
			mView = new ModelAndView("home");
			String emailString = request.getParameter("email");
			UserSummary userSummary = userService.getUserByEmailId(emailString);
			String name = userSummary.getFirstName() + " " + userSummary.getLastName();
			String role = enrollService.getRole(userSummary);

			mView.addObject("status", false);
			if (role.compareTo("Instructor") == 0) {
				mView.addObject("status", true);
			}

			mView.addObject("Role", role);
			mView.addObject("name", name);
			return mView;
		}

	}
}

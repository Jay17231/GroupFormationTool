package csci5408.catme.controller;

import csci5408.catme.authentication.ISessionStore;
import csci5408.catme.dao.ICourseDao;
import csci5408.catme.dto.UserSummary;
import csci5408.catme.service.IAuthenticationService;
import csci5408.catme.service.IEnrollmentService;
import csci5408.catme.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static csci5408.catme.authentication.AuthConfig.AUTH_COOKIE_NAME;

/**
 * @author krupa
 */

@Controller
public class LoginController {

	final IUserService userService;
	final IEnrollmentService enrollService;
	final IAuthenticationService authenticationService;
	final ICourseDao courseDao;
	final ISessionStore sessionStore;

	public LoginController(IUserService userService, IEnrollmentService enrollService,
						   IAuthenticationService authenticationService, ICourseDao courseDao,
						   ISessionStore sessionStore) {
		this.authenticationService = authenticationService;
		this.userService = userService;
		this.enrollService = enrollService;
		this.courseDao = courseDao;
		this.sessionStore = sessionStore;
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
		if (authenticationService.isAuthenticated()) {
			return "redirect:";
		}
		return "login.html"; // extension depends on view resolver.
	}

	@PostMapping("/login")
	public ModelAndView loginPost(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mView;
		boolean authState = authenticationService.login(request.getParameter("email"), request.getParameter("password"),
				response);

		if (!authState) {
			return new ModelAndView("redirect:login");
		}

		if (authenticationService.getLoggedInUser().getAdmin()) {
			mView = new ModelAndView("redirect:adminDashboard");
			return mView;
		} else {
			return new ModelAndView("redirect:courses");
		}
	}

	@GetMapping("/signout")
	public String logout(HttpServletRequest request) {
		Cookie[] cookies = ((HttpServletRequest)request).getCookies();
		if(cookies != null) {
			for (Cookie cookie : cookies) {
				String cookieName = cookie.getName();
				if(cookieName.equals(AUTH_COOKIE_NAME)) {
					sessionStore.invalidateSession(cookie.getValue());
					try {
						request.logout();
					} catch (ServletException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return "redirect:/";
	}
}

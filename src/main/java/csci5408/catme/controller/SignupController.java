package csci5408.catme.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import csci5408.catme.authentication.ISessionStore;
import csci5408.catme.dao.ICourseDao;
import csci5408.catme.dto.PasswordPolicyRule;
import csci5408.catme.dto.UserSummary;
import csci5408.catme.service.IAuthenticationService;
import csci5408.catme.service.IEnrollmentService;
import csci5408.catme.service.IPasswordValidationService;
import csci5408.catme.service.IUserService;

@Controller
public class SignupController {
	
	final IUserService userService;
	final IEnrollmentService enrollService;
	final IAuthenticationService authenticationService;
	final ICourseDao courseDao;
	final ISessionStore sessionStore;
	final IPasswordValidationService passwordValidationService;
	PasswordPolicyRule passwordValidated;

	public SignupController(IUserService userService, IEnrollmentService enrollService,
						   IAuthenticationService authenticationService, ICourseDao courseDao,
						   ISessionStore sessionStore, IPasswordValidationService passwordValidationService) {
		this.authenticationService = authenticationService;
		this.userService = userService;
		this.enrollService = enrollService;
		this.courseDao = courseDao;
		this.sessionStore = sessionStore;
		this.passwordValidationService = passwordValidationService;
	}	
	
	@GetMapping("/signup")
	public String signUp(Model model) {
		model.addAttribute("signup", new UserSummary());
		return "signup"; // extension depends on view resolver.
	}
	
	@PostMapping("/signup")
	public String signupPost(@ModelAttribute UserSummary signup, HttpServletRequest request, Model model) {
		
		passwordValidated = passwordValidationService.validatePassword(request.getParameter("password").toString());
		if(passwordValidationService.isValidated())
		{
			UserSummary userSummary = authenticationService.signUp(signup, request.getParameter("password").toString());
			return "redirect:login"; // extension depends on view resolver.
		}
		else {
			model.addAttribute("signup", new UserSummary());
			model.addAttribute("minLength",passwordValidated.isMinLength());
			model.addAttribute("maxLength",passwordValidated.isMaxLength());
			model.addAttribute("minUpper",passwordValidated.isMinUpperCase());
			model.addAttribute("minLower",passwordValidated.isMinLowerCase());
			model.addAttribute("minSymbol",passwordValidated.isMinSymbol());
			model.addAttribute("blockSymbol",passwordValidated.isBlockChar());
			return "signup-error.html";
		}	
	}
	

}

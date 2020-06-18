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
import csci5408.catme.dto.PasswordValidationResult;
import csci5408.catme.dto.UserSummary;
import csci5408.catme.service.IAuthenticationService;
import csci5408.catme.service.IEnrollmentService;
import csci5408.catme.service.IPasswordValidationService;
import csci5408.catme.service.IUserService;

@Controller
public class SignupController {
	
	
	final IAuthenticationService authenticationService;
	final IPasswordValidationService passwordValidationService;
	PasswordValidationResult passwordValidationResult;

	public SignupController(IAuthenticationService authenticationService, IPasswordValidationService passwordValidationService) {
		this.authenticationService = authenticationService;
		this.passwordValidationService = passwordValidationService;
	}	
	
	@GetMapping("/signup")
	public String signUp(Model model) {
		model.addAttribute("signup", new UserSummary());
		return "signup"; // extension depends on view resolver.
	}
	
	@PostMapping("/signup")
	public String signupPost(@ModelAttribute UserSummary signup, HttpServletRequest request, Model model) {
		
		passwordValidationResult = passwordValidationService.validatePassword(request.getParameter("password").toString());
		if(passwordValidationResult.isValidated())
		{
			UserSummary userSummary = authenticationService.signUp(signup, request.getParameter("password").toString());
			return "redirect:login"; // extension depends on view resolver.
		}
		else {
			model.addAttribute("signup", new UserSummary());
			model.addAttribute("minLength",passwordValidationResult.isMinLength());
			model.addAttribute("maxLength",passwordValidationResult.isMaxLength());
			model.addAttribute("minUpper",passwordValidationResult.isMinUpperCase());
			model.addAttribute("minLower",passwordValidationResult.isMinLowerCase());
			model.addAttribute("minSymbol",passwordValidationResult.isMinSymbol());
			model.addAttribute("blockSymbol",passwordValidationResult.isBlockChar());
			return "signup-error.html";
		}	
	}
	

}

package csci5408.catme.controller;

import csci5408.catme.configuration.ConfigProperties;
import csci5408.catme.dao.impl.PasswordHistoryDaoImpl;
import csci5408.catme.service.IAuthenticationService;
import csci5408.catme.service.IEmailService;
import csci5408.catme.service.IPasswordValidationService;
import csci5408.catme.service.IUserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ForgotPasswordController {

	final IUserService userService;
	final IEmailService emailService;
	final IAuthenticationService authenticationService;
	final IPasswordValidationService passwordValidationService;

	public ForgotPasswordController(
			IUserService user, IEmailService mail, IAuthenticationService auth,
			PasswordHistoryDaoImpl passwordHistoryDao, BCryptPasswordEncoder bCryptPasswordEncoder,
			ConfigProperties configProperties,
			IPasswordValidationService passwordValidationService) {
		this.userService = user;
		this.emailService = mail;
		this.authenticationService = auth;
		this.passwordValidationService = passwordValidationService;
	}

	@GetMapping("/forgotpassword")
	public String forgotPassword() {
		return "forgot-password.html"; // extension depends on view resolver.
	}

	@RequestMapping("password-reset")
	public ModelAndView passwordSent(@RequestParam("email") String userEmail) {

		ModelAndView mView = new ModelAndView("password-reset");
		mView.addObject("status", false);
		// Check if user exists
		if (userService.getUserByEmailId(userEmail) == null) {
			mView.addObject("status", false);
			return mView.addObject("name", "User does not exist. Please try a different email address");
		}

		emailService.sendResetPasswordLink(userEmail);

		mView.addObject("email", userEmail);
		mView.addObject("status", true);
		return mView;
	}

	@GetMapping("/update-password")
	public ModelAndView updatePassword(@RequestParam("email") String useremail,
									   @RequestParam(value = "status", defaultValue = "true") Boolean status) {

		if (userService.getUserByEmailId(useremail) != null) {
			ModelAndView mView = new ModelAndView("update-password");
			mView.addObject("status", status);
			mView.addObject("email", useremail);
			mView.addObject("password", "");

			return mView;
		} else {
			return null;
		}
	}

	@PostMapping("/update-password-submit")
	public ModelAndView updatePasswordSubmit(
			@RequestParam("email") String email,
			@RequestParam("password") String password
	) {
		boolean isOldPassword = passwordValidationService.isOldPassword(email, password);
		if (isOldPassword) {
			ModelAndView mView = new ModelAndView("redirect:/update-password?email=" + email);
			mView.addObject("status", false);
			return mView;
		}
		authenticationService.changePassword(userService.getUserByEmailId(email), password);
		ModelAndView mView = new ModelAndView("login");
		mView.addObject("status", true);
		return mView;
	}


}

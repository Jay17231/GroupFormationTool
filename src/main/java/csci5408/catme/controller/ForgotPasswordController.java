package csci5408.catme.controller;

import csci5408.catme.configuration.ConfigProperties;
import csci5408.catme.dao.IPasswordHistoryDao;
import csci5408.catme.dao.impl.PasswordHistoryDaoImpl;
import csci5408.catme.domain.PasswordHistory;
import csci5408.catme.dto.UserSummary;
import csci5408.catme.service.IAuthenticationService;
import csci5408.catme.service.IEmailService;
import csci5408.catme.service.IUserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ForgotPasswordController {

	final IUserService userService;
	final IEmailService emailService;
	final IAuthenticationService authenticationService;
	final IPasswordHistoryDao passwordHistoryDao;
	final BCryptPasswordEncoder bCryptPasswordEncoder;
	final ConfigProperties configProperties;

	public ForgotPasswordController(
			IUserService user, IEmailService mail, IAuthenticationService auth,
			PasswordHistoryDaoImpl passwordHistoryDao, BCryptPasswordEncoder bCryptPasswordEncoder,
			ConfigProperties configProperties
	) {
		this.userService = user;
		this.emailService = mail;
		this.authenticationService = auth;
		this.passwordHistoryDao = passwordHistoryDao;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.configProperties = configProperties;
	}

	@GetMapping("/forgotpassword")
	public String forgotPassword() {
		return "forgot-password.html"; // extension depends on view resolver.
	}

	@RequestMapping("password-reset")
	public ModelAndView passwordSent(@RequestParam("email") String useremail) {

		ModelAndView mView = new ModelAndView("password-reset");
		mView.addObject("status", false);
		// Check if user exists
		if (userService.getUserByEmailId(useremail) == null) {
			mView.addObject("status", false);
			return mView.addObject("name", "User does not exist. Please try a different email address");
		}
		String newPass = authenticationService.resetPassword();

		String url= configProperties.getHostname() +"update-password?email=" + useremail;

		String nameString = userService.getUserByEmailId(useremail).getFirstName();

		mView.addObject("newPassword", newPass);
		mView.addObject("name", nameString);
		mView.addObject("email", useremail);
		mView.addObject("status", true);

		authenticationService.changePassword(userService.getUserByEmailId(useremail), newPass);

		emailService.sendMail(userService.getUserByEmailId(useremail), "Forgot Password - New Credentials",
				"Your login email: " + useremail + ". Your Password: \n" + url);

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
		UserSummary userSummary = userService.getUserByEmailId(email);
		List<PasswordHistory> lists = passwordHistoryDao.getPasswordsByUserId(userSummary.getId());
		for (int i = 0; i < lists.size(); i++) {
			System.out.println(lists.get(i).getPassword());
			if (bCryptPasswordEncoder.matches(password, lists.get(i).getPassword())) {
				ModelAndView mView = new ModelAndView("redirect:/update-password?email=" + email);
				mView.addObject("status", false);

				return mView;

			}
		}

		authenticationService.changePassword(userService.getUserByEmailId(email), password);
		passwordHistoryDao.passwordInsert(userSummary.getId(), bCryptPasswordEncoder.encode(password));


		ModelAndView mView = new ModelAndView("login");
		mView.addObject("status", true);
		return mView;
	}


}

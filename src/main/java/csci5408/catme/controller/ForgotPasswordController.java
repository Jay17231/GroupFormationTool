package csci5408.catme.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import csci5408.catme.service.AuthenticationService;
import csci5408.catme.service.EmailService;
import csci5408.catme.service.UserService;

@Controller
public class ForgotPasswordController {

	final UserService user;
	final EmailService mail;
	final AuthenticationService auth;

	public ForgotPasswordController(UserService user, EmailService mail, AuthenticationService auth) {
		this.user = user;
		this.mail = mail;
		this.auth = auth;
	}

	@RequestMapping("password-reset")
	public ModelAndView passwordSent(@RequestParam("email") String useremail) {

		ModelAndView mView = new ModelAndView("password-reset");
		mView.addObject("status", false);
		// Check if user exists
		if (user.getUserByEmailId(useremail) == null) {
			mView.addObject("status", false);
			return mView.addObject("name", "User does not exist. Please try a different email address");
		}
		String newPass = auth.resetPassword(8);

		String nameString = user.getUserByEmailId(useremail).getFirstName();

		mView.addObject("newPassword", newPass);
		mView.addObject("name", nameString);
		mView.addObject("email", useremail);
		mView.addObject("status", true);

		auth.changePassword(user.getUserByEmailId(useremail), newPass);

		mail.sendMail(user.getUserByEmailId(useremail), "Forgot Password - New Credentials",
				"Your login email: " + useremail + ". Your Password: " + newPass);

		return mView;
	}

}

package csci5408.catme.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import csci5408.catme.service.AuthenticationService;
import csci5408.catme.service.EmailNotifications;
import csci5408.catme.service.UserService;

@Controller
public class ForgotPasswordController {

	final UserService user;
	final EmailNotifications mail;
	final AuthenticationService auth;

	public ForgotPasswordController(UserService user, EmailNotifications mail, AuthenticationService auth) {
		this.user = user;
		this.mail = mail;
		this.auth = auth;
	}

	@RequestMapping("password-reset")
	public ModelAndView passwordSent(@RequestParam("email") String useremail) {

		ModelAndView mView = new ModelAndView("password-reset");

		// Check if user exists
		if (user.getUserByEmailId(useremail) == null) {
			return mView.addObject("name", "User does not exist. Please try a different email address");
		}
		String newPass = auth.generatePassword(8);

		String nameString = user.getUserByEmailId(useremail).getFirstName();

		mView.addObject("newPassword", newPass);
		mView.addObject("name", nameString);
		mView.addObject("email", useremail);

		auth.changePassword(user.getUserByEmailId(useremail), newPass);

		mail.sendMail(user.getUserByEmailId(useremail), "Forgot Password - New Credentials",
				"Your login email: " + useremail + ". Your Password: " + newPass);

		return mView;
	}

}

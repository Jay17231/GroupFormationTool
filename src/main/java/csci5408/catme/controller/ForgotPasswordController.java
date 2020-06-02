package csci5408.catme.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import csci5408.catme.service.EmailNotifications;
import csci5408.catme.service.UserService;

@Controller
public class ForgotPasswordController {
	
	final UserService user;
	final EmailNotifications mail;
	
	public ForgotPasswordController(UserService user, EmailNotifications mail) {
		this.user = user;
		this.mail = mail;
	}
	
	@RequestMapping("password-sent")
	@ResponseBody
	public String passwordSent(@RequestParam("email") String useremail) {
		
		//Check if user exists
		if(user.getUserByEmailId(useremail) == null) {
			return "Invalid Email ID, Please try a different email";
		}
		
		
		
		mail.sendMail(user.getUserByEmailId(useremail), "This is a test email", "Mail from the Controller");
		
		return " An Email with your login credentials has been sent to " + useremail;
	}
	
}

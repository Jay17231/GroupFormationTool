/**
 * 
 */
package csci5408.catme.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import csci5408.catme.domain.User;
import csci5408.catme.dto.UserSummary;
import csci5408.catme.service.impl.AuthenticationServiceImpl;

/**
 * @author krupa
 *
 */

@Controller
public class LoginController {

	@Autowired
	AuthenticationServiceImpl authenticationService;
	UserSummary userSummary;
	
	@GetMapping("/signup")
    public String signup(Model model) {
		model.addAttribute("signup", new UserSummary());
      return "signup"; //extension depends on view resolver.
  }
	
	@PostMapping("/signup")
    public String signupPost(@ModelAttribute UserSummary signup, HttpServletRequest request) {
//		signup.setStudentId("B00000001");
//		signup.setAdmin(true);
		userSummary = authenticationService.signUp(signup, request.getParameter("password").toString());
      return "login"; //extension depends on view resolver.
  }
	
	@GetMapping("/forgotpassword")
    public String forgotpassword() {
      return "ForgotPassword.html"; //extension depends on view resolver.
  }
	
	@GetMapping("/login")
    public String login() {
      return "login.html"; //extension depends on view resolver.
  }
	
	@PostMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
		
	
		if(authenticationService.login(request.getParameter("email"),request.getParameter("password") , response))
		{
			if(authenticationService.isAdmin(request.getParameter("email"),request.getParameter("password")))
			{
				return "adminDashboard";
			}
			else {
				return "home.html";
			}
		}
		else {
			return "login.html"; //extension depends on view resolver.
		}
	
	
	}
}

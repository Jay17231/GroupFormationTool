/**
 * 
 */
package csci5408.catme.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author krupa
 *
 */

@Controller
public class LoginController {

	
	@GetMapping("/signup")
    public String signup() {
      return "signup.html"; //extension depends on view resolver.
  }
	
	@GetMapping("/forgotpassword")
    public String forgotpassword() {
      return "ForgotPassword.html"; //extension depends on view resolver.
  }
	
	@GetMapping("/login")
    public String login() {
      return "login.html"; //extension depends on view resolver.
  }
	
	
}

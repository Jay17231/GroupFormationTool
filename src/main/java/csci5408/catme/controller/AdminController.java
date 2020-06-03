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
public class AdminController {
	
	@GetMapping("/adminDashboard")
    public String signup() {
      return "adminDashboard"; 
  }
	
	@GetMapping("/signout")
    public String signout() {
      return "logout.html"; 
  }
	
	@GetMapping("/viewcourse")
    public String courses() {
      return "admin_courses"; 
  }
	
	

}

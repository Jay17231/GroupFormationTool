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
	
	@GetMapping("/adminhome")
    public String signup() {
      return "adminDashboard"; 
  }
	
	@GetMapping("/signout")
    public String signout() {
      return "logout.html"; 
  }
	
	@GetMapping("/courses")
    public String courses() {
      return "courses"; 
  }
	
	

}

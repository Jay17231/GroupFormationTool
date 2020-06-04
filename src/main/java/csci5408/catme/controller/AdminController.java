/**
 * 
 */
package csci5408.catme.controller;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import csci5408.catme.models.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

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

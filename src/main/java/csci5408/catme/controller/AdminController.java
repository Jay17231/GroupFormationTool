/**
 * 
 */
package csci5408.catme.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import csci5408.catme.dao.impl.CourseDaoImpl;
import csci5408.catme.domain.Course;
import csci5408.catme.sql.ConnectionManager;

/**
 * @author krupa
 *
 */

@Controller
public class AdminController {

	ConnectionManager dataSource = new ConnectionManager();
	CourseDaoImpl cdao = new CourseDaoImpl(dataSource);

	public AdminController(ConnectionManager datasource, CourseDaoImpl cdao) {
		this.dataSource = datasource;
		this.cdao = cdao;
	}

	@GetMapping("/adminDashboard")
	public String signup(Model model) {
		List<Course> courses = cdao.findAll();

		model.addAttribute("allCourse", courses);
		return "adminDashboard";
	}

	@PostMapping("/adminDashboard")
	public String viewcourses(@ModelAttribute Course allCourse) {

		return "adminDashboard";

	}

	@GetMapping("/signout")
	public String signout() {
		return "logout.html";
	}

	@GetMapping("/viewcourse/{id}")
	public String courses(@PathVariable("id") String id, Model model) {
		int cid = Integer.parseInt(id);
		Course course = cdao.findCoursesById(cid);
		model.addAttribute("course", course);
		return "admin_courses";
	}

	@PostMapping("/viewcourse/{id}")
	public String postcourses(@ModelAttribute Course course) {

		return "admin_courses";

	}

	@GetMapping("/createCourse")
	public String createCourse(Model model) {
		model.addAttribute("createCourse", new Course());
		return "createCourse";
	}

	@PostMapping("/createCourse")
	public String createCoursePost(@ModelAttribute Course createCourse) {

		Course c = cdao.save(createCourse);
		return "redirect:/adminDashboard";
	}

	@GetMapping("/deleteCourse/{id}")
	public String deleteCourse(@PathVariable("id") String id) {
		Course c = new Course();
		int cid = Integer.parseInt(id);
		c.setId(cid);
		cdao.delete(c);
		return "redirect:/adminDashboard";
	}

	@PostMapping("/deleteCourse/{id}")
	public String deleteCoursePost() {

		return "redirect:/adminDashboard";
	}

}

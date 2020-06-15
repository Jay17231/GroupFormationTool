/**
 * 
 */
package csci5408.catme.controller;

import csci5408.catme.dao.impl.CourseDaoImpl;
import csci5408.catme.domain.Course;
import csci5408.catme.sql.impl.ConnectionManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @author krupa
 */

@Controller
public class AdminController {

	ConnectionManager dataSource;
	CourseDaoImpl courseDao;

	public AdminController(ConnectionManager datasource, CourseDaoImpl courseDao) {
		this.dataSource = datasource;
		this.courseDao = courseDao;
	}

	@GetMapping("/adminDashboard")
	public String signup(Model model) {
		List<Course> courses = courseDao.findAll();

		model.addAttribute("allCourse", courses);
		return "adminDashboard";
	}

	@PostMapping("/adminDashboard")
	public String viewcourses(@ModelAttribute Course allCourse) {

		return "adminDashboard";

	}


	@GetMapping("/viewcourse/{id}")
	public String courses(@PathVariable("id") String id, Model model) {

		Course course = courseDao.findCoursesById(Long.valueOf(id));
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

		Course c = courseDao.save(createCourse);
		return "redirect:/adminDashboard";
	}

	@GetMapping("/deleteCourse/{id}")
	public String deleteCourse(@PathVariable("id") String id) {
		Course c = new Course();
		int cid = Integer.parseInt(id);
		c.setId(Long.valueOf(id));
		courseDao.delete(c);
		return "redirect:/adminDashboard";
	}

	@PostMapping("/deleteCourse/{id}")
	public String deleteCoursePost() {

		return "redirect:/adminDashboard";
	}

}

package csci5408.catme.controller;

import csci5408.catme.dao.ICourseDao;
import csci5408.catme.domain.Course;
import csci5408.catme.domain.Operation;
import csci5408.catme.dto.CourseRole;
import csci5408.catme.dto.CourseSummary;
import csci5408.catme.dto.UserSummary;
import csci5408.catme.service.IAuthenticationService;
import csci5408.catme.service.IEnrollmentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class CourseController {

	final IAuthenticationService authenticationService;
	final ICourseDao courseDao;
	final IEnrollmentService enrollmentService;

	public CourseController(IAuthenticationService authenticationService, ICourseDao courseDao,
							IEnrollmentService enrollmentService) {
		this.authenticationService = authenticationService;
		this.courseDao = courseDao;
		this.enrollmentService = enrollmentService;
	}

	@GetMapping("/courses")
	public ModelAndView courseGreeting() {
		Course userCourse;
		ModelAndView mView;
		if (!authenticationService.isAuthenticated()) {
			return new ModelAndView("redirect:login");
		}
		UserSummary userSummary = authenticationService.getLoggedInUser();
		if (userSummary.getAdmin()) {
			mView = new ModelAndView("admin-dashboard");
			return mView;
		} else {
			Long userId = userSummary.getId();
			List<CourseRole> userCourses = enrollmentService.getUserCoursesAndRoles(userSummary);
			List<Course> globalCourses = courseDao.findAll();
			mView = new ModelAndView("courses");
			mView.addObject("userCourses", userCourses);
			mView.addObject("globalCourses", globalCourses);

			return mView;

		}
	}

	@GetMapping("/courses/{courseId}")
	public ModelAndView coursePage(@PathVariable Long courseId) {
		if (!authenticationService.isAuthenticated()) {
			return new ModelAndView("redirect:/login");
		}
		ModelAndView modelAndView = new ModelAndView("course-single");
		Optional<Course> c = courseDao.findById(courseId);
		CourseRole courseRole = null;
		if (c.isPresent()) {
			courseRole = enrollmentService.getCourseRole(authenticationService.getLoggedInUser(),
					CourseSummary.from(c.get()), true);
		}
		boolean addStudents = false, addTa = false;
		for (Operation operation : courseRole.getPermissions()) {
			if (operation.toString().equals("ADD_STUDENTS")) {
				addStudents = true;
			}
			if (operation.toString().equals("ADD_TA")) {
				addTa = true;
			}
		}
		modelAndView.addObject("role", courseRole);
		modelAndView.addObject("courseOp", c);
		modelAndView.addObject("addStudents", addStudents);
		modelAndView.addObject("addTa", addTa);
		return modelAndView;
	}
}

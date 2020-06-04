package csci5408.catme.controller;

import csci5408.catme.dao.CourseDao;
import csci5408.catme.domain.Course;
import csci5408.catme.dto.UserSummary;
import csci5408.catme.service.AuthenticationService;
import csci5408.catme.service.EnrollmentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CourseController {

	final AuthenticationService authenticationService;
	final CourseDao courseDao;
	final EnrollmentService enrollmentService;

	public CourseController(AuthenticationService authenticationService, CourseDao courseDao, EnrollmentService enrollmentService) {
		this.authenticationService = authenticationService;
		this.courseDao = courseDao;
		this.enrollmentService = enrollmentService;
	}

	@GetMapping("/courses")
	public ModelAndView courseGreeting() {
		Course userCourse;
		ModelAndView mView;
		if(!authenticationService.isAuthenticated()) {
			return new ModelAndView("redirect:login");
		}
		UserSummary userSummary = authenticationService.getLoggedInUser();
		if (userSummary.getAdmin()) {
			mView = new ModelAndView("adminDashboard");
			return mView;
		} else {
			Long userId = userSummary.getId();
			List<Course> userCourses = courseDao.findCoursesByUserId(userId);
			List<Course> globalCourses = courseDao.findAll();
			mView = new ModelAndView("courses");
			mView.addObject("userCourses", userCourses);
			mView.addObject("globalCourses", globalCourses);
			return mView;

//			if (userCourses.size() > 0) {
//				userCourse = userCourses.get(0);
//				String courseName = userCourse.getCourseName();
//				String name = userSummary.getFirstName() + " " + userSummary.getLastName();
//				Role userRole = enrollmentService.getRole(userSummary);
//				if (userRole == null) {
//					mView = new ModelAndView("courses");
//					mView.addObject("guest", true);
//					return mView;
//				}
//				String role = userRole.getName();
//				Long roleId = userRole.getId();
//				if (role.compareToIgnoreCase("Student") == 0) {
//					mView = new ModelAndView("courses");
//					mView.addObject("student", true);
//					return mView;
//				}
//
//				mView = new ModelAndView("home");
//				mView.addObject("course", courseName);
//				mView.addObject("courseid", userCourse.getId());
//				mView.addObject("userid", userId);
//				mView.addObject("status", false);
//				if (role.compareTo("Instructor") == 0) {
//					mView.addObject("status", true);
//				}
//
//				mView.addObject("Role", role);
//				mView.addObject("roleid", roleId);
//				mView.addObject("name", name);
//				return mView;
//			} else {
//				mView = new ModelAndView("courses");
//				mView.addObject("globalCourses", courseDao.findAll());
//				return mView;
//			}

		}
	}
}

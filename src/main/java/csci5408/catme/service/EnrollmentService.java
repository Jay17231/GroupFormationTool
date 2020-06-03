package csci5408.catme.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import csci5408.catme.dto.CourseSummary;
import csci5408.catme.dto.UserSummary;

public interface EnrollmentService {
	boolean enrollStudent(CourseSummary c, UserSummary u);

	boolean enrollStudents(CourseSummary c, List<UserSummary> u);

	boolean enrollStudents(CourseSummary c, MultipartFile file);

	String getRole(UserSummary user);
}

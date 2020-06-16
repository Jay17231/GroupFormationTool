package csci5408.catme.controller;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import csci5408.catme.dao.IEnrollmentDao;
import csci5408.catme.domain.Enrollment;
import csci5408.catme.dto.UserSummary;
import csci5408.catme.service.IAuthenticationService;
import csci5408.catme.service.IEmailService;
import csci5408.catme.service.IUserService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Jay Gajjar (jy386888@dal.ca)
 */

@Controller
public class UploadCSVController {

	private List<String[]> addedRecords;
	private List<String[]> discardRecords;

	final IUserService user;
	final UserSummary userSummary;
	final IAuthenticationService auth;
	final IEnrollmentDao enrollmentDao;

	final IEmailService mail;

	public UploadCSVController(IUserService user, IAuthenticationService auth, IEnrollmentDao enrollmentDao,
							   IEmailService mail) {
		this.auth = auth;
		this.user = user;
		this.mail = mail;
		this.enrollmentDao = enrollmentDao;
		addedRecords = new ArrayList<String[]>();
		discardRecords = new ArrayList<String[]>();
		userSummary = new UserSummary();
	}

	@PostMapping("/upload-csv-file")
	public String uploadCSVFile(@RequestParam("file") MultipartFile file, Model model,
			@RequestParam("courseid") Long courseid) {

		// validate file
		if (file.isEmpty()) {
			model.addAttribute("message", "Please select a CSV file to upload.");
			model.addAttribute("status", false);
		} else {
			// parse CSV file to create a list of `User` objects
			try {

				Reader reader = new InputStreamReader(file.getInputStream());
				Long userLong = null;
				CSVReader csvReader = new CSVReaderBuilder(reader).build();
				List<String[]> studentRecords = csvReader.readAll();
				Iterator<String[]> recIter = studentRecords.iterator();

				while (recIter.hasNext()) {
					String[] record = recIter.next();
					signUpStudentCsv(record, courseid);
				}

				model.addAttribute("added", addedRecords);
				model.addAttribute("discarded", discardRecords);
				model.addAttribute("status", true);

			} catch (Exception ex) {
				model.addAttribute("message", "An error occurred while processing the CSV file." + " ERR:  " + ex);
				ex.printStackTrace();
				model.addAttribute("status", false);
			}
		}

		return "file-upload-status";
	}

	private void signUpStudentCsv(String[] studRecord, Long courseid) {

		if (studRecord.length != 4) {
			studRecord = ArrayUtils.add(studRecord, "Invalid Entry");
			discardRecords.add(studRecord);
			return;
		}

		String studentId = studRecord[0];
		String firstName = studRecord[1];
		String lastName = studRecord[2];
		String emailId = studRecord[3];

		UserSummary userExist = user.getUserByEmailId(emailId);

		if (userExist != null) {
			studRecord = ArrayUtils.add(studRecord, "User Exists");
			discardRecords.add(studRecord);
			return;
		}

		String password = auth.resetPassword();
		userSummary.setEmailId(emailId);
		userSummary.setLastName(lastName);
		userSummary.setFirstName(firstName);
		userSummary.setStudentId(studentId);
		userSummary.setAdmin(false);

		auth.signUp(userSummary, password); // Signing up

		UserSummary newUser = user.getUserByEmailId(emailId);

		Long roleId = (long) 402; // Role ID for student;
		Enrollment enrollment = new Enrollment();
		enrollment.setCourseId(courseid);
		enrollment.setUserId(newUser.getId());
		enrollment.setRoleId(roleId);
		enrollment.setCourseId(courseid);

		enrollmentDao.save(enrollment); // Enrolling into course

		mail.sendMail(newUser, "New Student Account - Credentials", "Hello " + firstName + " " + lastName
				+ ". Your login email is: " + emailId + " and your password is " + password);

		addedRecords.add(studRecord);

	}

}

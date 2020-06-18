package csci5408.catme.service;

import csci5408.catme.dto.UserSummary;

public interface IEmailService {
	boolean sendMail(UserSummary summary, String subject, String body);

	public boolean sendMail(String emailId, String subject, String body);

	boolean sendResetPasswordLink(String userEmail);
}

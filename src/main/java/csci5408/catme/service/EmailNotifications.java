package csci5408.catme.service;

import csci5408.catme.dto.UserSummary;

public interface EmailNotifications {
	boolean sendMail(UserSummary summary, String subject, String body);
}

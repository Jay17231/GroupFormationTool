package csci5408.catme.service.impl;

import csci5408.catme.configuration.ConfigProperties;
import csci5408.catme.dto.UserSummary;
import csci5408.catme.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements IEmailService {

	private final JavaMailSender javaMailSender;

	private final ConfigProperties configProperties;

	@Autowired
	public EmailServiceImpl(JavaMailSender javaMailSender, ConfigProperties properties) throws MailException {
		this.javaMailSender = javaMailSender;
		this.configProperties = properties;
	}

	@Override
	public boolean sendMail(UserSummary summary, String subject, String body) {
		return this.sendMail(summary.getEmailId(), subject, body);
	}

	@Override
	public boolean sendMail(String emailId, String subject, String body) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(emailId);
		mailMessage.setFrom(configProperties.getFromEmail());
		mailMessage.setSubject(subject);
		mailMessage.setText(body);
		javaMailSender.send(mailMessage);
		return true;
	}

	@Override
	public boolean sendResetPasswordLink(String userEmail) {
		String url = configProperties.getHostname() + "update-password?email=" + userEmail;

		this.sendMail(userEmail, "Forgot Password - New Credentials",
				"Your login email: " + userEmail + ". Your Password: \n" + url);
		return true;
	}
}

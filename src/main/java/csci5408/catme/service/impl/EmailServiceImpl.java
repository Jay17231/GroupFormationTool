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

	private JavaMailSender javaMailSender;

	private ConfigProperties configProperties;

	@Autowired
	public EmailServiceImpl(JavaMailSender javaMailSender, ConfigProperties properties) throws MailException {
		this.javaMailSender = javaMailSender;
		this.configProperties = properties;
	}

	@Override
	public boolean sendMail(UserSummary summary, String subject, String body) {

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(summary.getEmailId());
		mailMessage.setFrom(configProperties.getFromEmail());
		mailMessage.setSubject(subject);
		mailMessage.setText(body);

		javaMailSender.send(mailMessage);

		return true;
	}
}

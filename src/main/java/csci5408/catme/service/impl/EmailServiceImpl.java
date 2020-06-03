package csci5408.catme.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import csci5408.catme.dto.UserSummary;
import csci5408.catme.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String fromEmail;

	@Autowired
	public EmailServiceImpl(JavaMailSender javaMailSender) throws MailException {
		this.javaMailSender = javaMailSender;
	}

	@Override
	public boolean sendMail(UserSummary summary, String subject, String body) {

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(summary.getEmailId());
		mailMessage.setFrom(fromEmail);
		mailMessage.setSubject(subject);
		mailMessage.setText(body);

		javaMailSender.send(mailMessage);

		return false;
	}
}

package csci5408.catme.service.impl;

import csci5408.catme.configuration.ConfigProperties;
import csci5408.catme.dto.UserSummary;
import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EmailServiceImplTest {

    @Test
    public void sendMailTest() {
        JavaMailSender jms = mock(JavaMailSender.class);
        ConfigProperties cfp = mock(ConfigProperties.class);
        EmailServiceImpl emailService = new EmailServiceImpl(jms, cfp);
        UserSummary userSummary = new UserSummary();
        userSummary.setEmailId("jon@doe.com");
        when(cfp.getFromEmail()).thenReturn("a@b.ccc");
        assertTrue(emailService.sendMail(userSummary, "Hi", "Bye"));
    }
}

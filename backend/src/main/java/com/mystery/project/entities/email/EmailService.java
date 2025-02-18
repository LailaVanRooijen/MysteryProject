package com.mystery.project.entities.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailService {
  private Logger log = Logger.getLogger(EmailService.class.getName());
  private final JavaMailSender mailSender;

  public void sendEmail(String to, String subject, String body) {
    try {
      MimeMessage message = mailSender.createMimeMessage();

      MimeMessageHelper helper = new MimeMessageHelper(message, true);

      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(body, true);

      mailSender.send(message);
      log.info("E-mail verzonden naar: " + to);

    } catch (MailException e) {
      log.info("Exception in Mail: " + e); // todo idk
    } catch (MessagingException e) {
      log.info("Exception in Messaging Mail: " + e); // todo idk
    }
  }
}

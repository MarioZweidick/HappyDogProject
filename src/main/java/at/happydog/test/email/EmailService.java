package at.happydog.test.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 EmailService class

 Implements EmailSender interface

 Sends and logs Emails with JavaMailSender and MimeMessage
 **/

@Service
@AllArgsConstructor
public class EmailService implements EmailSender{

    private final static Logger LOGGER = LoggerFactory
            .getLogger(EmailService.class);
    private final JavaMailSender mailSender;


    //TODO Instead of Sync a Queue for resending email
    @Override
    @Async
    public void send(String to, String email) {

        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject(EmailConstant.EMAIL_SUBJECT);
            helper.setFrom(EmailConstant.EMAIL_FROM);
            mailSender.send(mimeMessage);

        }catch (MessagingException ex){
            LOGGER.error("failed to send email", ex);
            throw new IllegalStateException("failed to send email");
        }

    }
}

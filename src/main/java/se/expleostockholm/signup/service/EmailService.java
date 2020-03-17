package se.expleostockholm.signup.service;

import lombok.AllArgsConstructor;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import se.expleostockholm.signup.HtmlEmailTemplate;
import se.expleostockholm.signup.domain.Event;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;

@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public MimeMessage createMail(String recipient, Event event) {

        HtmlEmailTemplate emailTemplate = new HtmlEmailTemplate(event);
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            Calendar calendar = CalendarService.createIcsCalendar(event);
            helper.setFrom("signup5@kristiangrundstrom.se");
            helper.setTo(recipient);
            helper.setSubject(event.getTitle());
            helper.setText(emailTemplate.getEmailAsString(), true);
            helper.addAttachment("myCalendar.ics", new ByteArrayDataSource(String.valueOf(calendar), "text/calendar"));

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return message;
    }

    public void sendMail(MimeMessage message) throws MessagingException {
        javaMailSender.send(message);
    }
}
package se.expleostockholm.signup.service;

import lombok.AllArgsConstructor;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.context.annotation.Profile;
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

    public MimeMessage createInvitationEmail(String recipient, Event event) {

        HtmlEmailTemplate emailTemplate = new HtmlEmailTemplate(event);
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom(System.getenv("MAIL_FROM"));
            helper.setTo(recipient);
            helper.setSubject(event.getTitle());
            helper.setText(emailTemplate.getInvitationEmail(), true);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return message;
    }

    public MimeMessage createICSCalendarEmail(String recipient, Event event) {

        HtmlEmailTemplate emailTemplate = new HtmlEmailTemplate(event);
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            Calendar calendar = CalendarService.createIcsCalendar(event);
            helper.setFrom(System.getenv("MAIL_FROM"));
            helper.setTo(recipient);
            helper.setSubject(event.getTitle());
            helper.setText(emailTemplate.getAcceptanceEmail(), true);
            helper.addAttachment("myCalendar.ics", new ByteArrayDataSource(String.valueOf(calendar), "text/calendar"));

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }

        return message;
    }

    public void sendMail(MimeMessage message) {
        javaMailSender.send(message);

        System.out.println("sending mail");
    }

}
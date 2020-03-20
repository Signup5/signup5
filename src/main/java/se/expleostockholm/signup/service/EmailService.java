package se.expleostockholm.signup.service;

import lombok.AllArgsConstructor;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import se.expleostockholm.signup.HtmlEmailTemplate;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.domain.Invitation;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.text.ParseException;

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

    public void sendEmailWithCalendarAttachment(String recipient, Event event) {

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

        } catch (MessagingException | IOException | ParseException e) {
            e.printStackTrace();
        }

        javaMailSender.send(message);

    }

    public void sendInvitationEmail(Event event) {
        event.getInvitations().forEach(invitation -> {
            MimeMessage message = createInvitationEmail(invitation.getGuest().getEmail(), event);
            javaMailSender.send(message);
        });
    }

    public void sendCalendarToHostEmail(Event event) {
        sendEmailWithCalendarAttachment(event.getHost().getEmail(), event);
    }

}
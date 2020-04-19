package se.expleostockholm.signup.service;

import java.io.IOException;
import java.text.ParseException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import se.expleostockholm.signup.HtmlEmailTemplate;
import se.expleostockholm.signup.domain.Event;

@Service
public class EmailService {

  private final JavaMailSender javaMailSender;

  @Value("${mail.from.default}")
  private String MAIL_FROM;

  public EmailService(JavaMailSender javaMailSender) {
    this.javaMailSender = javaMailSender;
  }

  /**
   * Method for creating an invitation email to be sent to the recipient.
   * <p>
   * Accepts two arguments; a Recipient who will be getting an invitation to an event and the Event to which the
   * recipient will be invited to.
   *
   * @param recipient the person who will be getting an invitation to an event
   * @param event     the Event to which the Recipient is invited to
   * @return an email with details to what Event the person is invited to
   */
  public MimeMessage createInvitationEmail(String recipient, Event event) {

    HtmlEmailTemplate emailTemplate = new HtmlEmailTemplate(event);
    MimeMessage message = javaMailSender.createMimeMessage();

    try {
      MimeMessageHelper helper = new MimeMessageHelper(message);
      helper.setFrom(MAIL_FROM);
      helper.setTo(recipient);
      helper.setSubject(event.getTitle());
      helper.setText(emailTemplate.getInvitationEmail(), true);

    } catch (MessagingException e) {
      e.printStackTrace();
    }

    return message;
  }

  /**
   * Method for sending an email to a recipient with an ICS Calendar invitation attachment.
   * <p>
   * This method is called when a guest responds to an invitation with the Attendance values;
   * "Attending" or "NOT_ATTENDING".
   *
   * @param recipient person who has responded "Attending" or "NOT_ATTENDING"
   * @param event     the event which recipient has previously been invited to
   */
  public void sendEmailWithCalendarAttachment(String recipient, Event event) {

    HtmlEmailTemplate emailTemplate = new HtmlEmailTemplate(event);
    MimeMessage message = javaMailSender.createMimeMessage();

    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      Calendar calendar = CalendarService.createIcsCalendar(event);
      helper.setFrom(MAIL_FROM);
      helper.setTo(recipient);
      helper.setSubject(event.getTitle());
      helper.setText(emailTemplate.getAcceptanceEmail(), true);
      helper.addAttachment("myCalendar.ics", new ByteArrayDataSource(String.valueOf(calendar), "text/calendar"));

    } catch (MessagingException | IOException | ParseException e) {
      e.printStackTrace();
    }

    javaMailSender.send(message);
  }

  @Async
  public void sendResetPasswordLink(String email, String token) throws MessagingException {
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
    helper.setFrom(MAIL_FROM);
    helper.setTo(email);
    helper.setSubject("Reset password");
    helper.setText("To reset your password, click the link below:\n" + System.getenv("HOST_URL") + "password/new/" + token
        , true);
    javaMailSender.send(mimeMessage);
  }

  @Async
  public void sendInvitationEmail(Event event) {
    event.getInvitations().forEach(invitation -> {
      MimeMessage message = createInvitationEmail(invitation.getGuest().getEmail(), event);
      javaMailSender.send(message);
    });
  }

  @Async
  public void sendCalendarToHostEmail(Event event) {
    sendEmailWithCalendarAttachment(event.getHost().getEmail(), event);
  }

}
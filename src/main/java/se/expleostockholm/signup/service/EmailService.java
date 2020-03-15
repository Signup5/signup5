package se.expleostockholm.signup.service;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.parameter.Cn;
import net.fortuna.ical4j.model.parameter.Role;
import net.fortuna.ical4j.model.parameter.Rsvp;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.FixedUidGenerator;
import net.fortuna.ical4j.util.UidGenerator;
import org.apache.catalina.Host;
import se.expleostockholm.signup.Email;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.domain.Person;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.net.URI;
import java.time.LocalDate;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import static net.fortuna.ical4j.model.Parameter.RSVP;

public class EmailService {

    public static Message createEmail(List<String> recipients, Calendar calendar) {

        final String mail_username = System.getenv("MAIL_USERNAME");
        final String password = System.getenv("MAIL_PASSWORD");
        final String host = System.getenv("MAIL_SMTP_HOST");

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(mail_username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mail_username));
            message.setSubject("Signup5 release party!");

            for (String recipient : recipients) {
                message.addRecipients(Message.RecipientType.CC,
                        InternetAddress.parse(recipient));
            }

            Event testing = new Event();

            testing.setTitle("duude");
            testing.setDate_of_event(LocalDate.of(2020, 4, 21));

            Person hoster = new Person();
            hoster.setFirst_name("kalle");
            hoster.setLast_name("kalas");
            hoster.setEmail("kalle@kalas.se");

            testing.setHost(hoster);

            Email email = new Email(testing);

            BodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(email.getEmailString(), "text/html; charset=utf-8");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(htmlPart);


            BodyPart attachment = new MimeBodyPart();
            String filename = "mycalendar.ics";
            FileOutputStream fout = new FileOutputStream(filename);
            CalendarOutputter outputter = new CalendarOutputter();
            outputter.output(calendar, fout);

            DataSource source = new FileDataSource(filename);
            attachment.setDataHandler(new DataHandler(source));
            attachment.setFileName(filename);

            multipart.addBodyPart(attachment);
            message.setContent(multipart);

            return message;

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void sendEmail(Message message) throws MessagingException {
        Transport.send(message);
        System.out.println("Epost skickat!");
    }
}


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

        String from = "signup5@kristiangrundstrom.se";
        final String username = System.getenv("MAIL_USERNAME");
        final String password = System.getenv("MAIL_PASSWORD");
        final String host = System.getenv("MAIL_SMTP_HOST");

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setSubject("Signup5 release party!");

            for (String recipient : recipients) {
                message.addRecipients(Message.RecipientType.CC,
                        InternetAddress.parse(recipient));
            }

//            BodyPart messageBodyPart = new MimeBodyPart();
//            messageBodyPart.setText("Nu får ni ett fint mail som är autogenererat från vår egen datamaskin.");


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

    // Event
    public static Calendar createCalendar() throws SocketException {

        java.util.Calendar startDate = new GregorianCalendar();
        startDate.set(java.util.Calendar.MONTH, java.util.Calendar.MARCH);
        startDate.set(java.util.Calendar.DAY_OF_MONTH, 14);
        startDate.set(java.util.Calendar.YEAR, 2020);
        startDate.set(java.util.Calendar.HOUR_OF_DAY, 12);
        startDate.set(java.util.Calendar.MINUTE, 0);
        startDate.set(java.util.Calendar.SECOND, 0);

        java.util.Calendar endDate = new GregorianCalendar();
        endDate.set(java.util.Calendar.MONTH, java.util.Calendar.MARCH);
        endDate.set(java.util.Calendar.DAY_OF_MONTH, 16);
        endDate.set(java.util.Calendar.YEAR, 2020);
        endDate.set(java.util.Calendar.HOUR_OF_DAY, 21);
        endDate.set(java.util.Calendar.MINUTE, 0);
        endDate.set(java.util.Calendar.SECOND, 3);

        String eventName = "Signup5 release party! Free champagne!";
        DateTime start = new DateTime(startDate.getTime());
        DateTime end = new DateTime(endDate.getTime());
        VEvent meeting = new VEvent(start, end, eventName);



        UidGenerator ug = new FixedUidGenerator("191");
        Uid uid = ug.generateUid();
        meeting.getProperties().add(uid);

//        Summary summary = new Summary();
//        summary.setValue("this is a summary of the event");
//        meeting.getProperties().add(summary);

        Description description = new Description();
        description.setValue("this is the description of the event!");
        meeting.getProperties().add(description);

        System.out.println(uid);

        Organizer org = new Organizer(URI.create("mailto:marcus8209@gmail.com"));
        org.getParameters().add(Role.REQ_PARTICIPANT);
        org.getParameters().add(Rsvp.TRUE);
        org.getParameters().add(new Cn("org"));
        meeting.getProperties().add(org);



//        Attendee dev1 = new Attendee(URI.create("mailto:marcus8209@gmail.com"));
//        dev1.getParameters().add(Role.REQ_PARTICIPANT);
//        dev1.getParameters().add(Rsvp.TRUE);
//        dev1.getParameters().add(new Cn("Developer 1"));
//        meeting.getProperties().add(dev1);
//
//        Attendee dev2 = new Attendee(URI.create("mailto:dev2@mycompany.com"));
//        dev2.getParameters().add(Role.OPT_PARTICIPANT);
//        dev2.getParameters().add(new Cn("Developer 2"));
//        meeting.getProperties().add(dev2);

        net.fortuna.ical4j.model.Calendar icsCalendar = new net.fortuna.ical4j.model.Calendar();
        icsCalendar.getProperties().add(new ProdId("-//Events Calendar//iCal4j 1.0//EN"));
        icsCalendar.getProperties().add(CalScale.GREGORIAN);
        icsCalendar.getProperties().add(Version.VERSION_2_0);

        icsCalendar.getComponents().add(meeting);

        return icsCalendar;

    }


    public static void sendEmail(Message message) throws MessagingException {
        Transport.send(message);
        System.out.println("Epost skickat!");
    }
}


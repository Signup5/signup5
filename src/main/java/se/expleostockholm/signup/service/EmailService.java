package se.expleostockholm.signup.service;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.FixedUidGenerator;
import net.fortuna.ical4j.util.UidGenerator;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

public class EmailService {

    public static Message createEmail(List<String> recipients, Calendar calendar) {

        String from = "info@kristiangrundstrom.se";
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

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Nu får ni ett fint mail som är autogenererat från vår egen datamaskin.");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);


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
        startDate.set(java.util.Calendar.DAY_OF_MONTH, 13);
        startDate.set(java.util.Calendar.YEAR, 2020);
        startDate.set(java.util.Calendar.HOUR_OF_DAY, 12);
        startDate.set(java.util.Calendar.MINUTE, 0);
        startDate.set(java.util.Calendar.SECOND, 0);

        java.util.Calendar endDate = new GregorianCalendar();
        endDate.set(java.util.Calendar.MONTH, java.util.Calendar.MARCH);
        endDate.set(java.util.Calendar.DAY_OF_MONTH, 15);
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

        System.out.println(uid);

//        Attendee dev1 = new Attendee(URI.create("mailto:dev1@mycompany.com"));
//        dev1.getParameters().add(Role.REQ_PARTICIPANT);
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


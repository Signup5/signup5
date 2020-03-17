package se.expleostockholm.signup.service;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.parameter.Cn;
import net.fortuna.ical4j.model.parameter.Role;
import net.fortuna.ical4j.model.parameter.Rsvp;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.FixedUidGenerator;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.domain.Person;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.net.URI;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

public class CalendarService {

    private static VEvent meeting;

    public static net.fortuna.ical4j.model.Calendar createIcsCalendar(Event event) throws IOException, MessagingException {
        meeting = createMeeting(event);

        net.fortuna.ical4j.model.Calendar calendar = new net.fortuna.ical4j.model.Calendar();
        calendar.getProperties().add(new ProdId("-//Events Calendar//iCal4j 1.0//EN"));
        calendar.getProperties().add(CalScale.GREGORIAN);
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getComponents().add(meeting);
        calendar.validate();

        String filename = "mycalendar.ics";
        FileOutputStream fout = new FileOutputStream(filename);
        CalendarOutputter outputter = new CalendarOutputter();
        outputter.output(calendar, fout);

        DataSource source = new FileDataSource(filename);


        return calendar;
    }

    private static VEvent createMeeting(Event event) throws SocketException {
        final int year = event.getDate_of_event().getYear();
        final int month = event.getDate_of_event().getMonthValue();
        final int day = event.getDate_of_event().getDayOfMonth();
        final int hour = event.getTime_of_event().getHour();
        final int minute = event.getTime_of_event().getMinute();

        final java.util.Calendar startDate = new GregorianCalendar(year, month, day, hour, minute);
        final java.util.Calendar endDate = new GregorianCalendar(year, month, day, hour + 1, minute);
        final DateTime start = new DateTime(startDate.getTime());
        final DateTime end = new DateTime(endDate.getTime());

        meeting = new VEvent(start, end, event.getTitle());
        meeting.getProperties().add(new FixedUidGenerator("11").generateUid());

        meeting.getProperties().add(new Description(event.getDescription()));
        setMeetingOrganizer(event.getHost());
        setMeetingAttendees(event.getInvitations());
        return meeting;
    }

    private static void setMeetingOrganizer(Person host) {
        Organizer org = new Organizer(URI.create("mailto:" + host.getEmail()));
        org.getParameters().add(Role.REQ_PARTICIPANT);
        org.getParameters().add(Rsvp.TRUE);
        org.getParameters().add(new Cn(host.getFirst_name() + " " + host.getLast_name()));
        meeting.getProperties().add(org);
    }

    private static void setMeetingAttendees(List<Invitation> guests) {
        guests.forEach(invitation -> {
            Attendee attendee = new Attendee(URI.create("mailto:" + invitation.getGuest().getEmail()));
            attendee.getParameters().add(Role.REQ_PARTICIPANT);
            attendee.getParameters().add(Rsvp.TRUE);
            attendee.getParameters().add(new Cn(invitation.getGuest().getFirst_name() + " " + invitation.getGuest().getLast_name()));
            meeting.getProperties().add(attendee);
        });
    }
}

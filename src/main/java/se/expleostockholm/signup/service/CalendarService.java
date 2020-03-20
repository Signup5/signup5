package se.expleostockholm.signup.service;

import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.parameter.Cn;
import net.fortuna.ical4j.model.parameter.Role;
import net.fortuna.ical4j.model.parameter.Rsvp;
import net.fortuna.ical4j.model.property.*;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.domain.Person;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class CalendarService {

    private static VEvent meeting;

    public static net.fortuna.ical4j.model.Calendar createIcsCalendar(Event event) throws IOException, MessagingException, ParseException {
        meeting = createMeeting(event);

        net.fortuna.ical4j.model.Calendar calendar = new net.fortuna.ical4j.model.Calendar();
        calendar.getProperties().add(new ProdId("-//Events Calendar//iCal4j 1.0//EN"));
        calendar.getProperties().add(CalScale.GREGORIAN);
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getComponents().add(meeting);
        calendar.validate();

        return calendar;
    }

    private static VEvent createMeeting(Event event) throws ParseException {
        final TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
        final TimeZone eventTimeZone = registry.getTimeZone("UTC");
        final TzId timeZoneId = eventTimeZone.getVTimeZone().getTimeZoneId();
        final java.util.TimeZone calendarTimeZone = java.util.TimeZone.getTimeZone("UTC");

        final LocalDateTime startDateTime = event.toLocalDateTime();
        final LocalDateTime endDateTime = startDateTime.plusMinutes(event.getDuration());

        final java.util.Calendar startDate = new GregorianCalendar(
                startDateTime.getYear(),
                startDateTime.minusMonths(1).getMonthValue(),
                startDateTime.getDayOfMonth(),
                startDateTime.minusHours(1).getHour(),
                startDateTime.getMinute());

        final java.util.Calendar endDate = new GregorianCalendar(
                endDateTime.getYear(),
                endDateTime.minusMonths(1).getMonthValue(),
                endDateTime.getDayOfMonth(),
                endDateTime.minusHours(1).getHour(),
                endDateTime.getMinute());

        startDate.setTimeZone(calendarTimeZone);
        endDate.setTimeZone(calendarTimeZone);

        String s = "20200101T101010";

        final DateTime start = new DateTime("20200101T101010");

        final DateTime end = new DateTime("20200101T111010");

        meeting = new VEvent(start, end, event.getTitle());
        meeting.getProperties().add( new Uid( event.getId() + "@" + event.getHost().getEmail() + "-" + event.getDate_of_event()));

        meeting.getProperties().add(new Description(event.getDescription()));
        setMeetingOrganizer(event.getHost());
        meeting.getProperties().add(timeZoneId);
        setMeetingAttendees(event.getInvitations());
        meeting.getProperties().add(new Location(event.getLocation()));
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
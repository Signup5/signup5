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

import java.net.URI;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;
import java.util.List;

public class CalendarService {

    private static VEvent meeting;

    /**
     * Method for creating an iCal4J v2.0 ICS Calendar specified as a Gregorian calendar.
     * <p>
     * Accepts an Event as argument for creating a VEvent and adding it to the retuning ICS Calendar object.
     *
     * @param event the Event argument for extracting the event details
     * @return a new ICS Calendar
     * @throws ParseException exception is thrown if LocalDateTime fails to convert.
     */
    public static net.fortuna.ical4j.model.Calendar createIcsCalendar(Event event) throws ParseException {
        meeting = createMeeting(event);

        net.fortuna.ical4j.model.Calendar calendar = new net.fortuna.ical4j.model.Calendar();
        calendar.getProperties().add(new ProdId("-//SignUp5 Calendar//iCal4j 1.0//EN"));
        calendar.getProperties().add(CalScale.GREGORIAN);
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getComponents().add(meeting);
        calendar.validate();

        return calendar;
    }

    /**
     * Method for creating a VEvent to be added to an ICS Calendar.
     * <p>
     * Takes in an Event as argument and uses its details to create a VEvent.
     *
     * @param event the Event with invitation details used in the event
     * @return a VEvent used to be used in an ICS Calendar
     * @throws ParseException throws exception if LocalDateTime conversion fails
     */
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
        startDate.setTimeZone(calendarTimeZone);

        final java.util.Calendar endDate = new GregorianCalendar(
                endDateTime.getYear(),
                endDateTime.minusMonths(1).getMonthValue(),
                endDateTime.getDayOfMonth(),
                endDateTime.minusHours(1).getHour(),
                endDateTime.getMinute());
        endDate.setTimeZone(calendarTimeZone);

        final DateTime start = new DateTime(localDateTimeToString(startDateTime));
        final DateTime end = new DateTime(localDateTimeToString(endDateTime));

        meeting = new VEvent(start, end, event.getTitle());
        meeting.getProperties().add(new Uid(event.getId() + "@" + event.getHost().getEmail() + "-" + event.getDate_of_event()));

        meeting.getProperties().add(new Description(event.getDescription()));
        setMeetingOrganizer(event.getHost());
        meeting.getProperties().add(timeZoneId);
        setMeetingAttendees(event.getInvitations());
        meeting.getProperties().add(new Location(event.getLocation()));
        return meeting;
    }

    /**
     * Method for converting a LocalDateTime object to a string for a VEvent to be able to write correct Date and Time
     * to a ICS calendar invitation.
     *
     * @param localDateTime object to be converted
     * @return localDateTime as a string
     */
    public static String localDateTimeToString(LocalDateTime localDateTime) {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter dtfDate = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter dtfTime = DateTimeFormatter.ofPattern("HHmmss");

        return sb.append(localDateTime.format(dtfDate))
                .append("T")
                .append(localDateTime.format(dtfTime)).toString();
    }

    /**
     * Method for setting the Organizer for a VEvent.
     *
     * @param host Person to be set as VEvent organizer
     */
    private static void setMeetingOrganizer(Person host) {
        Organizer org = new Organizer(URI.create("mailto:" + host.getEmail()));
        org.getParameters().add(Role.REQ_PARTICIPANT);
        org.getParameters().add(Rsvp.TRUE);
        org.getParameters().add(new Cn(host.getFirst_name() + " " + host.getLast_name()));
        meeting.getProperties().add(org);
    }

    /**
     * Method for adding guests to a VEvent.
     * <p>
     * Accepts a list of guests as argument for adding guests to a VEvent.
     *
     * @param guests a collection of guests to be invited to a VEvent
     */
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
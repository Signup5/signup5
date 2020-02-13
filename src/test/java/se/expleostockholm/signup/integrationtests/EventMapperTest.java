package se.expleostockholm.signup.integrationtests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.expleostockholm.signup.domain.Attendance;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.domain.Person;
import se.expleostockholm.signup.repository.EventMapper;
import se.expleostockholm.signup.repository.InvitationMapper;
import se.expleostockholm.signup.repository.PersonMapper;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = {SignupDbTests.Initializer.class})
class EventMapperTest extends SignupDbTests {

    @Resource
    EventMapper eventMapper;

    @Resource
    PersonMapper personMapper;

    @Resource
    InvitationMapper invitationMapper;

    @Test
    void getEventById() {
        Optional<Event> actualEvent = eventMapper.getEventById(1L);

        Event expectedEvent = Event.builder()
                .id(1L)
                .title("That Championship Season")
                .description("Enhanced discrete moderator")
                .date_of_event(LocalDate.parse("2020-09-04"))
                .time_of_event(LocalTime.parse("12:00:00"))
                .location("9982 Coleman Terrace")
                .build();

        assertEventsAreEqual(expectedEvent, actualEvent);

    }

    @Test
    void getAllEvents() {
        List<Event> events = eventMapper.getAllEvents();
        assertEquals(11, events.size(), "Number of events did not match!");
    }

    @Test
    void createNewEvent() {
        Person expectedGuest = personMapper.getPersonById(10L).get();
        Event expectedEvent = createMockEvent();
        eventMapper.saveEvent(expectedEvent);

        Invitation expectedInvitation = createMockInvitation(expectedEvent, expectedGuest);
        invitationMapper.saveInvitation(expectedInvitation);

        expectedEvent.setInvitations(List.of(expectedInvitation));
        Optional<Event> actualEvent = eventMapper.getEventById(expectedEvent.getId());

        assertEventsAreEqual(expectedEvent, actualEvent);
        assertPersonsAreEqual(expectedEvent.getHost(), actualEvent.get().getHost(), "Host");
        assertPersonsAreEqual(expectedGuest, actualEvent.get().getInvitations().get(0).getGuest(), "Guest");

        assertAll(
                () -> assertEquals(expectedInvitation.getId(), actualEvent.get().getInvitations().get(0).getId(), "Invitation id did not match!"),
                () -> assertEquals(Attendance.NO_RESPONSE, actualEvent.get().getInvitations().get(0).getAttendance(), "Attendance did not match!")
        );

    }

    Event createMockEvent() {
        String expected_event_description = "This is a test event.";
        String expected_event_location = "Vasagatan 1";
        String expected_event_title = "My event";
        LocalDate expected_event_date = LocalDate.now();
        LocalTime expected_event_time = LocalTime.now();

        return Event.builder()
                .host(personMapper.getPersonById(50L).get())
                .date_of_event(expected_event_date)
                .time_of_event(expected_event_time)
                .description(expected_event_description)
                .location(expected_event_location)
                .title(expected_event_title)
                .build();
    }

    Invitation createMockInvitation(Event expectedEvent, Person guest) {
        return Invitation.builder()
                .event_id(expectedEvent.getId())
                .guest(guest)
                .attendance(Attendance.NO_RESPONSE)
                .build();
    }

    void assertPersonsAreEqual(Person expectedPerson, Person actualPerson, String role) {
        assertAll(
                () -> assertEquals(expectedPerson.getId(), actualPerson.getId(), role + " id did not match!"),
                () -> assertEquals(expectedPerson.getEmail(), actualPerson.getEmail(), role + " email did not match!"),
                () -> assertEquals(expectedPerson.getFirst_name(), actualPerson.getFirst_name(), role + " first name did not match!"),
                () -> assertEquals(expectedPerson.getLast_name(), actualPerson.getLast_name(), role + " last name did not match!")
        );

    }

    void assertEventsAreEqual(Event expectedEvent, Optional<Event> actualEvent) {
        assertAll(
                () -> assertTrue(actualEvent.isPresent(), "No event found!"),
                () -> assertEquals(expectedEvent.getId(), actualEvent.get().getId(), "Event id did not match!"),
                () -> assertEquals(expectedEvent.getDate_of_event(), actualEvent.get().getDate_of_event(), "Event date did not match!!"),
                () -> assertEquals(expectedEvent.getTime_of_event(), actualEvent.get().getTime_of_event(), "Event time did not match!!"),
                () -> assertEquals(expectedEvent.getDescription(), actualEvent.get().getDescription(), "Event description did not match!"),
                () -> assertEquals(expectedEvent.getLocation(), actualEvent.get().getLocation(), "Event location did not match!"),
                () -> assertEquals(expectedEvent.getTitle(), actualEvent.get().getTitle(), "Event title did not match!")
        );
    }

}
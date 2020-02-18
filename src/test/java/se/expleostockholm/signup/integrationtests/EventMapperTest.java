package se.expleostockholm.signup.integrationtests;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.AfterTestClass;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.MethodOrderer.*;
import static org.junit.jupiter.api.TestInstance.*;
import static se.expleostockholm.signup.utils.EventUtils.assertEventsAreEqual;
import static se.expleostockholm.signup.utils.EventUtils.createMockEvent;
import static se.expleostockholm.signup.utils.InvitationUtils.assertInvitationsAreEqual;
import static se.expleostockholm.signup.utils.InvitationUtils.createMockInvitation;
import static se.expleostockholm.signup.utils.PersonUtils.assertPersonsAreEqual;

@TestMethodOrder(OrderAnnotation.class)
@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = {SignupDbTests.Initializer.class})
public class EventMapperTest extends SignupDbTests {

    @Resource
    private EventMapper eventMapper;

    @Resource
    private PersonMapper personMapper;

    @Resource
    private InvitationMapper invitationMapper;

    private Event expectedEvent;
    private Invitation expectedInvitation;

    @AfterTestClass
    public void tearDown() {
        invitationMapper.removeInvitationByEventId(expectedEvent.getId());
        eventMapper.removeEventById(expectedEvent.getId());
    }

    @Test
    @Order(1)
    public void event_exists_success() {
        Optional<Event> actualEvent = eventMapper.getEventById(1L);
        Person expectedGuest = Person.builder()
                .id(8L)
                .email("hroch7@tamu.edu")
                .first_name("Hakim")
                .last_name("Roch")
                .build();

        Invitation expectedInvitation = Invitation.builder()
                .id(7L)
                .event_id(1L)
                .guest(expectedGuest)
                .attendance(Attendance.NO_RESPONSE)
                .build();

        Person expectedHost = Person.builder()
                .id(18L)
                .email("fbartolomeoh@illinois.edu")
                .first_name("Fenelia")
                .last_name("Bartolomeo")
                .build();

        Event expectedEvent = Event.builder()
                .id(1L)
                .title("That Championship Season")
                .description("Enhanced discrete moderator")
                .host(expectedHost)
                .date_of_event(LocalDate.parse("2020-09-04"))
                .time_of_event(LocalTime.parse("12:00:00"))
                .location("9982 Coleman Terrace")
                .invitations(List.of(expectedInvitation))
                .build();

        assertEventsAreEqual(expectedEvent, actualEvent);
    }

    @Test
    @Order(2)
    public void allEvents_nrOfEvents_match() {
        List<Event> events = eventMapper.getAllEvents();
        assertEquals(10, events.size(), "Number of events did not match!");
    }

    @Test
    @Order(3)
    public void event_saved_success() {
        Person expectedHost = personMapper.getPersonById(50L).get();
        Person expectedGuest = personMapper.getPersonById(10L).get();

        expectedEvent = createMockEvent(expectedHost);
        eventMapper.saveEvent(expectedEvent);

        expectedInvitation = createMockInvitation(expectedEvent, expectedGuest);
        invitationMapper.saveInvitation(expectedInvitation);

        expectedEvent.setInvitations(List.of(expectedInvitation));

        Optional<Event> actualEvent = eventMapper.getEventById(expectedEvent.getId());
        Invitation actualInvitation = actualEvent.get().getInvitations().get(0);
        Person actualHost = actualEvent.get().getHost();
        Person actualGuest = actualInvitation.getGuest();

        assertEventsAreEqual(expectedEvent, actualEvent);
        assertPersonsAreEqual(expectedHost, actualHost, "Host");
        assertPersonsAreEqual(expectedGuest, actualGuest, "Guest");
        assertInvitationsAreEqual(expectedInvitation, actualInvitation);
    }
}
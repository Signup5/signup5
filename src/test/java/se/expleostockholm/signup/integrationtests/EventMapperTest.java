package se.expleostockholm.signup.integrationtests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.domain.Person;
import se.expleostockholm.signup.repository.EventMapper;
import se.expleostockholm.signup.repository.InvitationMapper;
import se.expleostockholm.signup.repository.PersonMapper;
import se.expleostockholm.signup.utils.EventUtils;
import se.expleostockholm.signup.utils.InvitationUtils;
import se.expleostockholm.signup.utils.PersonUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        EventUtils.assertEventsAreEqual(expectedEvent, actualEvent);
    }

    @Test
    void getAllEvents() {
        List<Event> events = eventMapper.getAllEvents();
        assertEquals(11, events.size(), "Number of events did not match!");
    }

    @Test
    void createNewEvent() {
        Person expectedGuest = personMapper.getPersonById(10L).get();
        Person expectedHost = personMapper.getPersonById(50L).get();

        Event expectedEvent = EventUtils.createMockEvent(expectedHost);
        eventMapper.saveEvent(expectedEvent);

        Invitation expectedInvitation = InvitationUtils.createMockInvitation(expectedEvent, expectedGuest);
        invitationMapper.saveInvitation(expectedInvitation);

        expectedEvent.setInvitations(List.of(expectedInvitation));

        Optional<Event> actualEvent = eventMapper.getEventById(expectedEvent.getId());

        EventUtils.assertEventsAreEqual(expectedEvent, actualEvent);
        PersonUtils.assertPersonsAreEqual(expectedEvent.getHost(), actualEvent.get().getHost(), "Host");
        PersonUtils.assertPersonsAreEqual(expectedGuest, actualEvent.get().getInvitations().get(0).getGuest(), "Guest");
        InvitationUtils.assertInvitationsAreEqual(expectedInvitation, actualEvent.get().getInvitations().get(0));
    }
}
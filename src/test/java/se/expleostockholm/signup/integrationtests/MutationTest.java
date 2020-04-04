package se.expleostockholm.signup.integrationtests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.expleostockholm.signup.utils.EventUtils.assertEventsAreEqual;
import static se.expleostockholm.signup.utils.EventUtils.createMockEvent;

import java.util.Optional;
import javax.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.expleostockholm.signup.domain.Attendance;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.domain.Person;
import se.expleostockholm.signup.domain.web.Response;
import se.expleostockholm.signup.exception.EventException;
import se.expleostockholm.signup.repository.EventMapper;
import se.expleostockholm.signup.repository.InvitationMapper;
import se.expleostockholm.signup.repository.PersonMapper;
import se.expleostockholm.signup.resolver.Mutation;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {SignupDbTests.Initializer.class})
public class MutationTest extends SignupDbTests {

    @Resource
    private Mutation mutation;

    @Resource
    private InvitationMapper invitationMapper;

    @Resource
    private EventMapper eventMapper;

    @Resource
    private PersonMapper personMapper;

    private final Long invitationId = 1L;
    private final Long hostId = 3L;

    private Event expectedEvent;
    private Person expectedHost;

    public void tearDown() {
        expectedEvent.getInvitations().forEach(e -> invitationMapper.removeInvitationByEventId(e.getEvent_id()));
        expectedEvent.getInvitations().forEach(e -> personMapper.removePersonByEmail(e.getGuest().getEmail()));
        eventMapper.removeEventById(expectedEvent.getId());
    }

    @Test
    @Order(1)
    public void attendance_updated_success() {
        Response response = mutation.setAttendance(Attendance.ATTENDING, invitationId);
        Optional<Invitation> invitation = invitationMapper.getInvitationById(invitationId);
        Assertions.assertAll(
                () -> assertEquals("Attendance was successfully updated!", response.getMessage(), "Response message did not match!"),
                () -> assertTrue(invitation.isPresent(), "Invitation not found!"),
                () -> assertEquals(Attendance.ATTENDING, invitation.get().getAttendance(), "Attendance did not match!")
        );
    }

    @Test
    @Order(2)
    public void createEvent_success() {
        expectedHost = personMapper.getPersonById(hostId).get();
        expectedEvent = createMockEvent(expectedHost);

        Event event = mutation.createEvent(expectedEvent);
        expectedEvent.setId(event.getId());
        assertEventsAreEqual(expectedEvent, eventMapper.getEventById(event.getId()));

        tearDown();
    }

    @Test
    @Order(3)
    public void createEvent_duplicate_fail() {
        assertThrows(EventException.class, () ->
                mutation.createEvent(eventMapper.getEventById(1L).get())
        );
    }
}

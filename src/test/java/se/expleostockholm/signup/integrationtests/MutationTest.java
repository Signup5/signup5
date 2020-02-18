package se.expleostockholm.signup.integrationtests;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.expleostockholm.signup.domain.*;
import se.expleostockholm.signup.repository.EventMapper;
import se.expleostockholm.signup.repository.InvitationMapper;
import se.expleostockholm.signup.repository.PersonMapper;
import se.expleostockholm.signup.resolver.Mutation;
import se.expleostockholm.signup.utils.EventUtils;
import se.expleostockholm.signup.utils.PersonUtils;

import javax.annotation.Resource;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.expleostockholm.signup.utils.EventUtils.assertEventsAreEqual;
import static se.expleostockholm.signup.utils.EventUtils.createMockEvent;
import static se.expleostockholm.signup.utils.PersonUtils.createMockPerson;


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

    private Long eventId;
    private Long invitationId = 1L;
    private Long hostId = 3L;

    private Event expectedEvent;
    private Person expectedHost;

    @AfterTestClass
    public void tearDown() {
        personMapper.removePersonByEmail(expectedHost.getEmail());
        eventMapper.removeEventById(eventId);
    }

    @Test
    public void setAttendance() {
        Response response = mutation.setAttendance(Attendance.ATTENDING, invitationId);
        Optional<Invitation> invitation = invitationMapper.getInvitationById(invitationId);
        assertAll(
                () -> assertEquals("Attendance was updated!", response.getMessage(), "Response message did not match!"),
                () -> assertEquals(Attendance.ATTENDING, invitation.get().getAttendance(), "Attendance did not match!")
        );
    }

    @Test
    public void createEvent() {
        expectedHost = personMapper.getPersonById(hostId).get();
        expectedEvent = createMockEvent(expectedHost);

        Response response = mutation.createEvent(expectedEvent);
        eventId = response.getId();
        expectedEvent.setId(eventId);
        assertEventsAreEqual(expectedEvent, eventMapper.getEventById(response.getId()));
    }

}

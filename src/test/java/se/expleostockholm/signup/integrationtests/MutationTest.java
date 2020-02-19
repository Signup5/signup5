package se.expleostockholm.signup.integrationtests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.expleostockholm.signup.domain.*;
import se.expleostockholm.signup.repository.EventMapper;
import se.expleostockholm.signup.repository.InvitationMapper;
import se.expleostockholm.signup.repository.PersonMapper;
import se.expleostockholm.signup.resolver.Mutation;

import javax.annotation.Resource;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.expleostockholm.signup.utils.EventUtils.assertEventsAreEqual;
import static se.expleostockholm.signup.utils.EventUtils.createMockEvent;

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

    private Long invitationId = 1L;
    private Long hostId = 3L;

    private Event expectedEvent;
    private Person expectedHost;

    public void tearDown() {
        eventMapper.removeEventById(expectedEvent.getId());
    }

    @Test
    @Order(1)
    public void setAttendance() {
        Response response = mutation.setAttendance(Attendance.ATTENDING, invitationId);
        Optional<Invitation> invitation = invitationMapper.getInvitationById(invitationId);
        assertAll(
                () -> assertEquals("Attendance was updated!", response.getMessage(), "Response message did not match!"),
                () -> assertEquals(Attendance.ATTENDING, invitation.get().getAttendance(), "Attendance did not match!")
        );
    }

    @Test
    @Order(2)
    public void createEvent() {
        expectedHost = personMapper.getPersonById(hostId).get();
        expectedEvent = createMockEvent(expectedHost);

        Response response = mutation.createEvent(expectedEvent);
        expectedEvent.setId(response.getId());
        assertEventsAreEqual(expectedEvent, eventMapper.getEventById(response.getId()));

        tearDown();
    }

}

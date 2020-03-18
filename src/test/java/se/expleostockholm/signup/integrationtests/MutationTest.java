package se.expleostockholm.signup.integrationtests;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.expleostockholm.signup.domain.*;
import se.expleostockholm.signup.exception.EventAlreadyExistException;
import se.expleostockholm.signup.repository.EventMapper;
import se.expleostockholm.signup.repository.InvitationMapper;
import se.expleostockholm.signup.repository.PersonMapper;
import se.expleostockholm.signup.resolver.Mutation;
import se.expleostockholm.signup.service.EmailService;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
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

    private final Long invitationId = 1L;
    private final Long hostId = 3L;

    private Event expectedEvent;
    private Person expectedHost;

    public void tearDown() {
        eventMapper.removeEventById(expectedEvent.getId());
    }

    @Test
    @Order(1)
    public void attendance_updated_success() {
        Response response = mutation.setAttendance(Attendance.ATTENDING, invitationId);
        Optional<Invitation> invitation = invitationMapper.getInvitationById(invitationId);
        assertAll(
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

        Response response = mutation.createEvent(expectedEvent);
        expectedEvent.setId(response.getId());
        assertEventsAreEqual(expectedEvent, eventMapper.getEventById(response.getId()));

        tearDown();
    }

    @Test
    @Order(3)
    public void createEvent_duplicate_fail() {
        assertThrows(EventAlreadyExistException.class, () ->
                mutation.createEvent(eventMapper.getEventById(1L).get())
        );
    }
}

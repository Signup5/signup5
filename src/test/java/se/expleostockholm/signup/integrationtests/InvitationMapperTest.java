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
import se.expleostockholm.signup.utils.InvitationUtils;
import se.expleostockholm.signup.utils.PersonUtils;

import javax.annotation.Resource;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static se.expleostockholm.signup.utils.InvitationUtils.*;

@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = {SignupDbTests.Initializer.class})
class InvitationMapperTest extends SignupDbTests {

    @Resource
    private InvitationMapper invitationMapper;

    @Resource
    private PersonMapper personMapper;

    @Resource
    private EventMapper eventMapper;

    @Test
    void invitation_exists_success() {
        Optional<Invitation> invitation = invitationMapper.getInvitationById(2L);

        assertAll(
                () -> assertTrue(invitation.isPresent(), "No invitation found!"),
                () -> assertEquals(2L, invitation.get().getEvent_id(), "Event id did not match!"),
                () -> assertEquals(30L, invitation.get().getGuest().getId(), "Guest id did not match!"),
                () -> assertEquals(Attendance.NOT_ATTENDING, invitation.get().getAttendance(), "Attendance did not match!")
        );
    }

    @Test
    void attendance_updated_success() {
        invitationMapper.setAttendance(Attendance.MAYBE, 1L);
        Optional<Invitation> invitation = invitationMapper.getInvitationById(1L);

        assertAll(
                () -> assertTrue(invitation.isPresent(), "No invitation found!"),
                () -> assertEquals(Attendance.MAYBE, invitation.get().getAttendance(), "Attendance did not match!")
        );

    }

    @Test
    void invitation_saved_success() {

        Person guest = personMapper.getPersonById(50L).get();
        Event event = eventMapper.getEventById(10L).get();
        Invitation expected_invitation = createMockInvitation(event, guest);

        invitationMapper.saveInvitation(expected_invitation);
        Invitation actual_invitation = invitationMapper.getInvitationById(expected_invitation.getId()).get();

        assertInvitationsAreEqual(expected_invitation, actual_invitation);
    }
}

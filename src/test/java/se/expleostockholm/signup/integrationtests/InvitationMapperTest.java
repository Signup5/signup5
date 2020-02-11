package se.expleostockholm.signup.integrationtests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.expleostockholm.signup.domain.Attendance;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.repository.InvitationMapper;

import javax.annotation.Resource;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = {SignupDbTests.Initializer.class})
class InvitationMapperTest extends SignupDbTests {

    @Resource
    private InvitationMapper invitationMapper;

    @Test
    void verify_getInvitation() {
        Optional<Invitation> invitation = invitationMapper.getInvitationById(1L);

        assertAll(
                () -> assertTrue(invitation.isPresent(), "No invitation found!"),
                () -> assertEquals(6L, invitation.get().getEvent().getId(), "Event id did not match!"),
                () -> assertEquals(40L, invitation.get().getGuest().getId(), "Guest id did not match!"),
                () -> assertEquals(Attendance.NO_RESPONSE, invitation.get().getAttendance(), "Attendance did not match!")
        );
    }

    @Test
    void verify_setAttendance() {
        invitationMapper.setAttendance(Attendance.MAYBE, 1L);
        Optional<Invitation> invitation = invitationMapper.getInvitationById(1L);

        assertAll(
                () -> assertTrue(invitation.isPresent(), "No invitation found!"),
                () -> assertEquals(Attendance.MAYBE, invitation.get().getAttendance(), "Attendance did not match!")
        );

    }
}

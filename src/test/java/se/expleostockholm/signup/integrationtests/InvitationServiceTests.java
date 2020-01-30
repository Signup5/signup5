package se.expleostockholm.signup.integrationtests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.expleostockholm.signup.domain.Attendance;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.exception.InvitationNotFoundException;
import se.expleostockholm.signup.service.InvitationService;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = {SignupDbTests.Initializer.class})
public class InvitationServiceTests {

    @Resource
    private InvitationService invitationService;

    @Test
    void verify_getInvitation_success() {
        Invitation invitation = invitationService.getInvitationById(1L);
        assertEquals(1l, invitation.getId());
    }

    @Test
    void verify_getInvitation_fail() {
        assertThrows(InvitationNotFoundException.class, () -> invitationService.getInvitationById(-100000L));
    }

    @Test
    void verify_setAttendance_success() {
        invitationService.setAttendance(Attendance.MAYBE, 1L);
        assertEquals(Attendance.MAYBE, invitationService.getInvitationById(1L).getAttendance());
    }
}

package se.expleostockholm.signup.integrationtests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.expleostockholm.signup.domain.Attendance;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.repository.InvitationMapper;
import se.expleostockholm.signup.resolver.Mutation;

import javax.annotation.Resource;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {SignupDbTests.Initializer.class})
class MutationTest extends SignupDbTests {

    @Resource
    private Mutation mutation;

    @Resource
    private InvitationMapper invitationMapper;

    @Test
    public void setAttendance() {
        String responseMessage = mutation.setAttendance(Attendance.ATTENDING, 1L);

        Optional<Invitation> invitation = invitationMapper.getInvitationById(1L);

        assertAll(
                () -> assertEquals("Attendance was updated!", responseMessage, "Response message did not match!"),
                () -> assertEquals(Attendance.ATTENDING, invitation.get().getAttendance(), "Attendance did not match!")
        );

    }

}

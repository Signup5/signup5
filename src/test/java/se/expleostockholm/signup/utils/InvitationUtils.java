package se.expleostockholm.signup.utils;

import se.expleostockholm.signup.domain.Attendance;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.domain.Person;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvitationUtils {

    public static Invitation createMockInvitation(Event expectedEvent, Person guest) {
        return Invitation.builder()
                .event_id(expectedEvent.getId())
                .guest(guest)
                .attendance(Attendance.NO_RESPONSE)
                .build();
    }

    public static void assertInvitationsAreEqual(Invitation expectedInvitation, Invitation actualInvitation) {
        assertAll(
                () -> assertEquals(expectedInvitation.getId(), actualInvitation.getId(), "Invitation id did not match!"),
                () -> assertEquals(expectedInvitation.getAttendance(), actualInvitation.getAttendance(), "Attendance did not match!")
        );
    }
}

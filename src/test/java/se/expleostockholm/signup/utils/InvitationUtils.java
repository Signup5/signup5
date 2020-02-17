package se.expleostockholm.signup.utils;

import se.expleostockholm.signup.domain.Attendance;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.domain.Person;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static se.expleostockholm.signup.utils.PersonUtils.assertPersonsAreEqual;

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
                () -> assertEquals(expectedInvitation.getEvent_id(), actualInvitation.getEvent_id(), "Event id did not match!"),
                () -> assertEquals(expectedInvitation.getAttendance(), actualInvitation.getAttendance(), "Attendance did not match!"),
                () -> assertPersonsAreEqual(expectedInvitation.getGuest(), actualInvitation.getGuest(), "Guest")
        );

    }

    public static void assertInvitationListsAreEqual(List<Invitation> expectedInvitationList, List<Invitation> actualInvitationList){
        if (expectedInvitationList.size() > 1 && actualInvitationList.size() > 1) {
            Collections.sort(expectedInvitationList, (i1, i2) -> (int) (i1.getId() - i2.getId()));
            Collections.sort(actualInvitationList, (i1, i2) -> (int) (i1.getId() - i2.getId()));
        }

        assertAll(
                () -> assertEquals(expectedInvitationList.size(), actualInvitationList.size(), "Number of invitations did not match!"),
                () -> assertTrue(expectedInvitationList.equals(actualInvitationList), "Invitation lists did not match!")
        );
    }
}

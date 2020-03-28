package se.expleostockholm.signup.utils;

import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.domain.Person;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.expleostockholm.signup.utils.PersonUtils.assertPersonsAreEqual;

public class InvitationUtils {

    public static Invitation createMockInvitation(Event expectedEvent, Person guest) {
        return Invitation.builder()
                .event_id(expectedEvent.getId())
                .guest(guest)
                .build();
    }

    public static Invitation createMockInvitation(Person guest) {
        return Invitation.builder()
                .guest(guest)
                .build();
    }

    public static void assertInvitationsAreEqual(Invitation expectedInvitation, Invitation actualInvitation) {
        assertAll(
                () -> assertEquals(expectedInvitation.getAttendance(), actualInvitation.getAttendance(), "Attendance did not match!"),
                () -> assertPersonsAreEqual(expectedInvitation.getGuest(), actualInvitation.getGuest(), "Guest")
        );
    }

    public static void assertInvitationListsAreEqual(List<Invitation> expectedInvitationList, List<Invitation> actualInvitationList) {
        assertEquals(expectedInvitationList.size(), actualInvitationList.size(), "Number of invitations did not match!");

        if (expectedInvitationList.size() > 1 && actualInvitationList.size() > 1) {
            expectedInvitationList.sort(COMPARE_BY_GUEST_EMAIL);
            actualInvitationList.sort(COMPARE_BY_GUEST_EMAIL);
        }

        for (int i = 0; i < expectedInvitationList.size(); i++) {
            assertInvitationsAreEqual(expectedInvitationList.get(i), actualInvitationList.get(i));
        }
    }

    public static final Comparator<Invitation> COMPARE_BY_GUEST_EMAIL = Comparator.comparing(invitation -> invitation.getGuest().getEmail());
}

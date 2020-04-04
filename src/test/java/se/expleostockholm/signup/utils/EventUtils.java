package se.expleostockholm.signup.utils;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.expleostockholm.signup.utils.InvitationUtils.assertInvitationListsAreEqual;
import static se.expleostockholm.signup.utils.InvitationUtils.createMockInvitation;
import static se.expleostockholm.signup.utils.PersonUtils.assertPersonsAreEqual;
import static se.expleostockholm.signup.utils.PersonUtils.createMockPerson;

import com.github.javafaker.Faker;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.stream.IntStream;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.domain.Person;

public class EventUtils {

    public static Event createMockEvent(Person host) {
        Faker faker = new Faker();

        return Event.builder()
                .host(host)
                .date_of_event(LocalDate.now())
                .time_of_event(LocalTime.NOON)
                .duration((short) 60)
                .description(faker.book().genre())
                .location(faker.rickAndMorty().location())
                .title(faker.rickAndMorty().character())
                .invitations(IntStream.range(0, 1)
                        .mapToObj(i -> createMockInvitation(createMockPerson())).collect(toList()))
                .build();
    }

    public static void assertEventsAreEqual(Event expectedEvent, Optional<Event> actualEvent) {
        assertAll(
                () -> assertTrue(actualEvent.isPresent(), "No event found!"),
                () -> assertEquals(expectedEvent.getDate_of_event(), actualEvent.get().getDate_of_event(), "Event date did not match!"),
                () -> assertEquals(expectedEvent.getTime_of_event(), actualEvent.get().getTime_of_event(), "Event time did not match!"),
                () -> assertEquals(expectedEvent.getDescription(), actualEvent.get().getDescription(), "Event description did not match!"),
                () -> assertEquals(expectedEvent.getLocation(), actualEvent.get().getLocation(), "Event location did not match!"),
                () -> assertEquals(expectedEvent.getTitle(), actualEvent.get().getTitle(), "Event title did not match!"),
                () -> assertInvitationListsAreEqual(expectedEvent.getInvitations(), actualEvent.get().getInvitations()),
                () -> assertPersonsAreEqual(expectedEvent.getHost(), actualEvent.get().getHost(), "Host")
        );
    }
}

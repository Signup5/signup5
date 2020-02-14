package se.expleostockholm.signup.utils;

import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.domain.Person;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class EventUtils {

    public static Event createMockEvent(Person host) {
        return Event.builder()
                .host(host)
                .date_of_event(LocalDate.now())
                .time_of_event(LocalTime.now())
                .description("This is a test event.")
                .location("Vasagatan 1")
                .title("My event")
                .build();
    }

    public static void assertEventsAreEqual(Event expectedEvent, Optional<Event> actualEvent) {
        assertAll(
                () -> assertTrue(actualEvent.isPresent(), "No event found!"),
                () -> assertEquals(expectedEvent.getId(), actualEvent.get().getId(), "Event id did not match!"),
                () -> assertEquals(expectedEvent.getDate_of_event(), actualEvent.get().getDate_of_event(), "Event date did not match!!"),
                () -> assertEquals(expectedEvent.getTime_of_event(), actualEvent.get().getTime_of_event(), "Event time did not match!!"),
                () -> assertEquals(expectedEvent.getDescription(), actualEvent.get().getDescription(), "Event description did not match!"),
                () -> assertEquals(expectedEvent.getLocation(), actualEvent.get().getLocation(), "Event location did not match!"),
                () -> assertEquals(expectedEvent.getTitle(), actualEvent.get().getTitle(), "Event title did not match!")
        );
    }
}

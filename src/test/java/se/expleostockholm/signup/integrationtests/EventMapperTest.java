package se.expleostockholm.signup.integrationtests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.repository.EventMapper;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = {SignupDbTests.Initializer.class})
class EventMapperTest extends SignupDbTests {

    @Resource
    EventMapper eventMapper;

    @Test
    void getEventById() {
        Optional<Event> event = eventMapper.getEventById(1L);

        assertAll(
                () -> assertTrue(event.isPresent(), "No event found!"),
                () -> assertEquals("That Championship Season", event.get().getTitle(), "Event title did not match!"),
                () -> assertEquals("Enhanced discrete moderator", event.get().getDescription(), "Event description did not match!"),
                () -> assertEquals("2020-09-04", event.get().getDate_of_event().toString(), "Date of event did not match!"),
                () -> assertEquals("9982 Coleman Terrace", event.get().getLocation(), "Event location did not match!")
        );

    }

    @Test
    void getAllEvents() {
        List<Event> events = eventMapper.getAllEvents();
        assertEquals(10, events.size(),  "Number of events did not match!");
    }
}
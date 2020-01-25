package se.expleostockholm.signup.integrationtests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.repository.EventMapper;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = {SignupDbTests.Initializer.class})
class EventMapperTests extends SignupDbTests {

  @Resource
  private EventMapper eventMapper;

  @Test
  void verifyGetAllEvents() {
    List<Event> events = eventMapper.getAllEvents();
    assertEquals(1, events.size());
    Event event = events.get(0);
    assertEquals("Marcus Event", event.getTitle());
    assertEquals("2020-01-25", event.getDate_of_event().toString());
  }

}
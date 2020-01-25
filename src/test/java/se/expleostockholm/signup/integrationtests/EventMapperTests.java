package se.expleostockholm.signup.integrationtests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.repository.EventMapper;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = {EventMapperTests.Initializer.class})
class EventMapperTests {

  // will be started before and stopped after each test method
  @Container
  private static PostgreSQLContainer postgresqlTestContainer
    = new PostgreSQLContainer("postgres:12")
          .withDatabaseName("signup")
          .withUsername("postgres")
          .withPassword("password");

  @Resource
  private EventMapper eventMapper;

  @Test
  void verifyThatTestDbIsRunning() {
    assertTrue(postgresqlTestContainer.isRunning());
  }

  @Test
  void verifyGetAllEvents() {
    List<Event> events = eventMapper.getAllEvents();
    assertEquals(1, events.size());
    Event event = events.get(0);
    assertEquals("Marcus Event", event.getTitle());
    assertEquals("2020-01-25", event.getDate_of_event().toString());
  }

  static class Initializer
    implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
      TestPropertyValues.of(
        "spring.datasource.url=" + postgresqlTestContainer.getJdbcUrl(),
        "spring.datasource.username=" + postgresqlTestContainer.getUsername(),
        "spring.datasource.password=" + postgresqlTestContainer.getPassword()
      ).applyTo(configurableApplicationContext.getEnvironment());
    }
  }

}

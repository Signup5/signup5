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
import se.expleostockholm.signup.domain.Attendance;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.repository.InvitationMapper;

import javax.annotation.Resource;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = {InvitationMapperTests.Initializer.class})
class InvitationMapperTests {

  // will be started before and stopped after each test method
  @Container
  private static PostgreSQLContainer postgresqlTestContainer
    = new PostgreSQLContainer("postgres:12")
          .withDatabaseName("signup")
          .withUsername("postgres")
          .withPassword("password");


  @Resource
  private InvitationMapper invitationMapper;

  @Test
  void verifyGetInvitation(){
    Optional<Invitation> invitationOption = invitationMapper.getInvitationById(1L);
    assertTrue(invitationOption.isPresent());
    assertEquals(1L, invitationOption.get().getEvent_id());
  }

  @Test
  void verifySetAttendance(){
    invitationMapper.setAttendance(Attendance.MAYBE, 1L);
    Optional<Invitation> invitationOption = invitationMapper.getInvitationById(1L);
    assertTrue(invitationOption.isPresent());
    assertEquals(Attendance.MAYBE, invitationOption.get().getAttendance());
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

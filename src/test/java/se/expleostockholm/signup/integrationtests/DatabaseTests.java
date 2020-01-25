package se.expleostockholm.signup.integrationtests;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

public class DatabaseTests {
  // will be started before and stopped after each test
  @Container
  protected static PostgreSQLContainer postgresqlTestContainer
    = new PostgreSQLContainer("postgres:12")
          .withDatabaseName("signup")
          .withUsername("postgres")
          .withPassword("password");

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

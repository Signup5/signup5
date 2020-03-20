package se.expleostockholm.signup.integrationtests;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.junit.jupiter.Container;

import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class SignupDbTests {

    @Container
    protected static final SignupDbTestcontainer dbTestContainer = SignupDbTestcontainer.getInstance();

    @Test
    @Order(0)
    void verifyThatTestDbIsRunning() {
        assertTrue(dbTestContainer.isRunning());
    }

    public static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + dbTestContainer.getJdbcUrl(),
                    "spring.datasource.username=" + dbTestContainer.getUsername(),
                    "spring.datasource.password=" + dbTestContainer.getPassword(),
                    "spring.mail.host=" + System.getenv("MAIL_SMTP_HOST"),
                    "spring.mail.port=" + System.getenv("MAIL_SMTP_PORT"),
                    "spring.mail.username=" + System.getenv("MAIL_USERNAME"),
                    "spring.mail.password=" + System.getenv("MAIL_PASSWORD"),
                    "spring.mail.properties.mail.smtp.auth=" + System.getenv("MAIL_SMTP_AUTH")
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}

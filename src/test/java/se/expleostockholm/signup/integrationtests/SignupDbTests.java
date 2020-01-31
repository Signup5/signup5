package se.expleostockholm.signup.integrationtests;

import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.testcontainers.junit.jupiter.Container;

import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class SignupDbTests {

    @Container
    protected static SignupDbTestcontainer dbTestContainer = SignupDbTestcontainer.getInstance();


    @Test
    void verifyThatTestDbIsRunning() {
        assertTrue(dbTestContainer.isRunning());
    }

    public static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + dbTestContainer.getJdbcUrl(),
                    "spring.datasource.username=" + dbTestContainer.getUsername(),
                    "spring.datasource.password=" + dbTestContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}

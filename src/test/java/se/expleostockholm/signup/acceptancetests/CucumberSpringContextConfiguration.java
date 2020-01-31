package se.expleostockholm.signup.acceptancetests;

import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.expleostockholm.signup.SignupApplication;
import se.expleostockholm.signup.integrationtests.SignupDbTests;

import static org.springframework.boot.test.context.SpringBootTest.*;

@Testcontainers
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = SignupApplication.class, loader = SpringBootContextLoader.class)
public class CucumberSpringContextConfiguration extends SignupDbTests{

    @Bean
    public GraphQLTestTemplate graphQLTestTemplate() {
        return new GraphQLTestTemplate();
    };

}

package se.expleostockholm.signup.integrationtests;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.expleostockholm.signup.constant.JwtFilterConstant;
import se.expleostockholm.signup.util.JwtUtil;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {SignupDbTests.Initializer.class})
public class QueryTests extends SignupDbTests {

    @Resource
    private GraphQLTestTemplate graphQLTestTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        final String jwtToken = jwtUtil.generateToken(new User("bla", "", new ArrayList<>()));
        graphQLTestTemplate.addHeader(JwtFilterConstant.HEADER_STRING, JwtFilterConstant.TOKEN_PREFIX + jwtToken);
    }


    @Test
    public void getAllEvents() throws IOException {
        GraphQLResponse response = graphQLTestTemplate
                .perform("queries/getAllEvents.graphql", null);

        assertAll(
                () -> assertTrue(response.isOk(), "Response not OK!"),
                () -> assertEquals("1", response.get("$.data.getAllEvents[0].id"), "Event id did not match!"),
                () -> assertEquals("That Championship Season", response.get("$.data.getAllEvents[0].title"), "Event title did not match!"),
                () -> assertEquals("Enhanced discrete moderator", response.get("$.data.getAllEvents[0].description"), "Event description did not match!"),
                () -> assertEquals("2020-09-04", response.get("$.data.getAllEvents[0].date_of_event"), "Date of event did not match!"),
                () -> assertEquals(LocalTime.parse("12:00:00"), LocalTime.parse(response.get("$.data.getAllEvents[0].time_of_event")), "Time of event did not match"),
                () -> assertEquals("9982 Coleman Terrace", response.get("$.data.getAllEvents[0].location"), "Event location did not match!"),
                () -> assertEquals("18", response.get("$.data.getAllEvents[0].host.id"), "Host id did not match!")
        );
    }

    @Test
    public void getAllPersons() throws IOException {
        GraphQLResponse response = graphQLTestTemplate.perform("queries/getAllPersons.graphql", null);

        assertAll(
                () -> assertTrue(response.isOk(), "Response not OK!"),
                () -> assertEquals("1", response.get("$.data.getAllPersons[0].id"), "Person id did not match!"),
                () -> assertEquals("amatys0@wp.com", response.get("$.data.getAllPersons[0].email"), "Email did not match!"),
                () -> assertEquals("Ali", response.get("$.data.getAllPersons[0].first_name"), "First name did not match!"),
                () -> assertEquals("Matys", response.get("$.data.getAllPersons[0].last_name"), "Last name did not match!")
        );
    }

}

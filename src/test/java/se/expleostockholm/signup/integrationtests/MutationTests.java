package se.expleostockholm.signup.integrationtests;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.annotation.Resource;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {SignupDbTests.Initializer.class})
class MutationTests extends SignupDbTests {

    @Resource
    private GraphQLTestTemplate graphQLTestTemplate;

    @Test
    public void setAttendanceTest() throws IOException {


        GraphQLResponse response = graphQLTestTemplate.perform("queries/allEvents.graphql", null);

        assertTrue(response.isOk());
        assertEquals("1", response.get("$.data.allEvents[0].id"));
        assertEquals("Marcus Event", response.get("$.data.allEvents[0].title"));
        assertEquals("Party party.", response.get("$.data.allEvents[0].description"));
        assertEquals("2021-03-31", response.get("$.data.allEvents[0].date_of_event"));
    }


}

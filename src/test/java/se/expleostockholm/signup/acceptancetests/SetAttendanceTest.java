package se.expleostockholm.signup.acceptancetests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.expleostockholm.signup.domain.Attendance;
import se.expleostockholm.signup.integrationtests.SignupDbTests;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {SignupDbTests.Initializer.class})
public class SetAttendanceTest extends SignupDbTests {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    private GraphQLResponse response;
    private ObjectNode variables;

    @ParameterizedTest
    @EnumSource(value = Attendance.class, names = {"ATTENDING", "MAYBE", "NO_RESPONSE", "NOT_ATTENDING"})
    public void verify_setAttendance_success(Attendance attendance) throws IOException {
        variables = new ObjectMapper().createObjectNode();
        variables.put("attendance", attendance.toString());
        variables.put("invitation_id", 1);

        response = graphQLTestTemplate.perform("queries/setAttendance.graphql", variables);
        assertEquals("Attendance was updated!", response.get("$.data.message"));

        variables = new ObjectMapper().createObjectNode();
        variables.put("invitation_id", 1);

        response = graphQLTestTemplate.perform("queries/getInvitationById.graphql", variables);
        assertEquals(attendance.toString(), response.get("$.data.invitation.attendance"));
    }
}

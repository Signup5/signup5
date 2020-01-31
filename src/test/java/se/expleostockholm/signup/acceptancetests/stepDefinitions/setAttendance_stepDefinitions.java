package se.expleostockholm.signup.acceptancetests.stepDefinitions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import io.cucumber.java8.En;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.expleostockholm.signup.domain.Attendance;
import se.expleostockholm.signup.integrationtests.SignupDbTests;

import static org.junit.Assert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class setAttendance_stepDefinitions implements En {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    private GraphQLResponse response;
    private ObjectNode variables;

    public setAttendance_stepDefinitions() {
        Given("^Person is invited to an Event$", () -> {});

        When("^Person responds with \"([^\"]*)\"$", (String attendance) -> {
            ObjectNode variables = new ObjectMapper().createObjectNode();
            variables.put("attendance", attendance);
            variables.put("invitation_id", 1);

            response = graphQLTestTemplate.perform("queries/setAttendance.graphql", variables);
        });

        Then("^Person gets a confirmation message about new status$", () -> {
            assertEquals("Attendance was updated!", response.get("$.data.message"));
        });

        And("^Invitation status is updated to \"([^\"]*)\"$", (String attendance) -> {
            variables = new ObjectMapper().createObjectNode();
            variables.put("invitation_id", 1);

            response = graphQLTestTemplate.perform("queries/getInvitationById.graphql", variables);

            assertEquals(attendance, response.get("$.data.invitation.attendance"));
        });
    }
}
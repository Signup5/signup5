package se.expleostockholm.signup.acceptancetests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.expleostockholm.signup.domain.Attendance;
import se.expleostockholm.signup.integrationtests.SignupDbTests;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {SignupDbTests.Initializer.class})
public class SetAttendanceTest extends SignupDbTests {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    private static Stream<Arguments> providedData() {
        return Stream.of(
                Arguments.of(1L, Attendance.ATTENDING),
                Arguments.of(1L, Attendance.NOT_ATTENDING),
                Arguments.of(1L, Attendance.MAYBE),
                Arguments.of(1L, Attendance.NO_RESPONSE)
        );
    }

    @ParameterizedTest
    @MethodSource("providedData")
    public void verify_setAttendance_success(Long invitationId, Attendance attendance) throws IOException {
        ObjectNode queryVariables = variables(invitationId, attendance);

        String responseMessage = setAttendance(queryVariables);
        Attendance newAttendance = getAttendance(queryVariables);

        String expectedMessage = "Attendance was updated!";
        Attendance expectedAttendance = attendance;

        assertAll(
                () -> assertEquals(expectedMessage, responseMessage),
                () -> assertEquals(expectedAttendance, newAttendance)
        );
    }


    public String setAttendance(ObjectNode variables) throws IOException {
        return graphQLTestTemplate
                .perform("queries/setAttendance.graphql", variables)
                .get("$.data.message");
    }

    public Attendance getAttendance(ObjectNode variables) throws IOException {
        return Attendance.valueOf(graphQLTestTemplate
                .perform("queries/getInvitationById.graphql", variables)
                .get("$.data.invitation.attendance"));
    }

    public ObjectNode variables(Long invitationId, Attendance attendance) {
        ObjectNode queryVariables = new ObjectMapper().createObjectNode();
        queryVariables.put("attendance", attendance.toString());
        queryVariables.put("invitation_id", invitationId);

        return queryVariables;
    }


}

package se.expleostockholm.signup.acceptancetests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.expleostockholm.signup.domain.Attendance;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.domain.Person;
import se.expleostockholm.signup.integrationtests.SignupDbTests;
import se.expleostockholm.signup.repository.PersonMapper;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.expleostockholm.signup.utils.EventUtils.createMockEvent;
import static se.expleostockholm.signup.utils.PersonUtils.mockPerson;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {SignupDbTests.Initializer.class})
public class CreateEventTest extends SignupDbTests {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @Resource
    private PersonMapper personMapper;

    private ObjectNode variables;
    private ObjectNode eventVariables;

    private Event event;

    @Test
    public void createEvent_test() throws IOException {
        Person host = given_host_exists();

        event = createMockEvent(host);

        variables = new ObjectMapper().createObjectNode();
        eventVariables = new ObjectMapper().createObjectNode();

        String message = when_host_creates_new_event_and_invites_guests();
        System.out.println(message);
        then_host_can_see_invitation_statistics(1L);
    }

    public Person given_host_exists() {
        return personMapper.getPersonById(12L).get();
    }

    public String when_host_creates_new_event_and_invites_guests() throws IOException {
        Invitation invitation1 = Invitation.builder()
                .guest(mockPerson())
                .attendance(Attendance.ATTENDING)

                .build();

        Invitation invitation2 = Invitation.builder()
                .guest(personMapper.getPersonById(11L).get())
                .attendance(Attendance.NO_RESPONSE)
                .build();

        Invitation invitation3 = Invitation.builder()
                .guest(mockPerson())
                .attendance(Attendance.NO_RESPONSE)
                .build();

        event.setInvitations(List.of(invitation1, invitation2, invitation3));



        eventVariables.putPOJO("host", event.getHost());
        eventVariables.putPOJO("title", event.getTitle());
        eventVariables.putPOJO("date_of_event", event.getDate_of_event().toString());
        eventVariables.putPOJO("time_of_event", event.getTime_of_event().toString());
        eventVariables.putPOJO("invitations", event.getInvitations());
        variables.putPOJO("eventInput", eventVariables);

        System.out.println(variables.toPrettyString());

        return graphQLTestTemplate.perform("queries/createEvent.graphql", variables).getRawResponse().getBody();

    }

    public void then_host_can_see_invitation_statistics(Long event_id) {
//        invitationMapper.getInvitationByEventId(event_id);
        assertAll(
                () -> assertTrue(true)
        );
    }
}

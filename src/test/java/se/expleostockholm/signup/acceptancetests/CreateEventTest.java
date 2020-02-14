package se.expleostockholm.signup.acceptancetests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.expleostockholm.signup.domain.Attendance;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.domain.Person;
import se.expleostockholm.signup.integrationtests.SignupDbTests;
import se.expleostockholm.signup.repository.EventMapper;
import se.expleostockholm.signup.repository.InvitationMapper;
import se.expleostockholm.signup.repository.PersonMapper;
import se.expleostockholm.signup.utils.InvitationUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {SignupDbTests.Initializer.class})
public class CreateEventTest extends SignupDbTests {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;
    @Autowired
    PersonMapper personMapper;
    @Autowired
    InvitationMapper invitationMapper;
    @Autowired
    EventMapper eventMapper;

    private ObjectNode queryVariables;


    @Test
    public void createEventTest() {
        Person host = given_host_exists();

        queryVariables = new ObjectMapper().createObjectNode();

        queryVariables.put("host_id", host.getId());
        queryVariables.put("title", "My funny super event!");
        queryVariables.put("description", "Halloween costume party");
        queryVariables.putPOJO("date_of_event", LocalDate.of(2020, 10, 31));
        queryVariables.putPOJO("time_of_event", LocalTime.of(23, 30));
        queryVariables.put("location", "Addressway two");
        queryVariables.putPOJO("invitations", List.of(Invitation.builder().build()));

        Long event_id = when_host_creates_new_event_and_invites_guests();
        then_host_can_see_invitation_statistics(event_id);
    }

    Person given_host_exists() {
        return personMapper.getAllPersons().get(0);
    }

    Long when_host_creates_new_event_and_invites_guests() {
        return 1L;
    }

    void then_host_can_see_invitation_statistics(Long event_id) {
        invitationMapper.getInvitationByEventId(event_id);
        assertAll(

        );
    }
}

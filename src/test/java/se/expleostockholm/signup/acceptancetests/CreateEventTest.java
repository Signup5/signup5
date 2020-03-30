package se.expleostockholm.signup.acceptancetests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.expleostockholm.signup.constant.JwtFilterConstant;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.domain.Person;
import se.expleostockholm.signup.integrationtests.SignupDbTests;
import se.expleostockholm.signup.repository.EventMapper;
import se.expleostockholm.signup.repository.InvitationMapper;
import se.expleostockholm.signup.repository.PersonMapper;
import se.expleostockholm.signup.util.JwtUtil;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.expleostockholm.signup.utils.EventUtils.assertEventsAreEqual;
import static se.expleostockholm.signup.utils.EventUtils.createMockEvent;
import static se.expleostockholm.signup.utils.InvitationUtils.assertInvitationListsAreEqual;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {SignupDbTests.Initializer.class})
public class CreateEventTest extends SignupDbTests {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    @Resource
    private PersonMapper personMapper;

    @Resource
    private EventMapper eventMapper;

    @Resource
    private InvitationMapper invitationMapper;

    private ObjectNode eventInput;
    private ObjectNode eventVariables;
    private ObjectNode variables;

    private Event expectedEvent;

    public void tearDown() {
        expectedEvent.getInvitations().forEach(e -> invitationMapper.removeInvitationByEventId(e.getEvent_id()));
        expectedEvent.getInvitations().forEach(e -> personMapper.removePersonByEmail(e.getGuest().getEmail()));
        eventMapper.removeEventById(expectedEvent.getId());
    }

    @BeforeEach
    void setUp() {
        final String jwtToken = jwtUtil.generateToken(new User("bla", "", new ArrayList<>()));
        graphQLTestTemplate.addHeader(JwtFilterConstant.HEADER_STRING, JwtFilterConstant.TOKEN_PREFIX + jwtToken);
    }

    @Test
    public void createEvent_test() throws IOException {
        Person host = given_host_exists();
        expectedEvent = when_host_creates_new_event_and_invites_guests(host);
        then_host_can_see_invitation_statistics(expectedEvent);
        tearDown();
    }

    public Person given_host_exists() {
        Optional<Person> host = personMapper.getPersonById(12L);
        assertTrue(host.isPresent());
        return host.get();
    }

    public Event when_host_creates_new_event_and_invites_guests(Person host) throws IOException {
        expectedEvent = createMockEvent(host);
        
        eventVariables = new ObjectMapper().createObjectNode();
        eventInput = new ObjectMapper().createObjectNode();
        eventVariables.putPOJO("host", expectedEvent.getHost());
        eventVariables.putPOJO("title", expectedEvent.getTitle());
        eventVariables.putPOJO("description", expectedEvent.getDescription());
        eventVariables.putPOJO("date_of_event", expectedEvent.getDate_of_event().toString());
        eventVariables.putPOJO("time_of_event", expectedEvent.getTime_of_event().toString());
        eventVariables.putPOJO("location", expectedEvent.getLocation());
        eventVariables.putPOJO("duration", expectedEvent.getDuration().toString());
        eventVariables.putPOJO("invitations", expectedEvent.getInvitations());
        eventVariables.putPOJO("isDraft", true);
        eventVariables.putPOJO("isCanceled", false);
        eventInput.putPOJO("eventInput", eventVariables);

        GraphQLResponse graphQLResponse = graphQLTestTemplate.perform("mutations/createEvent.graphql", eventInput);

        List objectList = graphQLResponse.get("$.data.event.invitations", List.class);
        List<Invitation> actualInvitations = new ObjectMapper().convertValue(objectList, new TypeReference<>() {
        });

        Person actualHost = Person.builder()
                .id(graphQLResponse.get("$.data.event.host.id", Long.class))
                .email(graphQLResponse.get("$.data.event.host.email", String.class))
                .first_name(graphQLResponse.get("$.data.event.host.first_name", String.class))
                .last_name(graphQLResponse.get("$.data.event.host.last_name", String.class))
                .build();

        Event actualEvent = Event.builder()
                .id(graphQLResponse.get("$.data.event.id", Long.class))
                .host(actualHost)
                .title(graphQLResponse.get("$.data.event.title", String.class))
                .description(graphQLResponse.get("$.data.event.description", String.class))
                .date_of_event(LocalDate.parse(graphQLResponse.get("$.data.event.date_of_event", String.class)))
                .time_of_event(LocalTime.parse(graphQLResponse.get("$.data.event.time_of_event", String.class)))
                .duration(Short.parseShort(graphQLResponse.get("$.data.event.duration", String.class)))
                .location(graphQLResponse.get("$.data.event.location", String.class))
                .isDraft(graphQLResponse.get("$.data.event.isDraft", Boolean.class))
                .isCanceled(graphQLResponse.get("$.data.event.isCanceled", Boolean.class))
                .invitations(actualInvitations)
                .build();

        assertEventsAreEqual(expectedEvent, Optional.ofNullable(actualEvent));

        return actualEvent;
    }

    public void then_host_can_see_invitation_statistics(Event expectedEvent) throws IOException {
        variables = new ObjectMapper()
                .createObjectNode()
                .put("event_id", expectedEvent.getId());

        GraphQLResponse graphQLResponse = graphQLTestTemplate.perform("queries/getInvitationsByEventId.graphql", variables);

        List objectList = graphQLResponse.get("$.data.invitations", List.class);
        List<Invitation> actualInvitations = new ObjectMapper().convertValue(objectList, new TypeReference<>() {
        });
        List<Invitation> expectedInvitations = this.expectedEvent.getInvitations();

        assertInvitationListsAreEqual(expectedInvitations, actualInvitations);
    }

}
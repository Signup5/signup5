package se.expleostockholm.signup.integrationtests;

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
import se.expleostockholm.signup.domain.web.Response;
import se.expleostockholm.signup.repository.PersonMapper;
import se.expleostockholm.signup.util.JwtUtil;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {SignupDbTests.Initializer.class})
public class CreateNewUserTest extends SignupDbTests {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    private PersonMapper personMapper;

    @Autowired
    private JwtUtil jwtUtil;

    private ObjectNode personInput;
    private ObjectNode personVariables;
    
    @BeforeEach
    void setUp() {
        final String jwtToken = jwtUtil.generateToken(new User("bla", "", new ArrayList<>()));
        graphQLTestTemplate.addHeader(JwtFilterConstant.HEADER_STRING, JwtFilterConstant.TOKEN_PREFIX + jwtToken);
    }

    @Test
    public void createNewUserTest() throws IOException {
        personInput = new ObjectMapper().createObjectNode();
        personVariables = new ObjectMapper().createObjectNode();
        personVariables.put("email", "Test@Test.com");
        personVariables.put("first_name", "Test");
        personVariables.put("last_name", "TestLastName");
        personVariables.put("password", "password");
        personInput.putPOJO("person", personVariables);

        GraphQLResponse graphQLResponse = graphQLTestTemplate.perform("mutations/createPerson.graphql", personInput);
        Response response = graphQLResponse.get("$.data.response", Response.class);
        String responseMessage = response.getMessage();

        assertEquals("New person saved", responseMessage);
    }
}


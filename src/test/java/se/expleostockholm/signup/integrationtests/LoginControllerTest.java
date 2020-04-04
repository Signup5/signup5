package se.expleostockholm.signup.integrationtests;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashSet;
import java.util.Optional;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;
import se.expleostockholm.signup.domain.Person;
import se.expleostockholm.signup.domain.web.LoginModel;
import se.expleostockholm.signup.domain.web.LoginResponse;
import se.expleostockholm.signup.repository.PersonMapper;
import se.expleostockholm.signup.util.JwtUtil;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {SignupDbTests.Initializer.class})
@AutoConfigureMockMvc
class LoginControllerTest extends SignupDbTests {

  private final String EMAIL = "test@test.com";
  private final String PASSWORD = "test";
  private final String ENDPOINT = "/login";
  private final Person person = new Person();

  @Autowired
  private WebApplicationContext webAppContext;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private ObjectMapper mapper;

  @MockBean
  private PersonMapper personMapper;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @BeforeEach
  private void setUp(){
    person.setEmail(EMAIL);
    person.setPassword(passwordEncoder.encode(PASSWORD));
    when(personMapper.getPersonByEmail(any()))
        .thenReturn(Optional.of(person));
  }

  @Test
  void loginSuccessTest() throws Exception {
    final LoginModel loginDetails = new LoginModel(EMAIL, PASSWORD);
    MvcResult mvcResult = mockMvc.perform(post(ENDPOINT)
        .content(mapper.writeValueAsString(loginDetails))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();
    String content = mvcResult.getResponse().getContentAsString();
    LoginResponse loginResponse = mapper.readValue(content, LoginResponse.class);
    Assert.assertTrue(jwtUtil.validateToken(loginResponse.getJwt(), new User(EMAIL, PASSWORD,
        new HashSet<>())));

  }

  @Test
  void loginFailTest() throws Exception {
    final String incorrectPassword = "12345";
    final LoginModel loginModel = new LoginModel(EMAIL, incorrectPassword);
    mockMvc.perform(post(ENDPOINT)
        .content(mapper.writeValueAsString(loginModel))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }
}

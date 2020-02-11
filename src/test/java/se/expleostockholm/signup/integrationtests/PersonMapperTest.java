package se.expleostockholm.signup.integrationtests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.expleostockholm.signup.domain.Person;
import se.expleostockholm.signup.repository.PersonMapper;

import javax.annotation.Resource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
@ContextConfiguration(initializers = {SignupDbTests.Initializer.class})
class PersonMapperTest extends SignupDbTests {

    @Resource
    PersonMapper personMapper;

    @Test
    void getPersonById() {
        Optional<Person> person = personMapper.getPersonById(1L);

        assertAll(
                () -> assertTrue(person.isPresent(), "No person found!"),
                () -> assertEquals(1L, person.get().getId(), "Person id did not match!"),
                () -> assertEquals("Ali", person.get().getFirst_name(), "First name did not match!"),
                () -> assertEquals("Matys", person.get().getLast_name(), "Last name did not match!"),
                () -> assertEquals("amatys0@wp.com", person.get().getEmail(), "Email did not match!")
        );
    }

    @Test
    void getAllPersons() {
        List<Person> allPersons = personMapper.getAllPersons();
        assertEquals(50, allPersons.size(), "Number of persons did not match!");
    }
}
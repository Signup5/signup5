package se.expleostockholm.signup.utils;

import se.expleostockholm.signup.domain.Person;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonUtils {

    public static void assertPersonsAreEqual(Person expectedPerson, Person actualPerson, String role) {
        assertAll(
                () -> assertEquals(expectedPerson.getId(), actualPerson.getId(), role + " id did not match!"),
                () -> assertEquals(expectedPerson.getEmail(), actualPerson.getEmail(), role + " email did not match!"),
                () -> assertEquals(expectedPerson.getFirst_name(), actualPerson.getFirst_name(), role + " first name did not match!"),
                () -> assertEquals(expectedPerson.getLast_name(), actualPerson.getLast_name(), role + " last name did not match!")
        );

    }
}

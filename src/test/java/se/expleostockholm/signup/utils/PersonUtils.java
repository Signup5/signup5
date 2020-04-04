package se.expleostockholm.signup.utils;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.javafaker.Faker;
import java.util.Locale;
import se.expleostockholm.signup.domain.Person;

public class PersonUtils {

    public static Person createMockPerson() {
        Faker faker = new Faker((new Locale("sv-SE")));
        String randomFirstName = faker.name().firstName();
        String randomLastName = faker.name().lastName();
        String regex = "[^a-zA-Z]+";
        String randomEmail = randomFirstName.replaceAll(regex, "") + "." + randomLastName.replaceAll(regex, "") + "@mail.com";

        return Person.builder()
                .first_name(randomFirstName)
                .last_name(randomLastName)
                .email(randomEmail.toLowerCase())
                .build();
    }

    public static void assertPersonsAreEqual(Person expectedPerson, Person actualPerson, String role) {
        assertAll(
                () -> assertEquals(expectedPerson.getEmail(), actualPerson.getEmail(), role + " email did not match!"),
                () -> assertEquals(expectedPerson.getFirst_name(), actualPerson.getFirst_name(), role + " first name did not match!"),
                () -> assertEquals(expectedPerson.getLast_name(), actualPerson.getLast_name(), role + " last name did not match!")
        );
    }
}
package se.expleostockholm.signup.utils;

import com.github.javafaker.Faker;
import se.expleostockholm.signup.domain.Person;

import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonUtils {

    public static Person mockPerson() {

        Faker faker = new Faker((new Locale("sv-SE")));
        String randomFirstName = faker.name().firstName();
        String randomLastName = faker.name().lastName();
        String regex = "[^a-zA-Z]+";
        randomFirstName.replaceAll(regex, "");
        randomLastName.replaceAll(regex, "");
        String randomEmail = randomFirstName + "." + randomLastName + "@mail.com";
        return Person.builder()
                .first_name(randomFirstName)
                .last_name(randomLastName)
                .email(randomEmail.toLowerCase())
                .build();
    }

    public static void assertPersonsAreEqual(Person expectedPerson, Person actualPerson, String role) {
        assertAll(
                () -> assertEquals(expectedPerson.getId(), actualPerson.getId(), role + " id did not match!"),
                () -> assertEquals(expectedPerson.getEmail(), actualPerson.getEmail(), role + " email did not match!"),
                () -> assertEquals(expectedPerson.getFirst_name(), actualPerson.getFirst_name(), role + " first name did not match!"),
                () -> assertEquals(expectedPerson.getLast_name(), actualPerson.getLast_name(), role + " last name did not match!")
        );
    }
}
package se.expleostockholm.signup.unittests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.expleostockholm.signup.service.ServiceUtil.isValidDate;
import static se.expleostockholm.signup.service.ServiceUtil.isValidEmail;

class ServiceUtilTest {

    @ParameterizedTest
    @MethodSource("providedEmails")
    void isValidEmailTest(String email, boolean expectedResult) {
        assertEquals(expectedResult, isValidEmail(email));
    }

    @ParameterizedTest
    @MethodSource("providedDates")
    void isValidDateTest(LocalDate date, boolean expectedResult) {
        assertEquals(expectedResult, isValidDate(date));
    }

    private static Stream<Arguments> providedDates() {
        return Stream.of(
                Arguments.of(LocalDate.now(), true),
                Arguments.of(LocalDate.now().minusYears(1), false)
        );
    }

    private static Stream<Arguments> providedEmails() {
        return Stream.of(
                Arguments.of("test.testare@test.org", true),
                Arguments.of("test.testare@test..se", false),
                Arguments.of("*<@!||>.com", false),
                Arguments.of("", false),
                Arguments.of(".@..com", false),
                Arguments.of("test@åäö.se", false),
                Arguments.of("test@åäö.se", false)
        );
    }


}
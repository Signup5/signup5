package se.expleostockholm.signup.service;

import se.expleostockholm.signup.domain.Person;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServiceUtil {

    public static boolean isValidEmail(String email) {
        String validEmailPattern = "^[a-zA-Z0-9_#$%&'*+/=?^.-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-_]+\\.)+[a-zA-Z]{2,13}$";
        Pattern pattern = Pattern.compile(validEmailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidDate(LocalDate date) {
        return date.isAfter(LocalDate.now().minusDays(1));
    }

}

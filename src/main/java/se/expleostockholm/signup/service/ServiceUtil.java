package se.expleostockholm.signup.service;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServiceUtil {

    /**
     * Method for validating an email address.
     * <p>
     * Returns a Boolean if the email argument matches a RegEx pattern.
     *
     * @param   email   a String representing the email address to be validated
     * @return          true or false whether email is in a valid format or not
     */
    public static boolean isValidEmail(String email) {
        String validEmailPattern = "^[a-zA-Z0-9_#$%&'*+/=?^.-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-_]+\\.)+[a-zA-Z]{2,13}$";
        Pattern pattern = Pattern.compile(validEmailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Method for validating a date.
     *<p>
     * Returns a boolean if the date argument matches a RegEx pattern.
     *
     * @param   date    a LocalDate to be validated
     * @return          true or false whether the date is in a valid format or not
     */
    public static boolean isValidDate(LocalDate date) {
        return date.isAfter(LocalDate.now().minusDays(1));
    }


}

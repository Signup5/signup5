package se.expleostockholm.signup.service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.time.LocalDate;

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

        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
            return true;
        }  catch (AddressException ex) {
            return false;
         }
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

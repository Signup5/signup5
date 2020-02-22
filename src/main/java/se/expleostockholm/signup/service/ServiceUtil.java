package se.expleostockholm.signup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.domain.Person;
import se.expleostockholm.signup.repository.EventMapper;
import se.expleostockholm.signup.repository.PersonMapper;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ServiceUtil {

    private static PersonMapper personMapper;
    private static EventMapper eventMapper;

    @Autowired
    public ServiceUtil(PersonMapper personMapper, EventMapper eventMapper) {
        this.personMapper = personMapper;
        this.eventMapper = eventMapper;
    }

    public static boolean isValidEmail(String email) {
        String validEmailPattern = "^[a-zA-Z0-9_#$%&'*+/=?^.-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-_]+\\.)+[a-zA-Z]{2,13}$";
        Pattern pattern = Pattern.compile(validEmailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidDate(LocalDate date) {
        return date.isAfter(LocalDate.now().minusDays(1));
    }

    public static boolean doesPersonExist(Person person) {
        return person.getId().equals(personMapper.getPersonById(person.getId()));
    }

    public static boolean doesEventExist(Event event) {
        return eventMapper.eventExists(event) != 0;
    }

}

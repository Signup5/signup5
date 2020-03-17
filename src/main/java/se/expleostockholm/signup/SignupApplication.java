package se.expleostockholm.signup;

import com.github.javafaker.Faker;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.domain.Person;
import se.expleostockholm.signup.service.CalendarService;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;



@SpringBootApplication
public class SignupApplication {

    public static void main(String[] args) throws MessagingException, IOException, ParserException {
        SpringApplication.run(SignupApplication.class, args);


//        Calendar calendar = CalendarService.createIcsCalendar(createMockEvent(createMockPerson()));
////
//        List<String> emails = List.of("marcus8209@gmail.com");
////
//        Message message = createEmail(emails, calendar);
////
//        sendEmail(message);
    }

    public static Event createMockEvent(Person host) {
        Faker faker = new Faker();

        return Event.builder()
                .host(host)
                .date_of_event(LocalDate.now())
                .time_of_event(LocalTime.NOON)
                .description(faker.book().genre())
                .location(faker.rickAndMorty().location())
                .title(faker.rickAndMorty().character())
                .build();
    }

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

}

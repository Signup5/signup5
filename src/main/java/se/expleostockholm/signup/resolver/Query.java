package se.expleostockholm.signup.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.domain.Person;
import se.expleostockholm.signup.service.EventService;
import se.expleostockholm.signup.service.InvitationService;
import se.expleostockholm.signup.service.PersonService;

import java.util.List;

@Component
@AllArgsConstructor
public class Query implements GraphQLQueryResolver {

    private final PersonService personService;
    private final EventService eventService;
    private final InvitationService invitationService;

    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    public Person getPersonById(Long id) {
        return personService.getPersonById(id);
    }

    public Person getPersonByEmail(String email) { return personService.getPersonByEmail(email);}

    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    public Event getEventById(Long id) {
        return eventService.getEventById(id);
    }

    public List<Invitation> getAllInvitations() {
        return invitationService.getAllInvitations();
    }

    public Invitation getInvitationById(Long id) {
        return invitationService.getInvitationById(id);
    }

    public List<Invitation> getInvitationsByEventId(Long id) {
        return invitationService.getInvitationsByEventId(id);
    }
}

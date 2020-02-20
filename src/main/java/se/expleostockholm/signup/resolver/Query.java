package se.expleostockholm.signup.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.domain.Person;
import se.expleostockholm.signup.service.EventService;
import se.expleostockholm.signup.service.InvitationService;
import se.expleostockholm.signup.service.PersonService;

import java.util.List;

@Component
public class Query implements GraphQLQueryResolver {

    private final PersonService personService;
    private final EventService eventService;
    private final InvitationService invitationService;

    public Query(PersonService personService, EventService eventService, InvitationService invitationService) {
        this.personService = personService;
        this.eventService = eventService;
        this.invitationService = invitationService;
    }

    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    public Person getPersonById(Long id) {
        return personService.getPersonById(id);
    }

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

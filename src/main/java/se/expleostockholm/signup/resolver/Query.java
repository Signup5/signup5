package se.expleostockholm.signup.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.domain.Person;
import se.expleostockholm.signup.service.EventService;
import se.expleostockholm.signup.service.InvitationService;
import se.expleostockholm.signup.service.PersonService;

@Component
@AllArgsConstructor
public class Query implements GraphQLQueryResolver {

    private final PersonService personService;
    private final EventService eventService;
    private final InvitationService invitationService;

    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    public List<Event> getEventsByHostId(Long id) {
        return eventService.getEventsByHostId(id);
    }

    public List<Invitation> getInvitationsByGuestId(Long id) {
        return invitationService.getInvitationsByGuestId(id);
    }

    /**
     * GraphQL endpoint for for retrieving a Person based on its Id.
     * <p>
     * Accepts a Long as an argument representing the Person Id in the database.
     *
     * @param   id  a Long value representing a Person Id
     * @return      a Person if Person Id was found in the database
     */
    public Person getPersonById(Long id) {
        return personService.getPersonById(id);
    }


    /**
     * GraphQL endpoint for retrieving a Person based on its email address.
     * <p>
     * Accepts email as a String argument to be matched with a Person in the database.
     *
     * @param   email  a String representing a Persons email address
     * @return      a Person if email was found in the database
     */
    public Person getPersonByEmail(String email) {
        return personService.getPersonByEmail(email);}


    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    public List<Event> getHostedAndInvitedEventsByPersonId(Long id) {
        return eventService.getHostedAndInvitedEventsByPersonId(id);
    }

    /**
     * GraphQL endpoint for retrieving an Event from the Database based on its Id.
     * <p>
     * Accepts a Long as an argument representing the Event Id in the database.
     *
     * @param   id  a Long value representing an Event Id
     * @return      an Event if Id was found in the database
     */
    public Event getEventById(Long id) {
        return eventService.getEventById(id);
    }

    public List<Invitation> getAllInvitations() {
        return invitationService.getAllInvitations();
    }


    /**
     * GraphQL endpoint for retrieving an Invitation from the Database based on its Id.
     * <p>
     * Accepts a Long as an argument representing the Invitation Id in the database.
     *
     * @param   id  a Long value representing an Invitation Id
     * @return      an Invitation if Id was found in the database
     */
    public Invitation getInvitationById(Long id) {
        return invitationService.getInvitationById(id);
    }

    public List<Invitation> getUpcomingUnRepliedInvitationsByGuestId(Long id) {
        return invitationService.getUpcomingUnRepliedInvitationsByGuestId(id);
    }

    /**
     * GraphQL endpoint for retrieving all Invitations for an Event based on the Event Id.
     * <p>
     * Accepts a Long as an argument representing the Event Id in the database.
     *
     * @param   id  a Long value representing an Event Id
     * @return      a list of Invitations if Event Id was found in the database
     */
    public List<Invitation> getInvitationsByEventId(Long id) {
        return invitationService.getInvitationsByEventId(id);
    }

}

package se.expleostockholm.signup.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.exception.EventAlreadyExistException;
import se.expleostockholm.signup.exception.EventNotFoundException;
import se.expleostockholm.signup.exception.InvalidDateException;
import se.expleostockholm.signup.exception.PersonNotFoundException;
import se.expleostockholm.signup.repository.EventMapper;

import javax.mail.internet.MimeMessage;
import java.util.List;

import static se.expleostockholm.signup.service.ServiceUtil.isValidDate;

@Service
@AllArgsConstructor
public class EventService {

    private final EventMapper eventMapper;
    private final InvitationService invitationService;
    private final PersonService personService;
    private final EmailService emailService;

    /**
     * Method for creating a new event. Accepts an Event as an argument to be stored in the database if no duplicates
     * are found in the database.
     * <p>
     * The method uses verifications the Event properties to see if it matches an already existing event.
     * <p>
     * Returns an Event object with an updated Id property if no duplicates are found in the database.
     *
     * @param event the event to be checked for duplicates and saved in the database
     * @return an updated Even bean with a Id property
     */
    public Event createNewEvent(Event event) {
        if (!isDuplicateEvent(event)) {
            if (!personService.doesPersonExist(event.getHost())) {
                if (isValidDate(event.getDate_of_event())) {
                    eventMapper.saveEvent(event);
                    invitationService.saveInvitations(event.getInvitations(), event.getId());
                        sendInvitationEmail(event);
                    return event;
                }
                throw new InvalidDateException("Invalid date. Start of event cannot be in the past!");
            }
            throw new PersonNotFoundException("Event host not found!");
        }
        throw new EventAlreadyExistException("'" + event.getTitle() + "': " + event.getDate_of_event() + ": " + event.getTime_of_event() + " - Event already exists");
    }

    protected void sendInvitationEmail(Event event) {
        event.getInvitations().forEach(invitation -> {
            MimeMessage message = emailService.createInvitationEmail(invitation.getGuest().getEmail(), event);
            emailService.sendMail(message);
        });
    }

    public List<Event> getAllEvents() {
        List<Event> events = eventMapper.getAllEvents();
        if (events.size() == 0) {
            throw new EventNotFoundException("No events found!");
        }
        return events;
    }

    /**
     * Method for retrieving an Event from the Database based on its Id.
     * <p>
     * Accepts a Long as an argument representing the Event Id in the database.
     *
     * @param id a Long value representing an Event Id
     * @return an Event if Id was found in the database
     */
    public Event getEventById(Long id) {
        return eventMapper.getEventById(id).orElseThrow(() -> new EventNotFoundException("No event found!"));
    }

    /**
     * Method accepting an Event as an argument to check if there is an Event with the same data already found in the
     * database.
     *
     * @param event Event to be checked if it's not already in the database
     * @return true or false whether a duplicate Event was found
     */
    public boolean isDuplicateEvent(Event event) {
        return eventMapper.isDuplicateEvent(event) == 1;
    }

    public List<Event> getEventsByHostId(Long id) {

        List<Event> events = eventMapper.getEventsByHostId(id);
        if (events.size() == 0) {
            throw new EventNotFoundException("No events found!");
        }
        return events;
    }
}

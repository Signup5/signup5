package se.expleostockholm.signup.service;

import static se.expleostockholm.signup.service.ServiceUtil.isValidDate;
import static se.expleostockholm.signup.service.ServiceUtil.personNotInInvitationList;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.exception.EventException;
import se.expleostockholm.signup.exception.InvalidDateException;
import se.expleostockholm.signup.exception.PersonException;
import se.expleostockholm.signup.repository.EventMapper;

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
                    emailService.sendInvitationEmail(event);
                    emailService.sendCalendarToHostEmail(event);
                    return event;
                }
                throw new InvalidDateException("Invalid date. Start of event cannot be in the past!");
            }
            throw new PersonException("Event host not found!");
        }
        throw new EventException("'" + event.getTitle() + "': " + event.getDate_of_event() + ": " + event.getTime_of_event() + " - Event already exists");
    }


    public List<Event> getAllEvents() {
        return eventMapper.getAllEvents();
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
        return eventMapper.getEventById(id).orElseThrow(() -> new EventException("No event found!"));
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
        return eventMapper.getEventsByHostId(id);
    }

    public List<Event> getHostedAndInvitedEventsByPersonId(Long id) {
        return Stream.of(getEventsByHostId(id), getEventByGuestId(id))
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(Event::getDate_of_event))
                .collect(Collectors.toList());
    }

    public List<Event> getEventByGuestId(Long id) {
        return eventMapper.getEventsByGuestId(id);
    }

    public void updateEvent(Event event) {
        if (eventMapper.updateEvent(event) == 0) throw new EventException("No event updated!");

        List<Invitation> updatedInvitationList = event.getInvitations();
        List<Invitation> existingInvitations = invitationService.getInvitationsByEventId(event.getId());

        List<Invitation> invitationsToAdd = updatedInvitationList.stream()
                .filter(personNotInInvitationList(existingInvitations))
                .collect(Collectors.toList());

        List<Invitation> invitationsToRemove = existingInvitations.stream()
                .filter(personNotInInvitationList(updatedInvitationList))
                .collect(Collectors.toList());

        invitationService.saveInvitations(invitationsToAdd, event.getId());
        invitationService.deleteInvitations(invitationsToRemove);

        if (!event.getIsDraft()) {
            emailService.sendInvitationEmail(event);
            emailService.sendCalendarToHostEmail(event);
        }
    }

    public void cancelEvent(Long id) {
        if (eventMapper.cancelEvent(id) == 0)
            throw new EventException("Error updating event!");
    }
}

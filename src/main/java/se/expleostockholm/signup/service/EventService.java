package se.expleostockholm.signup.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.exception.EventAlreadyExistException;
import se.expleostockholm.signup.exception.EventNotFoundException;
import se.expleostockholm.signup.exception.InvalidDateException;
import se.expleostockholm.signup.repository.EventMapper;

import java.util.List;

import static se.expleostockholm.signup.service.ServiceUtil.*;

@Service
@AllArgsConstructor
public class EventService {

    private final EventMapper eventMapper;
    private final InvitationService invitationService;

    /**
     * Method for creating a new event. If the title, hostId, date, time and location match an existing event in the
     * database a EventAlreadyExistException is thrown.
     * @param event
     * @return Event
     */
    public Event createNewEvent(Event event) {
        if (!doesEventExist(event)) {
            if (!doesPersonExist(event.getHost())) {
                if (isValidDate(event.getDate_of_event())) {
                    eventMapper.saveEvent(event);
                    invitationService.saveInvitations(event.getInvitations(), event.getId());
                    return event;
                }
                throw new InvalidDateException("Invalid date. Start of event cannot be in the past!");
            }
        }

        throw new EventAlreadyExistException("'" + event.getTitle() + "': " + event.getDate_of_event() + ": " + event.getTime_of_event() + " - Event already exists");
    }

    public List<Event> getAllEvents() {
        List<Event> events = eventMapper.getAllEvents();
        if (events.size() == 0) {
            throw new EventNotFoundException("No events found!");
        }
        return events;
    }

    public Event getEventById(Long id) {
        return eventMapper.getEventById(id).orElseThrow(() -> new EventNotFoundException("No event found!"));
    }

}

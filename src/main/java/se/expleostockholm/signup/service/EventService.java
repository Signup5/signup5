package se.expleostockholm.signup.service;

import org.springframework.stereotype.Service;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.repository.EventMapper;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EventService {

    private EventMapper eventMapper;

    public EventService(EventMapper eventMapper) {
        this.eventMapper = eventMapper;
    }

    public List<Event> getAllEvents() {
        return eventMapper.getAllEvents();
    }

}

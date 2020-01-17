package se.expleostockholm.signup.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.repository.EventMapper;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EventService {

    @Resource
    private EventMapper eventMapper;

    public List<Event> getEventById(Long id) {
        return eventMapper.getEventById(id);
    }

    public List<Event> getAllEvents() {
        return eventMapper.getAllEvents();
    }

}

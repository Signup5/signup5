package se.expleostockholm.signup.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.services.EventService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class Query implements GraphQLQueryResolver {

    @Resource
    EventService eventService;

    public List<Event> allEvents() {
        return eventService.getAllEvents();
    }

}

package se.expleostockholm.signup.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.exception.EventNotFoundException;
import se.expleostockholm.signup.exception.InvitationNotFoundException;
import se.expleostockholm.signup.repository.EventMapper;
import se.expleostockholm.signup.repository.InvitationMapper;

import java.util.List;

@Component
public class Query implements GraphQLQueryResolver {

    private InvitationMapper invitationMapper;
    private EventMapper eventMapper;

    public Query(InvitationMapper invitationMapper, EventMapper eventMapper) {
        this.invitationMapper = invitationMapper;
        this.eventMapper = eventMapper;
    }

    public List<Event> allEvents() {
        return eventMapper.getAllEvents();
    }

    public Invitation getInvitationById(Long id) {
        return invitationMapper.getInvitationById(id).orElseThrow(() -> new InvitationNotFoundException("No invitation found!"));
    }

    public Event getEventById(Long id) {
        return eventMapper.getEventById(id).orElseThrow(() -> new EventNotFoundException("No event found!"));
    }

}

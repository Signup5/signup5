package se.expleostockholm.signup.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;
import se.expleostockholm.signup.domain.Attendance;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.repository.InvitationMapper;
import se.expleostockholm.signup.services.EventService;

import javax.annotation.Resource;
import java.util.List;

@Component
public class Query implements GraphQLQueryResolver {

    @Resource
    private EventService eventService;
    @Resource
    private InvitationMapper invitationMapper;

    public List<Event> allEvents() {
        return eventService.getAllEvents();
    }

    public Invitation getInvitationById(Long id) {
        return invitationMapper.getInvitationById(id).get();
    }

}

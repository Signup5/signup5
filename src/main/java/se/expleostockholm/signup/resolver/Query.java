package se.expleostockholm.signup.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.service.EventService;
import se.expleostockholm.signup.service.InvitationService;

import java.util.List;

@Component
@Slf4j
public class Query implements GraphQLQueryResolver {


    private EventService eventService;
    private InvitationService invitationService;

    public Query(EventService eventService, InvitationService invitationService) {
        this.eventService = eventService;
        this.invitationService = invitationService;
    }

    public List<Event> allEvents() {
        log.info("hejsansvejsan");
        return eventService.getAllEvents();
    }

    public Invitation getInvitationById(Long id) {
        return invitationService.getInvitationById(id);
    }

}

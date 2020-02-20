package se.expleostockholm.signup.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;
import se.expleostockholm.signup.domain.Attendance;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.domain.Response;
import se.expleostockholm.signup.exception.EventAlreadyExistException;
import se.expleostockholm.signup.repository.EventMapper;
import se.expleostockholm.signup.repository.InvitationMapper;
import se.expleostockholm.signup.repository.PersonMapper;
import se.expleostockholm.signup.service.EventService;
import se.expleostockholm.signup.service.InvitationService;
import se.expleostockholm.signup.service.PersonService;

import java.util.List;

@Component
public class Mutation implements GraphQLMutationResolver {

    private final EventService eventService;
    private final InvitationService invitationService;

    public Mutation(EventService eventService, InvitationService invitationService) {
        this.eventService = eventService;
        this.invitationService = invitationService;
    }

    public Response setAttendance(Attendance attendance, Long invitation_id) {
        invitationService.setAttendance(attendance, invitation_id);
        return Response.builder()
                .message("Attendance was successfully updated!")
                .build();
    }

    public Response createEvent(Event event) {
        eventService.saveEvent(event);
        System.out.println("Mutation: " + event.toString());
        return Response.builder()
                .message("Event was successfully saved")
                .id(event.getId())
                .build();
    }
}

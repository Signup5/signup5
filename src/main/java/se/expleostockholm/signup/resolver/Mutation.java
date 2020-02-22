package se.expleostockholm.signup.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import se.expleostockholm.signup.domain.Attendance;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.domain.Response;
import se.expleostockholm.signup.service.EventService;
import se.expleostockholm.signup.service.InvitationService;

@Component
@AllArgsConstructor
public class Mutation implements GraphQLMutationResolver {

    private final EventService eventService;
    private final InvitationService invitationService;

    public Response setAttendance(Attendance attendance, Long invitation_id) {
        invitationService.setAttendance(attendance, invitation_id);
        return Response.builder()
                .message("Attendance was successfully updated!")
                .build();
    }

    public Response createEvent(Event event) {
        eventService.createNewEvent(event);

        return Response.builder()
                .message("Event was successfully saved!")
                .id(event.getId())
                .build();
    }
}

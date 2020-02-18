package se.expleostockholm.signup.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;
import se.expleostockholm.signup.domain.*;
import se.expleostockholm.signup.repository.EventMapper;
import se.expleostockholm.signup.repository.InvitationMapper;
import se.expleostockholm.signup.repository.PersonMapper;

import java.util.List;
import java.util.Optional;

@Component
public class Mutation implements GraphQLMutationResolver {

    InvitationMapper invitationMapper;
    EventMapper eventMapper;
    PersonMapper personMapper;

    public Mutation(InvitationMapper invitationMapper, EventMapper eventMapper, PersonMapper personMapper) {
        this.invitationMapper = invitationMapper;
        this.eventMapper = eventMapper;
        this.personMapper = personMapper;
    }

    public Response setAttendance(Attendance attendance, Long invitation_id) {
        String responseMessage = invitationMapper.setAttendance(attendance, invitation_id) == 1 ?
                "Attendance was updated!" : "Oops... something went wrong while updating attendance.";

        return Response.builder()
                .message(responseMessage)
                .build();
    }

    public Response createEvent(Event event) {
        Long nrOfUpdatedEventRows = eventMapper.saveEvent(event);
        if (nrOfUpdatedEventRows > 0) {
            List<Invitation> invitations = event.getInvitations();
            Long nrOfInvitations = 0L;

            for (Invitation invitation: invitations) {
                Optional<Person> guest = personMapper.getPersonByEmail(invitation.getGuest().getEmail());
                if (guest.isEmpty()) {
                    personMapper.savePerson(invitation.getGuest());
                } else {
                    invitation.setGuest(guest.get());
                }
                invitation.setEvent_id(event.getId());
                nrOfInvitations += invitationMapper.saveInvitation(invitation);
            }
            if (nrOfInvitations == invitations.size())  {
                return Response.builder()
                        .message("Event was successfully saved")
                        .id(event.getId())
                        .build();
            }
        }

        return Response.builder()
                .message("Oops... something went wrong while creating event")
                .build();
    }
}

package se.expleostockholm.signup.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;
import se.expleostockholm.signup.domain.Attendance;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.domain.Person;
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

    public String setAttendance(Attendance attendance, Long invitation_id) {
        return invitationMapper.setAttendance(attendance, invitation_id) == 1 ?
                "Attendance was updated!" : "Oops... something went wrong while updating attendance.";
    }

    public String createEvent(Event event) {
        System.out.println("are we here====");


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
                System.out.println(invitation.toString());
            }
            System.out.println(event.toString());

            if (nrOfInvitations == invitations.size())  {
                return "Event was Successfully saved";
            }
        }
        System.out.println(event.toString());
        return "Oops... something went wrong while creating Event.";
    }
}

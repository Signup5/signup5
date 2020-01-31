package se.expleostockholm.signup.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;
import se.expleostockholm.signup.domain.Attendance;
import se.expleostockholm.signup.service.InvitationService;

@Component
public class Mutation implements GraphQLMutationResolver {

    InvitationService invitationService;

    public Mutation(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    public String setAttendance(Attendance attendance, Long invitation_id) {

        return invitationService.setAttendance(attendance, invitation_id) == 1 ?
                "Attendance was updated!" : "Oops... something went wrong while updating attendance.";
    }
}

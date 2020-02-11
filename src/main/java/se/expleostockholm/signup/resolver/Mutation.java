package se.expleostockholm.signup.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;
import se.expleostockholm.signup.domain.Attendance;
import se.expleostockholm.signup.repository.InvitationMapper;

@Component
public class Mutation implements GraphQLMutationResolver {

    InvitationMapper invitationMapper;

    public Mutation(InvitationMapper invitationMapper) {
        this.invitationMapper = invitationMapper;
    }

    public String setAttendance(Attendance attendance, Long invitation_id) {
        return invitationMapper.setAttendance(attendance, invitation_id) == 1 ?
                "Attendance was updated!" : "Oops... something went wrong while updating attendance.";
    }
}

package se.expleostockholm.signup.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;
import se.expleostockholm.signup.domain.Attendance;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.repository.InvitationMapper;

import javax.annotation.Resource;

@Component
public class Mutation implements GraphQLMutationResolver {

    @Resource
    InvitationMapper invitationMapper;

    public Invitation setAttendance(Attendance attendance, Long invitation_id) {
        return invitationMapper.setAttendance(attendance,  invitation_id);
    }

}

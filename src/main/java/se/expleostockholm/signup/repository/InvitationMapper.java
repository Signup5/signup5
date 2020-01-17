package se.expleostockholm.signup.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import se.expleostockholm.signup.domain.Attendance;
import se.expleostockholm.signup.domain.Invitation;

import java.util.Optional;

@Mapper
public interface InvitationMapper {

    @Select(getInvitationById)
    Optional<Invitation> getInvitationById(@Param("invitation_id") Long invitation_id);

    @Update(setAttendance)
    Long setAttendance(@Param("attendance") Attendance attendance, @Param("invitation_id") Long invitation_id);

    String getInvitationById =
            "SELECT * from signup.invitation WHERE id = #{invitation_id}";

    String setAttendance =
            "UPDATE signup.invitation SET attendance=#{attendance}::signup.attendance WHERE id = #{invitation_id}";
}
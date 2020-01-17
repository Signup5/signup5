package se.expleostockholm.signup.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import se.expleostockholm.signup.domain.Attendance;
import se.expleostockholm.signup.domain.Invitation;

@Mapper
public interface InvitationMapper {

    @Select("UPDATE signup.invitation SET attendance = #{attendance}::attendance WHERE id = #{invitation_id}")
    Invitation setAttendance(@Param("attendance") Attendance attendance, @Param("invitation_id") Long invitation_id);

}

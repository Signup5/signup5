package se.expleostockholm.signup.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import se.expleostockholm.signup.domain.Attendance;

@Mapper
public interface InvitationMapper {

    @Update("UPDATE signup.invitation SET attendance=#{attendance}::signup.attendance WHERE id = #{invitation_id}")
    Long setAttendance(@Param("attendance") Attendance attendance, @Param("invitation_id") Long invitation_id);

}

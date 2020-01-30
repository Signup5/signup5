package se.expleostockholm.signup.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import se.expleostockholm.signup.domain.Attendance;
import se.expleostockholm.signup.domain.Invitation;

import java.util.Optional;

@Mapper
@Repository
public interface InvitationMapper {

    @Select("SELECT * from invitation WHERE id = #{invitation_id}")
    Optional<Invitation> getInvitationById(@Param("invitation_id") Long invitation_id);

    @Update("UPDATE invitation SET attendance=#{attendance}::attendance WHERE id = #{invitation_id}")
    Long setAttendance(@Param("attendance") Attendance attendance, @Param("invitation_id") Long invitation_id);
}
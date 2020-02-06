package se.expleostockholm.signup.repository;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import se.expleostockholm.signup.domain.Attendance;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.domain.Invitation;
import se.expleostockholm.signup.domain.Person;

import java.util.Optional;

@Mapper
@Repository
public interface InvitationMapper {

    @Select("SELECT * from invitation WHERE id = #{id}")
    @Results({
            @Result(property = "person", column = "person_id",
                    one = @One(select = "se.expleostockholm.signup.repository.PersonMapper.getPersonById")),
            @Result(property = "event", column = "event_id",
                    one = @One(select = "se.expleostockholm.signup.repository.EventMapper.getEventById"))
    })
    Optional<Invitation> getInvitationById(Long id);

    @Update("UPDATE invitation SET attendance=#{attendance}::attendance WHERE id = #{invitation_id}")
    Long setAttendance(@Param("attendance") Attendance attendance, @Param("invitation_id") Long invitation_id);
}
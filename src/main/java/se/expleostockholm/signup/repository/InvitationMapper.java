package se.expleostockholm.signup.repository;

import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import se.expleostockholm.signup.domain.Attendance;
import se.expleostockholm.signup.domain.Invitation;

@Mapper
@Repository
public interface InvitationMapper {

    /**
     * Fetching single Invitation.
     * @param id
     * @return
     */
    @Select("SELECT * from invitation WHERE id = #{id}")
    @Results({
            @Result(property = "guest", column = "guest_id",
                    one = @One(select = "se.expleostockholm.signup.repository.PersonMapper.getPersonById"))
    })
    Optional<Invitation> getInvitationById(Long id);

    /**
     * Method setting Attendance to an Invitation.
     * @param attendance
     * @param invitation_id
     * @return
     */
    @Update("UPDATE invitation SET attendance=#{attendance}::attendance WHERE id = #{invitation_id}")
    Long setAttendance(@Param("attendance") Attendance attendance, @Param("invitation_id") Long invitation_id);

    /**
     * Requesting all Invitations.
     * @return list of all Invitations
     */
    @Select("SELECT * FROM invitation")
    @Results({
            @Result(property = "guest", column = "guest_id",
                    one = @One(select = "se.expleostockholm.signup.repository.PersonMapper.getPersonById"))
    })
    List<Invitation> getAllInvitations();

    /**
     * Fetch all Invitations from Event.
     * @param id
     * @return list of Invitations
     */
    @Select("SELECT * FROM invitation WHERE event_id = #{id}")
    @Results({
            @Result(property = "guest", column = "guest_id",
                    one = @One(select = "se.expleostockholm.signup.repository.PersonMapper.getPersonById"))
    })
    List<Invitation> getInvitationsByEventId(Long id);

    /**
     * Save Invitation. Returns 1 from DB if Invitations was saved. 0 if Invitations wasn't saved.
     * @param invitation
     * @return number of updated rows
     */
    @Insert("INSERT INTO invitation (guest_id, event_id, attendance) VALUES (#{guest.id}, #{event_id}, #{attendance}::attendance)")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Long saveInvitation(Invitation invitation);

    /**
     * Check if Invitation exist.
     * @param invitation
     * @return number of invitations that matches Invitation
     */
    @Select("SELECT COUNT(*) FROM invitation WHERE event_id=#{event_id} and guest_id=#{guest.id}")
    Long invitationExists(Invitation invitation);

    /**
     * Delete Invitation by Event id.
     * @param id
     */
    @Delete("DELETE FROM invitation WHERE event_id = #{id}")
    void removeInvitationByEventId(Long id);

    /**
     * Remove Invitation.
     * @param id
     * @return a Long with number of updated rows
     */
    @Delete("DELETE FROM invitation WHERE invitation.id = #{id}")
    Long removeInvitationById(Long id);

    /**
     * Fetch all Invitations for Guest.
     * @param id
     * @return list of all Invitations in database
     */
    @Select("SELECT * FROM invitation WHERE guest_id = #{id}")
    List<Invitation> getInvitationsByGuestId(Long id);

    /**
     * Fetch un replied Invitations by Guest.
     * @param id
     * @return list of Invitations
     */
    @Select("SELECT * FROM invitation WHERE guest_id = #{id} " +
            "AND (attendance = 'NO_RESPONSE' OR attendance = 'MAYBE') " +
            "AND (SELECT isCanceled FROM event WHERE event.id = event_id) = false " +
            "AND (SELECT isDraft FROM event WHERE event.id = event_id) = false " +
            "AND (SELECT date_of_event FROM event WHERE event.id = invitation.event_id) >= now()::DATE " +
            "ORDER BY (SELECT date_of_event FROM event WHERE event.id = invitation.event_id) ASC")
    @Results({
            @Result(property = "guest", column = "guest_id",
                    one = @One(select = "se.expleostockholm.signup.repository.PersonMapper.getPersonById"))
    })
    List<Invitation> getUpcomingUnRepliedInvitationsByGuestId(Long id);
}
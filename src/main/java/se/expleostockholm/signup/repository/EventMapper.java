package se.expleostockholm.signup.repository;

import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import se.expleostockholm.signup.domain.Event;

@Mapper
@Repository
public interface EventMapper {

    /**
     * Fetch all events.
     * @return
     */
    @Select("SELECT * FROM event")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "host", column = "host_id",
                    one = @One(select = "se.expleostockholm.signup.repository.PersonMapper.getPersonById")),
            @Result(property = "invitations", javaType = List.class, column = "id",
                    many = @Many(select = "se.expleostockholm.signup.repository.InvitationMapper.getInvitationsByEventId"))
    })
    List<Event> getAllEvents();

    /**
     * Fetch single Event.
     * @param event_id
     * @return
     */
    @Select("SELECT * FROM event WHERE id = #{event_id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "host", column = "host_id",
                    one = @One(select = "se.expleostockholm.signup.repository.PersonMapper.getPersonById")),
            @Result(property = "invitations", javaType = List.class, column = "id",
                    many = @Many(select = "se.expleostockholm.signup.repository.InvitationMapper.getInvitationsByEventId"))
    })
    Optional<Event> getEventById(Long event_id);

    /**
     * Method saving an Event.
     * @param event
     * @return
     */
    @Insert("INSERT INTO event (host_id, title, description, date_of_event, time_of_event, duration, location, isDraft) VALUES (#{host.id}, #{title}, #{description}, #{date_of_event}, #{time_of_event}, #{duration}, #{location}, #{isDraft})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long saveEvent(Event event);

    /**
     * Method deleting Event.
     * @param id
     */
    @Delete("DELETE FROM event WHERE id = #{id}")
    void removeEventById(Long id);

    /**
     * Method checking if Event already exists.
     *
     * @param event event to check if it exists in database
     * @return number of events that matches Event.
     */
    @Select("SELECT COUNT(*) FROM event WHERE host_id=#{host.id} AND title=#{title} AND date_of_event=#{date_of_event} AND time_of_event=#{time_of_event} AND location=#{location}")
    Long isDuplicateEvent(Event event);

    /**
     * Fetch all Events by host id.
     * @param id
     * @return
     */
    @Select("SELECT * FROM event WHERE host_id = #{id} AND isCanceled = false")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "host", column = "host_id",
                    one = @One(select = "se.expleostockholm.signup.repository.PersonMapper.getPersonById")),
            @Result(property = "invitations", javaType = List.class, column = "id",
                    many = @Many(select = "se.expleostockholm.signup.repository.InvitationMapper.getInvitationsByEventId"))
    })
    List<Event> getEventsByHostId(Long id);

    /**
     * Fetch Events by Guest where Guest is attending.
     * @param id
     * @return
     */
    @Select("SELECT * FROM event " +
            "WHERE (SELECT invitation.guest_id FROM invitation WHERE invitation.event_id = event.id AND invitation.guest_id = ${id} AND attendance = 'ATTENDING') = ${id} " +
            "AND isDraft = FALSE AND isCanceled = false")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "host", column = "host_id",
                    one = @One(select = "se.expleostockholm.signup.repository.PersonMapper.getPersonById")),
            @Result(property = "invitations", javaType = List.class, column = "id",
                    many = @Many(select = "se.expleostockholm.signup.repository.InvitationMapper.getInvitationsByEventId"))
    })
    List<Event> getEventsByGuestIdWhereGuestIsAttending(Long id);

    /**
     * Method for updating Event.
     * @param event
     * @return
     */
    @Update("UPDATE event SET title = #{title}, " +
            "host_id = #{host.id}, " +
            "description = #{description}, " +
            "date_of_event = #{date_of_event}, " +
            "time_of_event = #{time_of_event}, " +
            "location = #{location},  " +
            "duration = #{duration}, " +
            "isDraft = #{isDraft} " +
            "WHERE id = #{id}")
    Long updateEvent(Event event);

    /**
     * Method for canceling Event.
     * @param id
     * @return
     */
    @Update("UPDATE event SET isCanceled = TRUE WHERE id = #{id}")
    Long cancelEvent(Long id);
}
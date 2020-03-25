package se.expleostockholm.signup.repository;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import se.expleostockholm.signup.domain.Event;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface EventMapper {

    @Select("SELECT * FROM event")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "host", column = "host_id",
                    one = @One(select = "se.expleostockholm.signup.repository.PersonMapper.getPersonById")),
            @Result(property = "invitations", javaType = List.class, column = "id",
                    many = @Many(select = "se.expleostockholm.signup.repository.InvitationMapper.getInvitationsByEventId"))
    })
    List<Event> getAllEvents();


    @Select("SELECT * FROM event WHERE id = #{event_id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "host", column = "host_id",
                    one = @One(select = "se.expleostockholm.signup.repository.PersonMapper.getPersonById")),
            @Result(property = "invitations", javaType = List.class, column = "id",
                    many = @Many(select = "se.expleostockholm.signup.repository.InvitationMapper.getInvitationsByEventId"))
    })
    Optional<Event> getEventById(Long event_id);


    @Insert("INSERT INTO event (host_id, title, description, date_of_event, time_of_event, duration, location, isDraft) VALUES (#{host.id}, #{title}, #{description}, #{date_of_event}, #{time_of_event}, #{duration}, #{location}, #{isDraft})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long saveEvent(Event event);

    @Delete("DELETE FROM event WHERE id = #{id}")
    void removeEventById(Long id);

    @Select("SELECT COUNT(*) FROM event WHERE host_id=#{host.id} and title=#{title} and date_of_event=#{date_of_event} and time_of_event=#{time_of_event} and location=#{location}")
    Long isDuplicateEvent(Event event);

    @Select("SELECT * FROM event WHERE host_id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "host", column = "host_id",
                    one = @One(select = "se.expleostockholm.signup.repository.PersonMapper.getPersonById")),
            @Result(property = "invitations", javaType = List.class, column = "id",
                    many = @Many(select = "se.expleostockholm.signup.repository.InvitationMapper.getInvitationsByEventId"))
    })
    List<Event> getEventsByHostId(Long id);

    @Select("SELECT * FROM event WHERE (SELECT invitation.guest_id FROM invitation WHERE invitation.event_id = event.id AND invitation.guest_id = ${id} AND attendance != 'NOT_ATTENDING') = ${id} AND isDraft = false")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "host", column = "host_id",
                    one = @One(select = "se.expleostockholm.signup.repository.PersonMapper.getPersonById")),
            @Result(property = "invitations", javaType = List.class, column = "id",
                    many = @Many(select = "se.expleostockholm.signup.repository.InvitationMapper.getInvitationsByEventId"))
    })
    List<Event> getEventsByGuestId(Long id);

    @Update("UPDATE event SET title = #{title}, " +
            "host_id = #{host.id}, " +
            "description = #{description}, " +
            "date_of_event = #{date_of_event}, " +
            "time_of_event = #{time_of_event}, " +
            "location = #{location}, " +
            "duration = #{duration}, " +
            "isDraft = #{isDraft}")
    Long updateEvent(Event event);
}

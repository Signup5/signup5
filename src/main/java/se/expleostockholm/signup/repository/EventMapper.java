package se.expleostockholm.signup.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import se.expleostockholm.signup.domain.Event;

import java.util.List;

@Mapper
public interface EventMapper {

    @Select("SELECT * FROM event WHERE id = #{event_id}")
    List<Event> getEventById(@Param("event_id") Long event_id);

    @Select("SELECT * FROM signup.event")
    List<Event> getAllEvents();
}

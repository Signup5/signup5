package se.expleostockholm.signup.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import se.expleostockholm.signup.domain.Event;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface EventMapper {
    @Select("SELECT * FROM event")
    List<Event> getAllEvents();

    @Select("SELECT * FROM event WHERE id = ${event_id}")
    Optional<Event> getEventById(Long event_id);
}

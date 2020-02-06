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
    @Results({@Result(property = "host", column = "host_id",
                    one = @One(select = "se.expleostockholm.signup.repository.PersonMapper.getPersonById"))})
    List<Event> getAllEvents();

    @Select("SELECT * FROM event WHERE id = ${event_id}")
    @Results({@Result(property = "host", column = "host_id",
            one = @One(select = "se.expleostockholm.signup.repository.PersonMapper.getPersonById"))})
    Optional<Event> getEventById(Long event_id);
}

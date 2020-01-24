package se.expleostockholm.signup.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import se.expleostockholm.signup.domain.Event;

import java.util.List;

@Mapper
public interface EventMapper {

    @Select("SELECT * FROM event")
    List<Event> getAllEvents();
}

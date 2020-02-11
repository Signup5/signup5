package se.expleostockholm.signup.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import se.expleostockholm.signup.domain.Person;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface PersonMapper {

    @Select("SELECT * FROM person WHERE id = #{person_id}")
    Optional<Person> getPersonById(@Param("person_id") Long person_id);

    @Select("SELECT * FROM person")
    List<Person> getAllPersons();
}

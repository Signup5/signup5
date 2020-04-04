package se.expleostockholm.signup.repository;

import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import se.expleostockholm.signup.domain.Person;

@Mapper
@Repository
public interface PersonMapper {

    @Select("SELECT * FROM person WHERE id = #{person_id}")
    Optional<Person> getPersonById(@Param("person_id") Long person_id);

    @Select("UPDATE person SET password = #{new_password} WHERE id = #{person_id}")
    Optional<Person> updatePasswordById(String new_password , Long person_id);

    @Select("SELECT * FROM person WHERE email = #{email}")
    Optional<Person> getPersonByEmail(String email);

    @Select("SELECT * FROM person")
    List<Person> getAllPersons();

    @Insert("INSERT INTO person (first_name, last_name, email, password) VALUES(#{first_name}, #{last_name}, #{email}, #{password})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Long savePerson(Person person);

    @Delete("DELETE FROM person WHERE email = #{email}")
    void removePersonByEmail(String email);
}

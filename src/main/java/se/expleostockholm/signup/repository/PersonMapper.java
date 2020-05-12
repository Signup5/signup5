package se.expleostockholm.signup.repository;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import se.expleostockholm.signup.domain.Person;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface PersonMapper {

    /**
     * Fetch Person by id.
     *
     * @param person_id
     * @return a Person
     */
    @Select("SELECT * FROM person WHERE id = #{person_id}")
    Optional<Person> getPersonById(@Param("person_id") Long person_id);

    /**
     * Update password for Person.
     *
     * @param new_password
     * @param person_id
     * @return
     */
    @Select("UPDATE person SET password = #{new_password} WHERE id = #{person_id}")
    Optional<Person> updatePasswordById(String new_password, Long person_id);

    /**
     * Fetch Person by email.
     *
     * @param email
     * @return a Person
     */
    @Select("SELECT * FROM person WHERE email = #{email}")
    Optional<Person> getPersonByEmail(String email);

    /**
     * Fetch all Persons.
     *
     * @return list of Persons
     */
    @Select("SELECT * FROM person")
    List<Person> getAllPersons();

    /**
     * Save Person. Returns 1 if one Person was successfully saved. 0 if no Person was saved.
     *
     * @param person
     * @return number of updated records
     */
    @Insert("INSERT INTO person (first_name, last_name, email, password) VALUES(#{first_name}, #{last_name}, #{email}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    Long savePerson(Person person);

    /**
     * Delete Person.
     *
     * @param email
     */
    @Delete("DELETE FROM person WHERE email = #{email}")
    void removePersonByEmail(String email);
}

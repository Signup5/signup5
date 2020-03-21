package se.expleostockholm.signup.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.expleostockholm.signup.domain.Person;
import se.expleostockholm.signup.exception.PersonNotFoundException;
import se.expleostockholm.signup.exception.SavePersonException;
import se.expleostockholm.signup.repository.PersonMapper;

import java.util.List;
import java.util.Optional;

import static se.expleostockholm.signup.service.ServiceUtil.isValidEmail;

@Service
@AllArgsConstructor
public class PersonService {

  private final PersonMapper personMapper;
  private final PasswordEncoder passwordEncoder;

  /**
   * Method for saving a Person.
   * <p>
   * Takes a Person as an argument and if email is valid and there is no Person already found in the database
   * with that email Person is stored in the database.
   *
   * @param person a Person to save in database if email is valid in Person doesn't already exist
   * @return return an updated Person with an Id from the database
   */
  public Person savePerson(Person person) {

    if (isValidEmail(person.getEmail())) {
      Optional<Person> optionalPerson = personMapper.getPersonByEmail(person.getEmail());

      if (optionalPerson.isEmpty()) {
        personMapper.savePerson(person);
        return person;
      } else {
        return optionalPerson.get();
      }
    }
    throw new SavePersonException("Invalid email provided!");
  }

  public List<Person> getAllPersons() {
    return personMapper.getAllPersons();
  }

  /**
   * Method for retrieving a Person based on its Id.
   * <p>
   * Accepts a Long as an argument representing the Person Id in the database.
   *
   * @param id a Long value representing a Person Id
   * @return a Person if Person Id was found in the database
   */
  public Person getPersonById(Long id) {
    return personMapper.getPersonById(id).orElseThrow(() -> new PersonNotFoundException("No person found!"));
  }

  /**
   * Method to check if a Person exists in the database.
   * <p>
   * Accepts a Person as an argument and checks if Person is found in the dataabase.
   *
   * @param person a Person to be checked if found in database
   * @return true or false depending if Person exists in database
   */
  public boolean doesPersonExist(Person person) {
    return person.getId().equals(personMapper.getPersonById(person.getId()));
  }

  /**
   * Method for retrieving a Person based on its email address.
   * <p>
   * Accepts email as a String argument to be matched with a Person in the database.
   *
   * @param email a String representing a Persons email address
   * @return a Person if email was found in the database
   */
  public Person getPersonByEmail(String email) {
    return personMapper.getPersonByEmail(email).orElseThrow(() -> new PersonNotFoundException("No person found!"));
  }

  public Person createPerson(Person person) {
    person.setPassword(passwordEncoder.encode(person.getPassword()));
    return savePerson(person);
  }
}

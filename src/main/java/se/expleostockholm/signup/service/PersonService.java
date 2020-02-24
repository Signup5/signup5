package se.expleostockholm.signup.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.expleostockholm.signup.domain.Person;
import se.expleostockholm.signup.exception.PersonNotFoundException;
import se.expleostockholm.signup.exception.SavePersonException;
import se.expleostockholm.signup.repository.PersonMapper;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static se.expleostockholm.signup.service.ServiceUtil.isValidEmail;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonMapper personMapper;

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

    public Person getPersonById(Long id) {
        return personMapper.getPersonById(id).orElseThrow(() -> new PersonNotFoundException("No person found!"));
    }

    public boolean doesPersonExist(Person person) {
        return person.getId().equals(personMapper.getPersonById(person.getId()));
    }
}
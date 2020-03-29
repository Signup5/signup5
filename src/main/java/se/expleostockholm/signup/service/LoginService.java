package se.expleostockholm.signup.service;


import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.expleostockholm.signup.domain.Person;
import se.expleostockholm.signup.exception.LoginException;
import se.expleostockholm.signup.repository.PersonMapper;

@Service
public class LoginService {

  @Autowired
  private PersonMapper userRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  public UserDetails login(String email, String loginPassword) throws LoginException {
    Person person = userRepository.getPersonByEmail(email)
        .orElseThrow(() ->
            new LoginException("Username or password incorrect"));
    if(passwordEncoder.matches(loginPassword, person.getPassword())) {
      return new User(person.getEmail(), "", new HashSet<>());
    }
     else {
      throw new LoginException("Username or password not correct");
    }
  }

  public Person logiin(String email, String loginPassword) throws LoginException {
    Person person = userRepository.getPersonByEmail(email)
        .orElseThrow(() ->
            new LoginException("Username or password incorrect"));
    if(passwordEncoder.matches(loginPassword, person.getPassword())) {
      return person;
    }
    else {
      throw new LoginException("Username or password not correct");
    }
  }
}

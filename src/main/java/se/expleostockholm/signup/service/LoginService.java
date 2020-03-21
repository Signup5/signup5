package se.expleostockholm.signup.service;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

  public UserDetails login(String email, String password) throws LoginException,
      NoSuchAlgorithmException {
    Person person = userRepository.getPersonByEmail(email)
        .orElseThrow(() ->
            new LoginException("No user exists with the given combination of productionsite and email"));
    if (verifyUser(person, "")) {
      return new User(person.getEmail(), "", new HashSet<>());
    } else {
      throw new LoginException("Username or password not correct");
    }

  }
  private boolean verifyUser(Person person, String password){
    String hashedLoginPassword = passwordEncoder.encode(password);
    if(passwordEncoder.matches(password, hashedLoginPassword)){
      return true;
    }
    return false;
  }
}

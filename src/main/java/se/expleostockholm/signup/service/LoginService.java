package se.expleostockholm.signup.service;


import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.expleostockholm.signup.domain.Person;
import se.expleostockholm.signup.exception.LoginException;
import se.expleostockholm.signup.repository.PersonMapper;

@AllArgsConstructor
@Service
public class LoginService {

    private final PersonMapper userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Fetch Person if Person has valid credentials.
     *
     * @param email
     * @param loginPassword
     * @return a Person
     * @throws LoginException
     */
    public Person login(String email, String loginPassword) throws LoginException {
        Person person = userRepository.getPersonByEmail(email)
                .orElseThrow(() ->
                        new LoginException("Username or password incorrect"));
        if (passwordEncoder.matches(loginPassword, person.getPassword())) {
            return person;
        } else {
            throw new LoginException("Username or password not correct");
        }
    }
}

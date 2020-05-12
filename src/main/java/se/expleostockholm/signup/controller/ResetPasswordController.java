package se.expleostockholm.signup.controller;

import com.sun.net.httpserver.Authenticator;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.expleostockholm.signup.domain.Person;
import se.expleostockholm.signup.domain.web.NewPasswordRequest;
import se.expleostockholm.signup.domain.web.ResetPassword;
import se.expleostockholm.signup.service.EmailService;
import se.expleostockholm.signup.service.PersonService;
import se.expleostockholm.signup.util.JwtUtil;

import javax.mail.MessagingException;

@AllArgsConstructor
@RestController
@RequestMapping("/password")
public class ResetPasswordController {

    private final PersonService personService;
    private final EmailService emailService;
    private final JwtUtil jwtUtil;

    /**
     * Method for resetting user password.
     *
     * @param resetPassword email to send reset password link to.
     * @return status if new link was sent or not
     * @throws MessagingException
     */
    @PostMapping(path = "/reset")
    public ResponseEntity<?> sendResetPasswordLink(@RequestBody ResetPassword resetPassword) throws MessagingException {
        Person person = personService.getPersonByEmail(resetPassword.getEmail());
        final String jwtToken = jwtUtil.createResetPasswordToken(person.getEmail());
        emailService.sendResetPasswordLink(person.getEmail(), jwtToken);
        return new ResponseEntity<Authenticator.Success>(HttpStatus.OK);
    }

    /**
     * Method for setting new password.
     *
     * @param newPasswordRequest  password and token
     * @return  status if setting password was successful or not
     */
    @PostMapping(path = "/new")
    public ResponseEntity<?> setNewPassword(@RequestBody NewPasswordRequest newPasswordRequest) {
        try {
            jwtUtil.isTokenExpired(newPasswordRequest.getToken());
        } catch (ExpiredJwtException e) {
            throw new JwtException("You did not reset your password in time. You need to ask for another reset your password link");
        }
        final String email = jwtUtil.extractUsername(newPasswordRequest.getToken());
        Person person = personService.getPersonByEmail(email);
        personService.changePassword(newPasswordRequest.getPassword(), person);
        return new ResponseEntity<Authenticator.Success>(HttpStatus.OK);
    }
}
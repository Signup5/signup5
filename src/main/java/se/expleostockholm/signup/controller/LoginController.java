package se.expleostockholm.signup.controller;

import java.util.HashSet;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.expleostockholm.signup.domain.Person;
import se.expleostockholm.signup.domain.web.LoginModel;
import se.expleostockholm.signup.domain.web.LoginResponse;
import se.expleostockholm.signup.exception.LoginException;
import se.expleostockholm.signup.service.LoginService;
import se.expleostockholm.signup.util.JwtUtil;

@CrossOrigin
@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class LoginController {

  private final LoginService loginService;
  private final JwtUtil jwtUtil;

  @PostMapping
  public ResponseEntity<LoginResponse> createAuthenticationToken(@RequestBody LoginModel loginModel)
      throws LoginException {
    Person person = loginService.login(loginModel.getEmail(), loginModel.getPassword());
    final String jwt = jwtUtil.generateToken(new User(person.getEmail(), "", new HashSet<>()));
    return ResponseEntity.ok(new LoginResponse(jwt, person.asPersonModel()));
  }
}


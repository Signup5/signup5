package se.expleostockholm.signup.controller;

import java.security.NoSuchAlgorithmException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import se.expleostockholm.signup.domain.web.LoginModel;
import se.expleostockholm.signup.domain.web.LoginResponse;
import se.expleostockholm.signup.exception.LoginException;
import se.expleostockholm.signup.service.LoginService;
import se.expleostockholm.signup.util.JwtUtil;

@CrossOrigin
@RestController
public class LoginController {

  @Autowired
  private LoginService loginService;


  @Autowired
  private JwtUtil jwtUtil;

  @PostMapping(value = "/login")
  public ResponseEntity<LoginResponse> createAuthenticationToken(@RequestBody LoginModel loginModel)
      throws LoginException,
      NoSuchAlgorithmException {
    UserDetails userDetails = loginService.login(loginModel.getEmail(), loginModel.getPassword());
    final String jwt = jwtUtil.generateToken(userDetails);
    return ResponseEntity.ok(new LoginResponse(jwt));
  }

}


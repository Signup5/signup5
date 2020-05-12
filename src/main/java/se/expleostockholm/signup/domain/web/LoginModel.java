package se.expleostockholm.signup.domain.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model for login.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginModel {

  private String email;
  private String password;
}

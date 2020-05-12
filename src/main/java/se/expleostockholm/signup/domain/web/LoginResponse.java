package se.expleostockholm.signup.domain.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model for login response.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
  private String jwt;
  private PersonModel person;
}

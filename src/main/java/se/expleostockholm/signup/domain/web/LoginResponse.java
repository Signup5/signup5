package se.expleostockholm.signup.domain.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.expleostockholm.signup.domain.Person;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
  private String jwt;
  private PersonModel person;
}

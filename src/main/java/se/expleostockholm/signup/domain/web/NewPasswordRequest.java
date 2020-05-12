package se.expleostockholm.signup.domain.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model for requesting new password.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewPasswordRequest {
  private String password;
  private String token;
}

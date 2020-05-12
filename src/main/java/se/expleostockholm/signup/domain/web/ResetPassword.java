package se.expleostockholm.signup.domain.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model for reset password.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPassword {
  private String email;
}

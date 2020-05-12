package se.expleostockholm.signup.domain.web;

import java.util.List;

/**
 * Model for error response.
 */
public class ErrorResponse {

  private String message;
  private List<String> details;

  public ErrorResponse(String message, List<String> details) {
    super();
    this.message = message;
    this.details = details;
  }

}
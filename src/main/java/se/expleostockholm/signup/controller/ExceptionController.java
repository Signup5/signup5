package se.expleostockholm.signup.controller;


import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import se.expleostockholm.signup.exception.LoginException;
import se.expleostockholm.signup.exception.PersonException;

@ControllerAdvice
public class ExceptionController {


  @ExceptionHandler(LoginException.class)
  public final ResponseEntity<String> handleLoginException(Exception exception, WebRequest request) {
    return error(HttpStatus.BAD_REQUEST, exception);
  }

  @ExceptionHandler(JwtException.class)
  public final ResponseEntity<String> handleJwtException(Exception exception, WebRequest request) {
    return error(HttpStatus.INTERNAL_SERVER_ERROR, exception);
  }

  @ExceptionHandler(PersonException.class)
  public final ResponseEntity<String> handlePersonNotFoundException(Exception exception, WebRequest request) {
    return error(HttpStatus.INTERNAL_SERVER_ERROR, exception);
  }


  private ResponseEntity<String> error(HttpStatus status, Exception e) {
    return ResponseEntity.status(status).body(e.getMessage());
  }


}


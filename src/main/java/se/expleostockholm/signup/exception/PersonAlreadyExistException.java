package se.expleostockholm.signup.exception;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import java.util.List;

public class PersonAlreadyExistException extends RuntimeException implements GraphQLError {

  public PersonAlreadyExistException(String message) {
    super(message);
  }

  @Override
  public List<SourceLocation> getLocations() {
    return null;
  }

  @Override
  public ErrorClassification getErrorType() {
    return null;
  }
}
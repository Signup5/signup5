package se.expleostockholm.signup.exception;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.List;

/**
 * GraphQL exception for InvalidEmail.
 */
public class InvalidEmailException extends RuntimeException implements GraphQLError {

  public InvalidEmailException(String message) {
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

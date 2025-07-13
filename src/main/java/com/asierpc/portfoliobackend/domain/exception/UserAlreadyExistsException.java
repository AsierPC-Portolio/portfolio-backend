package com.asierpc.portfoliobackend.domain.exception;

/**
 * Exception thrown when trying to register a user that already exists.
 */
public class UserAlreadyExistsException extends RuntimeException {
  public UserAlreadyExistsException() {
    super("User already exists");
  }
}

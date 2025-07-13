package com.asierpc.portfoliobackend.domain.exception;

/**
 * Exception thrown when credentials are invalid during authentication.
 */
public class InvalidCredentialsException extends RuntimeException {
  public InvalidCredentialsException() {
    super("Invalid credentials");
  }
}

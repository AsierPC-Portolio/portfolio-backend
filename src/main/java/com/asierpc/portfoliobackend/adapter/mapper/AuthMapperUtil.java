package com.asierpc.portfoliobackend.adapter.mapper;

import com.asierpc.portfoliobackend.api.model.LoginRequest;
import com.asierpc.portfoliobackend.api.model.RegisterRequest;
import com.asierpc.portfoliobackend.domain.auth.model.LoginCommand;
import com.asierpc.portfoliobackend.domain.auth.model.RegisterCommand;

/**
 * Utility class for mapping between Auth API models and domain commands.
 */
public final class AuthMapperUtil {

  private AuthMapperUtil() {
    // Utility class
  }

  /**
   * Maps a LoginRequest to a LoginCommand.
   *
   * @param loginRequest the API login request
   * @return the domain login command, or null if input is null
   */
  public static LoginCommand toLoginCommand(LoginRequest loginRequest) {
    if (loginRequest == null) {
      return null;
    }
    return LoginCommand.builder()
        .email(loginRequest.getEmail())
        .password(loginRequest.getPassword())
        .build();
  }

  /**
   * Maps a RegisterRequest to a RegisterCommand.
   *
   * @param registerRequest the API register request
   * @return the domain register command, or null if input is null
   */
  public static RegisterCommand toRegisterCommand(RegisterRequest registerRequest) {
    if (registerRequest == null) {
      return null;
    }
    return RegisterCommand.builder()
        .email(registerRequest.getEmail())
        .password(registerRequest.getPassword())
        .firstName(registerRequest.getFirstName())
        .lastName(registerRequest.getLastName())
        .build();
  }
}

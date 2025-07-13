
package com.asierpc.portfoliobackend.adapter.rest;

import com.asierpc.portfoliobackend.adapter.mapper.AuthMapperUtil;
import com.asierpc.portfoliobackend.api.AuthenticationApi;
import com.asierpc.portfoliobackend.api.model.AuthResponse;
import com.asierpc.portfoliobackend.api.model.LoginRequest;
import com.asierpc.portfoliobackend.api.model.RegisterRequest;
import com.asierpc.portfoliobackend.domain.auth.AuthServicePort;
import com.asierpc.portfoliobackend.domain.auth.model.LoginCommand;
import com.asierpc.portfoliobackend.domain.auth.model.RegisterCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for authentication endpoints (login, register).
 */
@RestController
@RequiredArgsConstructor
public class AuthController implements AuthenticationApi {

  private final AuthServicePort authService;

  @Override
  public ResponseEntity<AuthResponse> loginUser(LoginRequest loginRequest) {
    LoginCommand command = AuthMapperUtil.toLoginCommand(loginRequest);
    String token = authService.login(command);
    AuthResponse response = new AuthResponse();
    response.setToken(token);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<AuthResponse> registerUser(RegisterRequest registerRequest) {
    RegisterCommand command = AuthMapperUtil.toRegisterCommand(registerRequest);
    String token = authService.register(command);
    AuthResponse response = new AuthResponse();
    response.setToken(token);
    return ResponseEntity.ok(response);
  }
}

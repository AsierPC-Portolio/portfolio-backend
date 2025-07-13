
package com.asierpc.portfoliobackend.adapter.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asierpc.portfoliobackend.adapter.mapper.AuthMapper;
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

  private static final Logger log = LoggerFactory.getLogger(AuthController.class);

  private final AuthServicePort authService;
  private final AuthMapper authMapper;

  @Override
  public ResponseEntity<AuthResponse> loginUser(LoginRequest loginRequest) {
    log.info("[LOGIN] Petición recibida: {}", loginRequest);
    try {
      LoginCommand command = authMapper.toLoginCommand(loginRequest);
      log.debug("[LOGIN] LoginCommand: {}", command);
      String token = authService.login(command);
      log.info("[LOGIN] Token generado correctamente");
      AuthResponse response = new AuthResponse();
      response.setToken(token);
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      log.error("[LOGIN] Error en loginUser: {}", e.getMessage(), e);
      throw e;
    }
  }

  @Override
  public ResponseEntity<AuthResponse> registerUser(RegisterRequest registerRequest) {
    log.info("[REGISTER] Petición recibida: {}", registerRequest);
    try {
      RegisterCommand command = authMapper.toRegisterCommand(registerRequest);
      log.debug("[REGISTER] RegisterCommand: {}", command);
      String token = authService.register(command);
      log.info("[REGISTER] Token generado correctamente");
      AuthResponse response = new AuthResponse();
      response.setToken(token);
      return ResponseEntity.status(201).body(response);
    } catch (Exception e) {
      log.error("[REGISTER] Error en registerUser: {}", e.getMessage(), e);
      throw e;
    }
  }
}

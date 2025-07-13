package com.asierpc.portfoliobackend.adapter.rest;

import com.asierpc.portfoliobackend.domain.auth.AuthServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.asierpc.portfoliobackend.api.model.AuthResponse;
import com.asierpc.portfoliobackend.api.model.LoginRequest;
import com.asierpc.portfoliobackend.api.model.RegisterRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.springframework.http.ResponseEntity;

class AuthControllerTest {
  private AuthServicePort authService;
  private AuthController authController;

  @BeforeEach
  void setUp() {
    authService = mock(AuthServicePort.class);
    authController = new AuthController(authService);
  }

  @Test
  void loginUser_returnsOkWithToken() {
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setEmail("test@example.com");
    loginRequest.setPassword("pass");
    when(authService.login(any())).thenReturn("token123");

    ResponseEntity<AuthResponse> response = authController.loginUser(loginRequest);
    assertEquals(200, response.getStatusCode().value());
    assertNotNull(response.getBody());
    assertEquals("token123", response.getBody().getToken());
  }
  @Test
  void registerUser_returnsCreatedWithToken() {
    RegisterRequest registerRequest = new RegisterRequest();
    registerRequest.setEmail("test@example.com");
    registerRequest.setPassword("pass");
    registerRequest.setFirstName("Test");
    registerRequest.setLastName("User");
    when(authService.register(any())).thenReturn("token456");

    ResponseEntity<AuthResponse> response = authController.registerUser(registerRequest);
    assertEquals(200, response.getStatusCode().value());
    assertNotNull(response.getBody());
    assertEquals("token456", response.getBody().getToken());
  }
}

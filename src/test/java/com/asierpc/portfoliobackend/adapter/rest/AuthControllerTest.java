package com.asierpc.portfoliobackend.adapter.rest;

import com.asierpc.portfoliobackend.adapter.mapper.AuthMapper;
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
    private AuthMapper authMapper;
    private AuthController authController;

    @BeforeEach
    void setUp() {
        authService = mock(AuthServicePort.class);
        authMapper = mock(AuthMapper.class);
        authController = new AuthController(authService, authMapper);
    }

    @Test
    void loginUser_returnsOkWithToken() {
        LoginRequest loginRequest = mock(LoginRequest.class);
        com.asierpc.portfoliobackend.domain.auth.model.LoginCommand command = mock(com.asierpc.portfoliobackend.domain.auth.model.LoginCommand.class);
        when(authMapper.toLoginCommand(loginRequest)).thenReturn(command);
        when(authService.login(command)).thenReturn("token123");

        ResponseEntity<AuthResponse> response = authController.loginUser(loginRequest);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("token123", response.getBody().getToken());
    }
    @Test
    void registerUser_returnsCreatedWithToken() {
        RegisterRequest registerRequest = mock(RegisterRequest.class);
        com.asierpc.portfoliobackend.domain.auth.model.RegisterCommand command = mock(com.asierpc.portfoliobackend.domain.auth.model.RegisterCommand.class);
        when(authMapper.toRegisterCommand(registerRequest)).thenReturn(command);
        when(authService.register(command)).thenReturn("token456");

        ResponseEntity<AuthResponse> response = authController.registerUser(registerRequest);
        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("token456", response.getBody().getToken());
    }
}

package com.asierpc.portfoliobackend.application.auth;

import com.asierpc.portfoliobackend.domain.auth.model.LoginCommand;
import com.asierpc.portfoliobackend.domain.auth.model.RegisterCommand;
import com.asierpc.portfoliobackend.domain.exception.InvalidCredentialsException;
import com.asierpc.portfoliobackend.domain.exception.UserAlreadyExistsException;
import com.asierpc.portfoliobackend.domain.user.User;
import com.asierpc.portfoliobackend.infrastructure.repository.UserRepository;
import com.asierpc.portfoliobackend.infrastructure.security.JwtUtil;
import com.asierpc.portfoliobackend.infrastructure.security.PasswordEncoderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {
    private JwtUtil jwtUtil;
    private PasswordEncoderService passwordEncoderService;
    private UserRepository userRepository;
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        jwtUtil = mock(JwtUtil.class);
        passwordEncoderService = mock(PasswordEncoderService.class);
        userRepository = mock(UserRepository.class);
        authService = new AuthServiceImpl(jwtUtil, passwordEncoderService, userRepository);
    }

    @Test
    void login_invalidCredentials_throwsException() {
        LoginCommand cmd = mock(LoginCommand.class);
        when(cmd.getEmail()).thenReturn("user@example.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        assertThrows(InvalidCredentialsException.class, () -> authService.login(cmd));
    }

    @Test
    void login_wrongPassword_throwsException() {
        LoginCommand cmd = mock(LoginCommand.class);
        when(cmd.getEmail()).thenReturn("user@example.com");
        when(cmd.getPassword()).thenReturn("wrong");
        User user = User.builder().email("user@example.com").password("encoded").build();
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(passwordEncoderService.matches(anyString(), anyString())).thenReturn(false);
        assertThrows(InvalidCredentialsException.class, () -> authService.login(cmd));
    }

    @Test
    void register_nullEmail_throwsException() {
        RegisterCommand cmd = mock(RegisterCommand.class);
        when(cmd.getEmail()).thenReturn(null);
        assertThrows(IllegalArgumentException.class, () -> authService.register(cmd));
    }

    @Test
    void register_blankEmail_throwsException() {
        RegisterCommand cmd = mock(RegisterCommand.class);
        when(cmd.getEmail()).thenReturn("   ");
        assertThrows(IllegalArgumentException.class, () -> authService.register(cmd));
    }

    @Test
    void register_userAlreadyExists_throwsException() {
        RegisterCommand cmd = mock(RegisterCommand.class);
        when(cmd.getEmail()).thenReturn("user@example.com");
        when(userRepository.existsByEmail(anyString())).thenReturn(true);
        assertThrows(UserAlreadyExistsException.class, () -> authService.register(cmd));
    }
    @Test
    void register_validUser_savesAndReturnsToken() {
        RegisterCommand cmd = mock(RegisterCommand.class);
        when(cmd.getEmail()).thenReturn("user@example.com");
        when(cmd.getPassword()).thenReturn("rawpass");
        when(cmd.getFirstName()).thenReturn("John");
        when(cmd.getLastName()).thenReturn("Doe");
        when(userRepository.existsByEmail("user@example.com")).thenReturn(false);
        when(passwordEncoderService.encode("rawpass")).thenReturn("encodedpass");
        when(jwtUtil.generateToken("user@example.com")).thenReturn("token123");

        String token = authService.register(cmd);

        assertEquals("token123", token);
        verify(userRepository).save(argThat(user ->
            user.getEmail().equals("user@example.com") &&
            user.getPassword().equals("encodedpass") &&
            user.getFirstName().equals("John") &&
            user.getLastName().equals("Doe")
        ));
    }
}

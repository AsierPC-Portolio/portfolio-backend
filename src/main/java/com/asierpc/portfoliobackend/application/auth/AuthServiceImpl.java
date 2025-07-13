package com.asierpc.portfoliobackend.application.auth;

import com.asierpc.portfoliobackend.domain.auth.AuthServicePort;
import com.asierpc.portfoliobackend.domain.exception.InvalidCredentialsException;
import com.asierpc.portfoliobackend.domain.exception.UserAlreadyExistsException;
import com.asierpc.portfoliobackend.domain.user.User;
import com.asierpc.portfoliobackend.infrastructure.repository.UserRepository;
import com.asierpc.portfoliobackend.infrastructure.security.JwtUtil;
import com.asierpc.portfoliobackend.infrastructure.security.PasswordEncoderService;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for authentication logic (login, register).
 */
@Service
public class AuthServiceImpl implements AuthServicePort {

  private final JwtUtil jwtUtil;
  private final PasswordEncoderService passwordEncoderService;
  private final UserRepository userRepository;

  /**
   * Constructs the AuthServiceImpl with required dependencies.
   *
   * @param jwtUtil the JWT utility for token operations
   * @param passwordEncoderService the password encoder service
   * @param userRepository the user repository
   */
  public AuthServiceImpl(
      JwtUtil jwtUtil,
      PasswordEncoderService passwordEncoderService,
      UserRepository userRepository) {
    this.jwtUtil = jwtUtil;
    this.passwordEncoderService = passwordEncoderService;
    this.userRepository = userRepository;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional(readOnly = true)
  public String login(com.asierpc.portfoliobackend.domain.auth.model.LoginCommand command) {
    Optional<User> userOpt = userRepository.findByEmail(command.getEmail());
    if (userOpt.isEmpty()
        || !passwordEncoderService.matches(command.getPassword(), userOpt.get().getPassword())) {
      throw new InvalidCredentialsException();
    }
    return jwtUtil.generateToken(userOpt.get().getEmail());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public String register(com.asierpc.portfoliobackend.domain.auth.model.RegisterCommand command) {
    String email = command.getEmail();
    if (email == null || email.isBlank()) {
      throw new IllegalArgumentException("Email must not be null or blank");
    }
    if (userRepository.existsByEmail(email)) {
      throw new UserAlreadyExistsException();
    }
    User user =
        User.builder()
          .email(email)
          .password(passwordEncoderService.encode(command.getPassword()))
          .firstName(command.getFirstName())
          .lastName(command.getLastName())
        .build();
    userRepository.save(user);
    return jwtUtil.generateToken(user.getEmail());
  }
}

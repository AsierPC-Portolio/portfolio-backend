
package com.asierpc.portfoliobackend.infrastructure.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service for encoding and verifying user passwords using BCrypt.
 */
@Service
public class PasswordEncoderService {
  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  public String encode(String rawPassword) {
    return passwordEncoder.encode(rawPassword);
  }

  public boolean matches(String rawPassword, String encodedPassword) {
    return passwordEncoder.matches(rawPassword, encodedPassword);
  }
}

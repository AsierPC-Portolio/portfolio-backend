package com.asierpc.portfoliobackend.infrastructure.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PasswordEncoderServiceTest {
  private PasswordEncoderService passwordEncoderService;

  @BeforeEach
  void setUp() {
    passwordEncoderService = new PasswordEncoderService();
  }

  @Test
  void encodeAndMatches() {
    String rawPassword = "mySecret123";
    String encoded = passwordEncoderService.encode(rawPassword);
    assertNotNull(encoded);
    assertNotEquals(rawPassword, encoded);
    assertTrue(passwordEncoderService.matches(rawPassword, encoded));
  }

  @Test
  void doesNotMatchWrongPassword() {
    String rawPassword = "mySecret123";
    String encoded = passwordEncoderService.encode(rawPassword);
    assertFalse(passwordEncoderService.matches("wrongPassword", encoded));
  }
}

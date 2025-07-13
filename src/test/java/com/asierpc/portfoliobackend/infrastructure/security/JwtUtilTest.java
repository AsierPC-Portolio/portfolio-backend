package com.asierpc.portfoliobackend.infrastructure.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for JwtUtil utility class.
 */
class JwtUtilTest {
  private JwtUtil jwtUtil;


  @BeforeEach
  void setUp() throws Exception {
    jwtUtil = new JwtUtil();
    // Set the secret field via reflection
    java.lang.reflect.Field secretField = JwtUtil.class.getDeclaredField("secret");
    secretField.setAccessible(true);
    secretField.set(jwtUtil, "testsecretkeytestsecretkeytestsecretkey12");
    // Call initKey manually
    java.lang.reflect.Method initKey = JwtUtil.class.getDeclaredMethod("initKey");
    initKey.setAccessible(true);
    initKey.invoke(jwtUtil);
  }

  @Test
  void generateAndValidateToken() {
    String username = "testuser";
    String token = jwtUtil.generateToken(username);
    assertNotNull(token);
    assertEquals(username, jwtUtil.extractUsername(token));
    assertTrue(jwtUtil.validateToken(token, username));
  }

  @Test
  void validateToken_withValidToken_returnsTrue() {
    String username = "validuser";
    String token = jwtUtil.generateToken(username);
    assertTrue(jwtUtil.validateToken(token, username));
  }

  @Test
  void validateToken_withInvalidUsername_returnsFalse() {
    String username = "user1";
    String token = jwtUtil.generateToken(username);
    assertFalse(jwtUtil.validateToken(token, "differentuser"));
  }

  @Test
  void validateToken_withMalformedToken_returnsFalseOrThrows() {
    String malformedToken = "this.is.not.a.valid.token";
    String username = "anyuser";
    assertThrows(Exception.class, () -> jwtUtil.validateToken(malformedToken, username));
  }
}

package com.asierpc.portfoliobackend.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * Utility class for generating and validating JWT tokens.
 */
@Component
public class JwtUtil {

  @Value("${jwt.secret}")
  private String secret;

  private SecretKey key;

  private static final long JWT_EXPIRATION_MS = 86400000; // 1 day


  /**
   * Initializes the secret key for JWT signing after dependency injection.
   */
  @PostConstruct
  private void initKey() {
    this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
  }

  /**
   * Generates a JWT token for the given username.
   *
   * @param username the username to include in the token subject
   * @return the generated JWT token as a String
   */
  public String generateToken(String username) {
    return Jwts.builder()
        .subject(username)
        .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_MS))
        .signWith(key)
        .compact();
  }

  public boolean validateToken(String token, String username) {
    final String extractedUsername = extractUsername(token);
    return (extractedUsername.equals(username) && !isTokenExpired(token));
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }
}


package com.asierpc.portfoliobackend.infrastructure.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Spring Security configuration for JWT-based authentication and authorization.
 */
@Configuration
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  public SecurityConfig(
      @Value("${cors.allowed-origins}") String[] allowedOrigins,
      JwtAuthenticationFilter jwtAuthenticationFilter
  ) {
    this.allowedOrigins = allowedOrigins;
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
  }

  @Value("${cors.allowed-origins}")
  private String[] allowedOrigins;


  
  /**
   * Configures the security filter chain for HTTP requests.
   *
   * @param http the HttpSecurity to modify
   * @return the configured SecurityFilterChain
   * @throws Exception if a security configuration error occurs
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/auth/login", "/auth/register").permitAll()
            .requestMatchers(
                "api-docs/**", "/swagger-ui/**", "/v3/api-docs/**"
            ).permitAll()
            .anyRequest().authenticated()
        )
        .cors(cors -> cors.configurationSource(corsConfigurationSource()));
    http.addFilterBefore(
        jwtAuthenticationFilter,
        UsernamePasswordAuthenticationFilter.class
    );
    return http.build();
  }

  /**
   * Configures CORS settings for the application.
   *
   * @return the configured CorsConfigurationSource
   */
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    if (allowedOrigins != null) {
      for (String origin : allowedOrigins) {
        configuration.addAllowedOrigin(origin);
      }
    }
    configuration.addAllowedMethod("*");
    configuration.addAllowedHeader("*");
    configuration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}

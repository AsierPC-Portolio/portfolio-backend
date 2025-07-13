package com.asierpc.portfoliobackend.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {
  private JwtUtil jwtUtil;
  private UserDetailsService userDetailsService;
  private JwtAuthenticationFilter filter;
  private HttpServletRequest request;
  private HttpServletResponse response;
  private FilterChain filterChain;

  @BeforeEach
  void setUp() {
    jwtUtil = mock(JwtUtil.class);
    userDetailsService = mock(UserDetailsService.class);
    filter = new JwtAuthenticationFilter(jwtUtil, userDetailsService);
    request = mock(HttpServletRequest.class);
    response = mock(HttpServletResponse.class);
    filterChain = mock(FilterChain.class);
    SecurityContextHolder.clearContext();
  }

  @Test
  void doFilterInternal_validToken_setsAuthentication() throws ServletException, IOException {
    when(request.getHeader("Authorization")).thenReturn("Bearer valid.jwt.token");
    when(jwtUtil.extractUsername("valid.jwt.token")).thenReturn("user@example.com");
    UserDetails userDetails = mock(UserDetails.class);
    when(userDetails.getUsername()).thenReturn("user@example.com");
    when(userDetails.getAuthorities()).thenReturn(java.util.Collections.emptyList());
    when(userDetailsService.loadUserByUsername("user@example.com")).thenReturn(userDetails);
    when(jwtUtil.validateToken("valid.jwt.token", "user@example.com")).thenReturn(true);

    filter.doFilterInternal(request, response, filterChain);

    assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    assertEquals(userDetails, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    verify(filterChain).doFilter(request, response);
  }

  @Test
  void doFilterInternal_invalidToken_doesNotSetAuthentication() throws ServletException, IOException {
    when(request.getHeader("Authorization")).thenReturn("Bearer invalid.jwt.token");
    when(jwtUtil.extractUsername("invalid.jwt.token")).thenReturn("user@example.com");
    UserDetails userDetails = mock(UserDetails.class);
    when(userDetails.getUsername()).thenReturn("user@example.com");
    when(userDetailsService.loadUserByUsername("user@example.com")).thenReturn(userDetails);
    when(jwtUtil.validateToken("invalid.jwt.token", "user@example.com")).thenReturn(false);

    filter.doFilterInternal(request, response, filterChain);

    assertNull(SecurityContextHolder.getContext().getAuthentication());
    verify(filterChain).doFilter(request, response);
  }

  @Test
  void doFilterInternal_noAuthHeader_doesNotSetAuthentication() throws ServletException, IOException {
    when(request.getHeader("Authorization")).thenReturn(null);

    filter.doFilterInternal(request, response, filterChain);

    assertNull(SecurityContextHolder.getContext().getAuthentication());
    verify(filterChain).doFilter(request, response);
  }
}

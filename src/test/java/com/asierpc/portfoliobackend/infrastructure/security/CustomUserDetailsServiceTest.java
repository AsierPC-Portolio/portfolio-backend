package com.asierpc.portfoliobackend.infrastructure.security;

import com.asierpc.portfoliobackend.domain.user.User;
import com.asierpc.portfoliobackend.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {
  @Test
  void loadUserByUsername_returnsCustomUserDetails() {
    UserRepository repo = mock(UserRepository.class);
    User user = new User();
    user.setEmail("mail@a.com");
    user.setPassword("pw");
    when(repo.findByEmail("mail@a.com")).thenReturn(Optional.of(user));
    CustomUserDetailsService service = new CustomUserDetailsService(repo);

    UserDetails details = service.loadUserByUsername("mail@a.com");
    assertTrue(details instanceof CustomUserDetails);
    assertEquals("mail@a.com", details.getUsername());
    assertEquals("pw", details.getPassword());
  }

  @Test
  void loadUserByUsername_notFound_throws() {
    UserRepository repo = mock(UserRepository.class);
    when(repo.findByEmail("no@no.com")).thenReturn(Optional.empty());
    CustomUserDetailsService service = new CustomUserDetailsService(repo);

    assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("no@no.com"));
  }
}

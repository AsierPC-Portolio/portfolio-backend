package com.asierpc.portfoliobackend.infrastructure.security;

import com.asierpc.portfoliobackend.domain.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class CustomUserDetailsTest {
  @Test
  void wrapsUser_and_exposes_fields() {
    User user = new User();
    user.setEmail("mail@a.com");
    user.setPassword("pw");
    CustomUserDetails cud = new CustomUserDetails(user);

    assertEquals(user, cud.getUser());
    assertEquals("mail@a.com", cud.getUsername());
    assertEquals("pw", cud.getPassword());
    assertTrue(cud.isAccountNonExpired());
    assertTrue(cud.isAccountNonLocked());
    assertTrue(cud.isCredentialsNonExpired());
    assertTrue(cud.isEnabled());
  }

  @Test
  void getAuthorities_returnsEmptyList() {
    User user = new User();
    CustomUserDetails cud = new CustomUserDetails(user);
    Collection<? extends GrantedAuthority> authorities = cud.getAuthorities();
    assertNotNull(authorities);
    assertTrue(authorities.isEmpty());
  }
}

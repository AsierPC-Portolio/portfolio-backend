package com.asierpc.portfoliobackend.domain.auth.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginCommandTest {
  @Test
  void equals_hashCode_toString_work() {
    LoginCommand a = LoginCommand.builder().email("a@a.com").password("x").build();
    LoginCommand b = LoginCommand.builder().email("a@a.com").password("x").build();
    LoginCommand c = LoginCommand.builder().email("b@b.com").password("y").build();

    assertEquals(a, b);
    assertEquals(a.hashCode(), b.hashCode());
    assertNotEquals(a, c);
    assertNotEquals(a.hashCode(), c.hashCode());

    String str = a.toString();
    assertTrue(str.contains("a@a.com"));
    assertTrue(str.contains("x"));
  }
  @Test
  void builder_and_lombok_annotations_work() {
    LoginCommand cmd = LoginCommand.builder()
        .email("test@example.com")
        .password("pass")
        .build();
    assertEquals("test@example.com", cmd.getEmail());
    assertEquals("pass", cmd.getPassword());

    // Test setters/getters
    cmd.setEmail("other@example.com");
    assertEquals("other@example.com", cmd.getEmail());
    cmd.setPassword("other");
    assertEquals("other", cmd.getPassword());

    // Test no-args constructor
    LoginCommand empty = new LoginCommand();
    empty.setEmail("a");
    empty.setPassword("b");
    assertEquals("a", empty.getEmail());
    assertEquals("b", empty.getPassword());
  }

  @Test
  void allArgsConstructor_works() {
    LoginCommand cmd = new LoginCommand("mail", "pw");
    assertEquals("mail", cmd.getEmail());
    assertEquals("pw", cmd.getPassword());
  }
}

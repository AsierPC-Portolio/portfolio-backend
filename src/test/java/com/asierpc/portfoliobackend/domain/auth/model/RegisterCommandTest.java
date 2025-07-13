package com.asierpc.portfoliobackend.domain.auth.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterCommandTest {
  @Test
  void builder_and_lombok_annotations_work() {
    RegisterCommand cmd = RegisterCommand.builder()
        .email("test@example.com")
        .password("pass")
        .firstName("Test")
        .lastName("User")
        .build();
    assertEquals("test@example.com", cmd.getEmail());
    assertEquals("pass", cmd.getPassword());
    assertEquals("Test", cmd.getFirstName());
    assertEquals("User", cmd.getLastName());

    // Test setters/getters
    cmd.setEmail("other@example.com");
    assertEquals("other@example.com", cmd.getEmail());
    cmd.setPassword("other");
    assertEquals("other", cmd.getPassword());
    cmd.setFirstName("A");
    assertEquals("A", cmd.getFirstName());
    cmd.setLastName("B");
    assertEquals("B", cmd.getLastName());

    // Test no-args constructor
    RegisterCommand empty = new RegisterCommand();
    empty.setEmail("a");
    empty.setPassword("b");
    empty.setFirstName("c");
    empty.setLastName("d");
    assertEquals("a", empty.getEmail());
    assertEquals("b", empty.getPassword());
    assertEquals("c", empty.getFirstName());
    assertEquals("d", empty.getLastName());
  }

  @Test
  void allArgsConstructor_works() {
    RegisterCommand cmd = new RegisterCommand("mail", "pw", "fn", "ln");
    assertEquals("mail", cmd.getEmail());
    assertEquals("pw", cmd.getPassword());
    assertEquals("fn", cmd.getFirstName());
    assertEquals("ln", cmd.getLastName());
  }

  @Test
  void equals_hashCode_toString_work() {
    RegisterCommand a = RegisterCommand.builder().email("a@a.com").password("x").firstName("f").lastName("l").build();
    RegisterCommand b = RegisterCommand.builder().email("a@a.com").password("x").firstName("f").lastName("l").build();
    RegisterCommand c = RegisterCommand.builder().email("b@b.com").password("y").firstName("g").lastName("h").build();

    assertEquals(a, b);
    assertEquals(a.hashCode(), b.hashCode());
    assertNotEquals(a, c);
    assertNotEquals(a.hashCode(), c.hashCode());

    String str = a.toString();
    assertTrue(str.contains("a@a.com"));
    assertTrue(str.contains("x"));
    assertTrue(str.contains("f"));
    assertTrue(str.contains("l"));
  }
}

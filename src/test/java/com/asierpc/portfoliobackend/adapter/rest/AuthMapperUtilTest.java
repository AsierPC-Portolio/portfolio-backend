package com.asierpc.portfoliobackend.adapter.rest;

import com.asierpc.portfoliobackend.adapter.mapper.AuthMapperUtil;
import com.asierpc.portfoliobackend.api.model.LoginRequest;
import com.asierpc.portfoliobackend.api.model.RegisterRequest;
import com.asierpc.portfoliobackend.domain.auth.model.LoginCommand;
import com.asierpc.portfoliobackend.domain.auth.model.RegisterCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthMapperUtilTest {
  @Test
  void toLoginCommand_mapsCorrectly() {
    LoginRequest req = new LoginRequest();
    req.setEmail("test@example.com");
    req.setPassword("pass");
    LoginCommand cmd = AuthMapperUtil.toLoginCommand(req);
    assertEquals("test@example.com", cmd.getEmail());
    assertEquals("pass", cmd.getPassword());
  }

  @Test
  void toRegisterCommand_mapsCorrectly() {
    RegisterRequest req = new RegisterRequest();
    req.setEmail("test@example.com");
    req.setPassword("pass");
    req.setFirstName("Test");
    req.setLastName("User");
    RegisterCommand cmd = AuthMapperUtil.toRegisterCommand(req);
    assertEquals("test@example.com", cmd.getEmail());
    assertEquals("pass", cmd.getPassword());
    assertEquals("Test", cmd.getFirstName());
    assertEquals("User", cmd.getLastName());
  }

  @Test
  void nullSafe() {
    assertNull(AuthMapperUtil.toLoginCommand(null));
    assertNull(AuthMapperUtil.toRegisterCommand(null));
  }
}

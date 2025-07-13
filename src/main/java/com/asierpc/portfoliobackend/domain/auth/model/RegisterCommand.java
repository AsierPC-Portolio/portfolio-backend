package com.asierpc.portfoliobackend.domain.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Command object for register requests.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterCommand {
  private String email;
  private String password;
  private String firstName;
  private String lastName;
}

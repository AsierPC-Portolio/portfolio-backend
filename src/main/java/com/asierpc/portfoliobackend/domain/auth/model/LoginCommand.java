package com.asierpc.portfoliobackend.domain.auth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Command object for login requests.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginCommand {
  private String email;
  private String password;
}

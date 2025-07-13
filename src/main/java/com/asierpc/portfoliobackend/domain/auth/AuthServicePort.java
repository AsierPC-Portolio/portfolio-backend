package com.asierpc.portfoliobackend.domain.auth;

import com.asierpc.portfoliobackend.domain.auth.model.LoginCommand;
import com.asierpc.portfoliobackend.domain.auth.model.RegisterCommand;

/**
 * Port for authentication use cases (Hexagonal Architecture).
 * Implemented in application/service layer.
 */
public interface AuthServicePort {

  String login(LoginCommand command);

  String register(RegisterCommand command);

}

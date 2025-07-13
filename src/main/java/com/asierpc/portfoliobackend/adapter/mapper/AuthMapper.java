package com.asierpc.portfoliobackend.adapter.mapper;

import com.asierpc.portfoliobackend.api.model.LoginRequest;
import com.asierpc.portfoliobackend.api.model.RegisterRequest;
import com.asierpc.portfoliobackend.domain.auth.model.LoginCommand;
import com.asierpc.portfoliobackend.domain.auth.model.RegisterCommand;
import org.mapstruct.Mapper;

/**
 * Mapper interface for authentication-related DTOs and domain commands.
 * Provides conversion methods for login and register requests.
 */
@Mapper(componentModel = "spring")
public interface AuthMapper {

  LoginCommand toLoginCommand(LoginRequest loginRequest);

  RegisterCommand toRegisterCommand(RegisterRequest registerRequest);

}

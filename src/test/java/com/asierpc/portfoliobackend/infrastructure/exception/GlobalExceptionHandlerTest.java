
package com.asierpc.portfoliobackend.infrastructure.exception;

import com.asierpc.portfoliobackend.domain.exception.InvalidCredentialsException;
import com.asierpc.portfoliobackend.domain.exception.UserAlreadyExistsException;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    @Test
    void handleGeneric_returnsInternalServerError() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        Exception ex = new Exception("Some error");
        ResponseEntity<Object> response = handler.handleGeneric(ex);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        Map<?,?> body = (Map<?,?>) response.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), body.get("status"));
        assertEquals("Unexpected error", body.get("message"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), body.get("error"));
        assertNotNull(body.get("timestamp"));
    }

    @Test
    void handleInvalidCredentials_returnsUnauthorized() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        InvalidCredentialsException ex = new InvalidCredentialsException();
        ResponseEntity<Object> response = handler.handleInvalidCredentials(ex);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        Map<?,?> body = (Map<?,?>) response.getBody();
        assertEquals(HttpStatus.UNAUTHORIZED.value(), body.get("status"));
        assertEquals("Invalid credentials", body.get("message"));
        assertEquals(HttpStatus.UNAUTHORIZED.getReasonPhrase(), body.get("error"));
        assertNotNull(body.get("timestamp"));
    }

    @Test
    void handleUserAlreadyExists_returnsConflict() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        UserAlreadyExistsException ex = new UserAlreadyExistsException();
        ResponseEntity<Object> response = handler.handleUserAlreadyExists(ex);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        Map<?,?> body = (Map<?,?>) response.getBody();
        assertEquals(HttpStatus.CONFLICT.value(), body.get("status"));
        assertEquals("User already exists", body.get("message"));
        assertEquals(HttpStatus.CONFLICT.getReasonPhrase(), body.get("error"));
        assertNotNull(body.get("timestamp"));
    }
}

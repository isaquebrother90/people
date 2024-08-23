package com.cadastra.people.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.nio.file.AccessDeniedException;
import java.util.Date;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<MessageExceptionHandler> handleNotPermittedException(AuthenticationException e,
                                                                               WebRequest request) {
        log.error("Not permitted access.", e);
        String type = URI.create("http://localhost:8080").toString();
        String title = "Not permitted access";
        String detail = e.getMessage();
        if (detail == null) detail = e.toString();
        String instance = request.getDescription(false).substring(4);

        MessageExceptionHandler error = new MessageExceptionHandler(type, title, HttpStatus.FORBIDDEN.value(),
                "Ops: Not permitted: " + detail, instance, new Date());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public ResponseEntity<MessageExceptionHandler> handleUnauthorizedException(AccessDeniedException e,
                                                                               WebRequest request) {
        log.error("Unauthorized access.", e);
        String type = URI.create("http://localhost:8080").toString();
        String title = "Unauthorized";
        String detail = e.getMessage();
        if (detail == null) detail = e.toString();
        String instance = request.getDescription(false).substring(4);

        MessageExceptionHandler error = new MessageExceptionHandler(type, title, HttpStatus.UNAUTHORIZED.value(),
                "Ops: Unauthorized, sorry.: " + detail, instance, new Date());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageExceptionHandler> handleAllUncaughtException(Exception e, WebRequest request) {
        log.error("An unknown error has occurred.", e);
        String type = URI.create("http://localhost:8080").toString();
        String title = "Internal Server Error";
        String detail = e.getMessage();
        if (detail == null) detail = e.toString();
        String instance = request.getDescription(false).substring(4);

        MessageExceptionHandler error = new MessageExceptionHandler(type, title, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred: " + detail, instance, new Date());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<MessageExceptionHandler> handleAllUncaughtException(BadCredentialsException e,
                                                                              WebRequest request) {
        log.error("Bad credential was detected.", e);
        String type = URI.create("http://localhost:8080").toString();
        String title = "Bad credentials";
        String detail = e.getMessage();
        if (detail == null) detail = e.toString();
        String instance = request.getDescription(false).substring(4);

        MessageExceptionHandler error = new MessageExceptionHandler(type, title, HttpStatus.UNAUTHORIZED.value(),
                "Invalid credentials: " + detail, instance, new Date());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<MessageExceptionHandler> handleAddressNotFoundException(AddressNotFoundException e,
                                                                              WebRequest request) {
        log.error("Erro ao buscar endereço", e);
        String type = URI.create("http://localhost:8080").toString();
        String title = "Erro ao buscar CEP";
        String detail = e.getMessage();
        if (detail == null) detail = e.toString();
        String instance = request.getDescription(false).substring(4);

        MessageExceptionHandler error = new MessageExceptionHandler(type, title, HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "CEP inválido ou não encontrado: " + detail, instance, new Date());
        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}

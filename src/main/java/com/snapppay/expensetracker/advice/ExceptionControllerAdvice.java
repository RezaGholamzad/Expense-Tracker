package com.snapppay.expensetracker.advice;

import com.snapppay.expensetracker.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<FailureResponse> badRequest(BadRequestException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new FailureResponse(ex.getClass().getName(), ex.getMessage()));
    }

    @ExceptionHandler(exception = {InvalidPasswordOrUsernameException.class, UnAuthorizedException.class, AuthenticationException.class})
    public ResponseEntity<FailureResponse> invalidUserOrPassword(Exception ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new FailureResponse(ex.getClass().getName(), ex.getMessage()));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<FailureResponse> handleAllExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new FailureResponse(ExpenseTrackerRuntimeException.class.getSimpleName(), ex.getMessage()));
    }
}

package com.restapi.advice;

import com.restapi.exception.ErrorMessage;
import com.restapi.exception.ResourceNotFoundException;
import com.restapi.exception.TokenRefreshException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class ExceptionHandlerController {

   @ExceptionHandler(ResourceNotFoundException.class)
   @ResponseStatus(value = HttpStatus.NOT_FOUND)
   public ErrorMessage resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {

      return new ErrorMessage(
            HttpStatus.NOT_FOUND.value(),
            new Date(),
            ex.getMessage(),
            request.getDescription(false));
   }

   @ExceptionHandler(RuntimeException.class)
   @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
   public ErrorMessage globalExceptionHandler(RuntimeException ex, WebRequest request) {

      return new ErrorMessage(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            new Date(),
            ex.getMessage(),
            request.getDescription(false));
   }

   @ExceptionHandler(value = TokenRefreshException.class)
   @ResponseStatus(HttpStatus.FORBIDDEN)
   public ErrorMessage handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
      return new ErrorMessage(
            HttpStatus.FORBIDDEN.value(),
            new Date(),
            ex.getMessage(),
            request.getDescription(false));
   }
}

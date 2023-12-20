package com.restapi.advice;

import com.restapi.exception.ErrorMessage;
import com.restapi.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerController {
   @ExceptionHandler(ResourceNotFoundException.class)
   public ResponseEntity<Map<String, String>> resourceNotFoundException(ResourceNotFoundException e) {
      Map<String, String> map = new HashMap<>();
      map.put("message", e.getMessage());
      return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
   }

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
}

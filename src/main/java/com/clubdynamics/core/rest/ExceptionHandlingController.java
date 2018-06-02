package com.clubdynamics.core.rest;

import com.clubdynamics.core.exception.InvalidCredentialsException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Will handle exceptions hitting the REST controller layer and turn them into custom responses.
 * Simply add handler method for all Exceptions you want to handle in a certain way. 
 * 
 * If the REST-endpoints return application/json, the ResponseEntity returned by your custom handler method
 * also needs to return JSON so you need to wrap the message in a Java object like in this example.
 * 
 * If the REST-endpoints however return text/plain, it's ok to let the ResponseEntity wrap a plain String.
 *
 */
@ControllerAdvice
@ResponseBody
public class ExceptionHandlingController {
    
    /**
     * Wrapper for error message in case endpoints return JSON
     *
     */
    public static class ErrorJson {
        
        public final String message;
        
        public ErrorJson(String message) {
            this.message = message;
        }
    }
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlingController.class);
    
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorJson> invalidCredentials(InvalidCredentialsException e) {
      
      LOGGER.info("InvalidCredentialsException for username={}", e.getLogin().username);
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorJson("Invalid user credentials"));
    }
    
    /**
     * Handler method that sends an error json message in case the request accepts json, or plain text otherwise.
     * Stolen from my cheatsheet project - TODO adapt? 
     * 
     * @param e
     * @return
     * @throws IOException
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> constraintViolationException(ConstraintViolationException e, HttpServletRequest req) {
        
      String message = e
          .getConstraintViolations()
          .stream()
          .map(v -> v.getMessage())
          .collect(Collectors.joining(";"));
      
        LOGGER.warn(
                String.format(
                        "%s , errorMessage=%s", 
                        e.getClass().getSimpleName(), 
                        message));
        List<String> headers = new ArrayList<>();
        Enumeration<String> en = req.getHeaders("accept");
        
        while(en.hasMoreElements()) {
            headers.add(en.nextElement());
        }

        LOGGER.warn("REQUEST was: {} - {}", req.getRequestURI(), headers);
        
        if(headers.contains("application/json")) {
            return ResponseEntity.status(400).body(new ErrorJson("ConstraintViolationException JSON dude: " + message));    
        } else {
            return ResponseEntity.status(400).body("ConstraintViolationException TEXT dude: " + message);
        }
    }
}

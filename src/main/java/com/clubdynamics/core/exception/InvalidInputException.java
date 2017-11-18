package com.clubdynamics.core.exception;

/**
 * To be thrown if input from client is invalid or inconsistent, e.g. validation of a bean fails.
 * Materializes as HTTP 400 BAD REQUEST.
 * 
 * @author Henning Schütz <henning@byteshaper.com>
 *
 */
public class InvalidInputException extends RuntimeException {

  private static final long serialVersionUID = -1610489496859707256L;
  
  public InvalidInputException(String message) {
    super(message);
  }
}

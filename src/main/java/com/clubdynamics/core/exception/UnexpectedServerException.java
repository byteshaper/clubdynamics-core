package com.clubdynamics.core.exception;

/**
 * For errors the server is responsible for, typically leading to a 500 INTERNAL SERVER ERROR.
 * 
 * @author Henning Schütz <henning@byteshaper.com>
 *
 */
public class UnexpectedServerException extends RuntimeException {

  private static final long serialVersionUID = -7641847373570195764L;

  public UnexpectedServerException(String message) {
    super(message);
  }
}

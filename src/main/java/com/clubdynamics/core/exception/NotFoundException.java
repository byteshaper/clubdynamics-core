package com.clubdynamics.core.exception;

/**
 * To be thrown when client requests something by id or any other criteria that is not available.
 * Materializes as HTTP 404 NOT FOUND.
 * 
 * @author Henning Schütz <henning@byteshaper.com>
 *
 */
public class NotFoundException extends RuntimeException {

  private static final long serialVersionUID = -8211079271212699792L;

  public NotFoundException(String message) {
    super(message);
    System.err.println("Notfoundexception: " + message);
  }
}

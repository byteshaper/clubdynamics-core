package com.clubdynamics.core.exception;

import com.clubdynamics.dto.entity.login.LoginDto;

/**
 * To be thrown when login attempt fails due to invalid credentials provided by the user.
 * 
 * @author Henning Schütz <henning@byteshaper.com>
 *
 */
public class InvalidCredentialsException extends RuntimeException {

	private static final long serialVersionUID = 3076873839557480832L;
	
	private final LoginDto login;
	
	public InvalidCredentialsException(LoginDto login) {
	  this.login = login;
	}

  public LoginDto getLogin() {
    return login;
  }
}

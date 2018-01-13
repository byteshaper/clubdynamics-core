package com.clubdynamics.core.domain.user;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceIntegrationTest {

  @Autowired
  private UserService userService;
  
  @Test
  public void invalidUserEmailIsRejected() {
    // TODO implement
  }
}

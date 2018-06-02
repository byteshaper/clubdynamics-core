package com.clubdynamics.core;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import com.clubdynamics.core.domain.AbstractRestIntegrationTest;
import com.clubdynamics.dto.login.LoginDto;
import com.clubdynamics.dto.login.LoginResultDto;
import org.junit.Test;

public class LoginRestIntegrationTest extends AbstractRestIntegrationTest {

  @Test
  public void loginDefaultUserCorrectCredentials() {
    LoginDto login = new LoginDto(DEFAULT_USERNAME, DEFAULT_USER_PASSWORD);
    
    LoginResultDto loginResult = postToClub("login", login)
      .then().assertThat().statusCode(200)
      .and().assertThat().contentType("application/json")
      .and().extract().response().as(LoginResultDto.class);
    
    assertThat(loginResult, notNullValue());
    assertThat(loginResult.jwtToken, notNullValue());
    assertThat(loginResult.jwtToken.length(), greaterThan(5));
  }
  
  @Test
  public void loginDefaultUserWrongCredentials() {
    LoginDto login = new LoginDto(DEFAULT_USERNAME, DEFAULT_USER_PASSWORD + "plusSomebullshit");
    postToClub("login", login).then().assertThat().statusCode(401);
  }
}

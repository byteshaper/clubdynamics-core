package com.clubdynamics.core;

import com.clubdynamics.core.domain.AbstractRestIntegrationTest;
import com.clubdynamics.dto.login.LoginDto;
import org.junit.Test;

public class LoginRestIntegrationTest extends AbstractRestIntegrationTest {

  @Test
  public void loginDefaultUserCorrectCredentials() {
    System.out.println(baseUrlWithClub + "login");
    LoginDto login = new LoginDto(DEFAULT_USERNAME, DEFAULT_USER_PASSWORD);
    postToClub("login", login)
      .then().assertThat().statusCode(200)
      .and().assertThat().contentType("application/json")
      .and().extract().response().as(LoginDto.class);
    
    // TODO hier geht's weiter (10.03.2018):
    // * Login-Endpoint implementieren - Response definieren
    // * JWT: Gültigkeitsdauer? Revoke? Refresh / renew?
  }
}

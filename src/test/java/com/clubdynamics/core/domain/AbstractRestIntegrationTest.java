package com.clubdynamics.core.domain;

import static io.restassured.RestAssured.given;

import com.clubdynamics.core.domain.club.ClubService;
import com.clubdynamics.core.domain.user.UserService;
import com.clubdynamics.dto.entity.contact.ContactTypeDto;
import com.clubdynamics.dto.entity.login.LoginDto;
import com.clubdynamics.dto.entity.login.LoginResultDto;
import com.clubdynamics.dto.entity.user.UserCreateDto;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Abstract superclass for all SpringBoot-launching integration tests that test against the RESTful interface.
 * Contains helper methods to login and logout as a certain user, hold frequently used user objects, bootstraps the app etc.
 * 
 * @author Henning Schütz
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class AbstractRestIntegrationTest {
  
  protected static final String CLUB_NAME = "BSV Kreuzberg e.V.";
  
  protected static final String URL_ALIAS = "bsvk";
  
  /**
   * Username of user that will be created as admin with all permissions ("default user") when club is created. 
   */
  protected static final String DEFAULT_USERNAME = "admin";
  
  /**
   * Password of user that will be created as admin with all permissions ("default user") when club is created. 
   */
  protected static final String DEFAULT_USER_PASSWORD = "topsecret!123";
  
  /**
   * Email of user that will be created as admin with all permissions ("default user") when club is created. 
   */
  protected static final String DEFAULT_USER_EMAIL = "admin@bsv-k.de";
  
  /**
   * Username of user that will be created as restricted user without any permissions when club is created.
   */
  protected static final String RESTRICTED_USERNAME = "restricteduser";
  
  /**
   * Password of user that will be created as restricted user without any permissions when club is created.
   */
  protected static final String RESTRICTED_USER_PASSWORD = "prettysecret!321";
  
  /**
   * Email of user that will be created as restricted user without any permissions when club is created. 
   */
  protected static final String RESTRICTED_USER_EMAIL = "restricted@bsv-k.de";
  
  /**
   * Contact type of email of both users (admin and restricted) that are created when club is created.d
   */
  protected static final ContactTypeDto CONTACT_TYPE_INIT_USERS = ContactTypeDto.PRIVATE;
  
  @LocalServerPort
  protected int port;

  @Autowired
  protected TestRestTemplate rest;
  
  /**
   * ID of the club created at the beginning.
   */
  protected Long clubId;
  
  @Autowired
  private ClubService clubService;  
  
  @Autowired
  private UserService userService;
  
  /**
   * Base-URL including port WITHOUT club-part of the path with trailing slash
   */
  protected String baseUrl;
  
  /**
   * Base-URL including port WITH club-part of the path with trailing slash
   */
  protected String baseUrlWithClub;
  
  @Before
  public void setupClubAndUsers() {
    
    if(true) {
      UserCreateDto defaultUser = new UserCreateDto(
          DEFAULT_USERNAME, DEFAULT_USER_PASSWORD, DEFAULT_USER_EMAIL, CONTACT_TYPE_INIT_USERS);
      clubId = clubService.createClub(CLUB_NAME, URL_ALIAS, defaultUser).getId();
      
      UserCreateDto restrictedUser = new UserCreateDto(
          RESTRICTED_USERNAME, RESTRICTED_USER_PASSWORD, RESTRICTED_USER_EMAIL, CONTACT_TYPE_INIT_USERS);
      userService.createNormalUser(restrictedUser, clubId);
      
      baseUrl = "http://localhost:" + port + "/clubdynamics/api/v1/";
      baseUrlWithClub = baseUrl + "clubs/" + clubId + "/";
    }
  }
  
  /**
   * Performs POST using RestAssured with content type application/json and the given payload and URL-path-suffix.
   * 
   * Example: when giving "login" as pathAfterClub, the URL will be http://localhost:<PORT>/clubs/<clubId>/login 
   * 
   * @param pathAfterClub
   * @param payload
   * @return
   */
  protected Response postToClub(String pathAfterClub, Object payload) {
    System.out.println("Posting unauthorized to : " + baseUrlWithClub + pathAfterClub);
    return given().contentType(ContentType.JSON).with().body(payload).post(baseUrlWithClub + pathAfterClub);
  }
  
  protected <R> R loginAndPostToClubSuccess(String pathAfterClub, Object payload, LoginDto login, Class<R> returnType) {
    LoginResultDto loginResult = login(login);
    return given()
        .contentType(ContentType.JSON)
        .with().header(new Header("Authorization", "Bearer " + loginResult.jwtToken))
        .with().body(payload)
        .post(baseUrlWithClub + pathAfterClub)
        .then().assertThat().statusCode(200)
        .and().assertThat().contentType("application/json")
        .and().extract().response().as(returnType);
  }
  
  protected LoginResultDto login(LoginDto login) {
    return postToClub("login", login)
    .then().assertThat().statusCode(200)
    .and().assertThat().contentType("application/json")
    .and().extract().response().as(LoginResultDto.class);
  }
}

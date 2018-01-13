package com.clubdynamics.core.domain.club;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.clubdynamics.core.domain.user.User;
import com.clubdynamics.core.domain.user.UserService;
import com.clubdynamics.core.exception.NotFoundException;
import com.clubdynamics.core.exception.UnexpectedServerException;
import com.clubdynamics.core.testutil.AssertAllFieldsEqual;
import com.clubdynamics.core.testutil.VerifyExceptions;
import com.clubdynamics.dto.UserCreateDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ClubServiceIntegrationTest {
  
  private static final String CLUB_NAME = "BSV Kreuzberg e.V.";
  
  private static final String URL_ALIAS = "bsvk";
  
  private static final String DEFAULT_USER_NAME = "bsvk-admin";
  
  private static final String DEFAULT_USER_EMAIL = "someone@bsv-k.de";
  
  private static final String DEFAULT_USER_PASSWORD = "supersecret123";
  
  @Autowired
  private ClubService clubService;
  
  @Autowired
  private UserService userService;
  
  @Test
  public void createClub() {
    // create new club:
    UserCreateDto defaultUser = new UserCreateDto();
    defaultUser.email = DEFAULT_USER_EMAIL;
    defaultUser.password = DEFAULT_USER_PASSWORD;
    defaultUser.username = DEFAULT_USER_NAME;
    Club club = clubService.createClub(CLUB_NAME, URL_ALIAS, defaultUser);
    
    // make sure it has a valid id after creation, and all values are ok:
    assertThat(club.getId(), greaterThan(0L));
    assertThat(club.getName(), equalTo(CLUB_NAME));
    assertThat(club.getUrlAlias(), equalTo(URL_ALIAS));
    
    // make sure default user was properly created:
    User defaultUserFromDb = userService.getClubDefaultUser(club.getId());
    assertTrue(defaultUserFromDb.isClubDefaultUser());
    assertThat(defaultUserFromDb.getUsername(), equalTo(DEFAULT_USER_NAME));
    assertThat(defaultUserFromDb.getPasswordHash(), notNullValue());
    assertThat(defaultUserFromDb.getPasswordHash(), not(equalTo(DEFAULT_USER_PASSWORD))); // must be hashed!
    // TODO check email
    
    // make sure club really exists after creation:
    assertThat(clubService.clubExists(CLUB_NAME), equalTo(true));
    Club clubReloaded = clubService.findClub(club.getId());
    AssertAllFieldsEqual.assertEqual(club, clubReloaded);
    
  }
}

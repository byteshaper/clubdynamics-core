package com.clubdynamics.core.auth;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.clubdynamics.core.config.SecurityConfig;
import com.clubdynamics.core.domain.user.User;
import com.clubdynamics.core.domain.userrole.UserRole;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * TODO RETHING ALL TESTS AND TEST for clubIdFromUrl != clubIdFromToken
 * 17.02.2018: hier geht's weiter!
 * 
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class JwtTokenServiceTest {

  private static final String USERNAME = "someusername";
  
  private static final long CLUB_ID = 15;
  
  private Algorithm algorithm;
  
  private JWTVerifier jwtVerifier;
  
  private JwtTokenService jwtTokenService;
  
  @Before
  public void setup() throws Exception {
    algorithm = Algorithm.HMAC256("secret");
    jwtVerifier = JWT.require(algorithm).withIssuer(SecurityConfig.JWT_ISSUER).build();
    jwtTokenService = new JwtTokenService(algorithm, jwtVerifier, 60);
  }
  
  @Test
  public void generateAndVerifyForNormalUser() {
    List<Permission> permissionsClub1 = 
        Arrays.asList(Permission.WRITE_PERSON, Permission.CREATE_USER);
    User user = createUser(
        USERNAME,
        false, 
        permissionsClub1);
    String generatedToken = jwtTokenService.generateJwtToken(user);
    SecurityUser parsedSecurityUser = jwtTokenService.parseFromJwtToken(generatedToken, CLUB_ID);
    assertNotNull(parsedSecurityUser);
    assertThat(parsedSecurityUser.getUsername(), equalTo(USERNAME));
    assertThat(parsedSecurityUser.getAuthorities().size(), equalTo(2));
    permissionsClub1.forEach(p -> 
      assertTrue(parsedSecurityUser.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals(p.name()))));
  }
  
  @Test
  public void generateAndVerifyForAllClubsAdminUserWithoutExplicitPermissions() {
    // no need to explicitly assign permissions to a user who may do anything he wants anyway: 
    List<Permission> permissionsClub1 = Arrays.asList();
    User user = createUser(
        USERNAME,
        true, 
        permissionsClub1);
    String generatedToken = jwtTokenService.generateJwtToken(user);
    SecurityUser parsedSecurityUser = jwtTokenService.parseFromJwtToken(generatedToken, CLUB_ID);
    assertNotNull(parsedSecurityUser);
    assertThat(parsedSecurityUser.getUsername(), equalTo(USERNAME));
    assertThat(parsedSecurityUser.getAuthorities().size(), equalTo(1));
    assertThat(
        new ArrayList<>(parsedSecurityUser.getAuthorities()).get(0).getAuthority(), 
        equalTo(SecurityConfig.ROLENAME_ALL_CLUBS_ADMIN));
  }
  
  @Test
  public void generateAndVerifyForAllClubsAdminUserWithExplicitPermissions() {
    // explicitly assigned permissions to a user who may do anything he want has no effects: 
    User user = createUser(
        USERNAME,
        true, 
        Arrays.asList(Permission.CREATE_USER, Permission.READ_PERSONS));
    String generatedToken = jwtTokenService.generateJwtToken(user);
    SecurityUser parsedSecurityUser = jwtTokenService.parseFromJwtToken(generatedToken, CLUB_ID);
    assertNotNull(parsedSecurityUser);
    assertThat(parsedSecurityUser.getUsername(), equalTo(USERNAME));
    assertThat(parsedSecurityUser.getAuthorities().size(), equalTo(1));
    assertThat(
        new ArrayList<>(parsedSecurityUser.getAuthorities()).get(0).getAuthority(), 
        equalTo(SecurityConfig.ROLENAME_ALL_CLUBS_ADMIN));
  }
  
  @Test(expected = UsernameNotFoundException.class)
  public void invalidToken() {
    jwtTokenService.parseFromJwtToken("invalidToken", CLUB_ID);
  }
  
  @Test
  public void tokenExpiresAsExpected() throws Exception {
    // set an extremely short expiry time of one second:
    jwtTokenService = new JwtTokenService(algorithm, jwtVerifier, 1);
    List<Permission> permissionsClub1 = Arrays.asList();
    User user = createUser(
        USERNAME,
        true, 
        permissionsClub1);
    String generatedToken = jwtTokenService.generateJwtToken(user);
    SecurityUser parsedSecurityUser = jwtTokenService.parseFromJwtToken(generatedToken, CLUB_ID);
    assertNotNull(parsedSecurityUser);
    Thread.sleep(1500); // sleep a bit longer than the expiry time
    boolean userNameNotFoundException = false;
    
    try {
      parsedSecurityUser = jwtTokenService.parseFromJwtToken(generatedToken, CLUB_ID);  
    } catch(UsernameNotFoundException e) {
      userNameNotFoundException = true;
    }
    
    assertTrue(userNameNotFoundException); // now the token is not valid anymore
  }
  
  private User createUser(
      String username, 
      boolean allClubsAdmin, 
      List<Permission> permissionsClub) {
    
    UserRole userRoleClub = new UserRole();
    userRoleClub.setName("SomeTitleDoesNotMatterHere");
    userRoleClub.setClubId(CLUB_ID);
    
    User user = new User();
    user.addRole(userRoleClub);
    user.setClubDefaultUser(allClubsAdmin);
    user.setUsername(username);
    user.setClubId(CLUB_ID);
    permissionsClub.forEach(userRoleClub::addPermission);
    return user;
  }
}

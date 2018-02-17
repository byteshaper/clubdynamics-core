package com.clubdynamics.core.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.clubdynamics.core.config.SecurityConfig;
import com.clubdynamics.core.domain.user.User;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService {

  private static final String CLAIM_USERNAME = "un";
  
  private static final String CLAIM_CLUB_DEFAULT_USER = "cluDefUs";
  
  private static final String CLAIM_PERMISSIONS = "per";
  
  private static final String CLAIM_CLUB_ID = "ci";
  
  private static final String PERMISSIONS_DELIMITER = ",";
  
  private final Algorithm algorithm;
  
  private final JWTVerifier jwtVerifier;
  
  private final int tokenExpiresAfterSeconds;
  
  public JwtTokenService(
      Algorithm algorithm, 
      JWTVerifier jwtVerifier,
      @Value("${jwt.expires.after.seconds}") int tokenExpiresAfterSeconds) throws UnsupportedEncodingException {
    this.algorithm = algorithm;
    this.jwtVerifier = jwtVerifier;
    this.tokenExpiresAfterSeconds = tokenExpiresAfterSeconds;
  }
  
  public String generateJwtToken(User user) {
    return JWT.create()
        .withIssuer(SecurityConfig.JWT_ISSUER)
        .withExpiresAt(createExpireDate())
        .withClaim(CLAIM_USERNAME, user.getUsername())
        .withClaim(CLAIM_CLUB_DEFAULT_USER, user.isClubDefaultUser()) // is this really necessary at all? probably yes!
        .withClaim(CLAIM_PERMISSIONS, createPermissionsString(user))
        .withClaim(CLAIM_CLUB_ID, user.getClubId())
        .sign(algorithm);
  }
  
  public SecurityUser parseFromJwtToken(String jwtToken, long clubIdFromUrl) {
    DecodedJWT decodedJwt = null; 
    
    try {
      decodedJwt = jwtVerifier.verify(jwtToken);
      Long clubIdFromToken = decodedJwt.getClaim(CLAIM_CLUB_ID).asLong();
      
      if(clubIdFromToken == null || clubIdFromUrl != clubIdFromToken) {
        throw new UsernameNotFoundException("Problem verifying JWT token: Club id did not match");
      }
      
      String username = decodedJwt.getClaim(CLAIM_USERNAME).asString();
      boolean clubDefaultUser = isClubDefaultUser(decodedJwt);
      
      SecurityUser securityUser = new SecurityUser(username, clubDefaultUser);
      
      if(!clubDefaultUser) {
        String permissionsString = decodedJwt.getClaim(CLAIM_PERMISSIONS).asString();
        securityUser.setUserPermissions(parsePermissions(permissionsString));
      }
      
      return securityUser;
    } catch(JWTVerificationException e) {
      throw new UsernameNotFoundException("Problem verifying JWT token: " + e.getMessage(), e);
    }
  }
  
  private List<Permission> parsePermissions(String permissionsString) {
     return Arrays
        .stream(permissionsString.split(PERMISSIONS_DELIMITER))
        .map(part -> Permission.valueOf(part))
        .collect(Collectors.toList());
  }
  
  private Date createExpireDate() {
    LocalDateTime ldt = LocalDateTime.now().plusSeconds(tokenExpiresAfterSeconds);
    return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
  }
  
  private String createPermissionsString(User user) {
    StringJoiner stringJoiner = new StringJoiner(PERMISSIONS_DELIMITER);
    user.getUserRoles().forEach(r -> 
      r.getPermissions()
        .forEach(p -> stringJoiner.add(p.name())));
    return stringJoiner.toString();
  }
  
  private boolean isClubDefaultUser(DecodedJWT decodedJwt) {
    Boolean clubDefaultUser = decodedJwt.getClaim(CLAIM_CLUB_DEFAULT_USER).asBoolean();
    return clubDefaultUser != null && clubDefaultUser.booleanValue();
  }
}

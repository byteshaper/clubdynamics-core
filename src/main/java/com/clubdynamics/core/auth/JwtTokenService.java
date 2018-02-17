package com.clubdynamics.core.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.clubdynamics.core.domain.user.User;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.apache.catalina.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService {

  private static final String CLAIM_USERNAME = "username";
  
  private static final String CLAIM_ALL_CLUBS_ADMIN = "admin";
  
  private static final String CLAIM_PERMISSIONS = "permissions";
  
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
        .withClaim(CLAIM_ALL_CLUBS_ADMIN, user.allClubsAdmin)
        .withClaim(CLAIM_PERMISSIONS, createPermissionsString(user))
        .sign(algorithm);
  }
  
  private Date createExpireDate() {
    LocalDateTime ldt = LocalDateTime.now().plusSeconds(tokenExpiresAfterSeconds);
    return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
  } 
}

package com.clubdynamics.core.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import java.io.UnsupportedEncodingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityBeanConfig {
  
  @Value("${jwt.secret}") 
  private String jwtSecret;

  @Bean
  public Algorithm createJWTAlgorithm() throws UnsupportedEncodingException {
    return Algorithm.HMAC256(jwtSecret);
  }
  
  @Bean 
  public JWTVerifier createJWTVerifier(Algorithm algorithm) {
    return JWT.require(algorithm).withIssuer(JwtTokenService.JWT_ISSUER).build();
  }
}

package com.clubdynamics.core.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.clubdynamics.core.auth.JwtTokenService;
import com.clubdynamics.core.auth.Permission;
import com.clubdynamics.core.rest.ApiPaths;
import com.clubdynamics.core.rest.auth.JwtTokenFilter;
import com.clubdynamics.core.rest.auth.JwtUserExtractor;
import java.io.UnsupportedEncodingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  
  @Autowired
  private JwtUserExtractor jwtUserExtractor;
  
  @Value("${jwt.secret}") 
  private String jwtSecret;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();
    JwtTokenFilter jwtTokenFilter = new JwtTokenFilter();
    jwtTokenFilter.setAuthenticationManager(authenticationManager());
    http.sessionManagement()
    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    .and()
    .addFilter(jwtTokenFilter).authorizeRequests()
    .and()
    .authorizeRequests()
    .antMatchers(
        HttpMethod.POST, 
        ApiPaths.BASE_PATH_CLUBS + "*") // vorher /*
    .hasAnyRole(JwtTokenService.ROLENAME_ALL_CLUBS_ADMIN)
    .antMatchers(
        HttpMethod.PUT, 
        ApiPaths.BASE_PATH_CLUBS + "*") // vorher /*
    .hasAnyRole(JwtTokenService.ROLENAME_ALL_CLUBS_ADMIN)
    .antMatchers(
        HttpMethod.GET, 
        ApiPaths.BASE_PATH_CLUBS + "*") // vorher /*
    .authenticated()
    .antMatchers(
        HttpMethod.GET, 
        ApiPaths.BASE_PATH_CLUBS)
    .hasAnyRole(JwtTokenService.ROLENAME_ALL_CLUBS_ADMIN)
//    .antMatchers(
//        HttpMethod.GET,
//        PersonController.SUBPATH_ANT + "**")    
//    .authenticated()
//    .antMatchers(
//        HttpMethod.PUT,
//        PersonController.SUBPATH_ANT + "*")    
//    .hasAnyRole(toRoleStrings(UserPermission.PERSON_WRITE))
//    .antMatchers(
//        HttpMethod.POST,
//        PersonController.SUBPATH_ANT)    
//    .hasAnyRole(toRoleStrings(UserPermission.PERSON_WRITE))
//    .antMatchers(
//        HttpMethod.PUT,
//            "/creativeperformance-service/**").hasAnyRole("")
    ;
  }
  
//  @Bean
//  public Algorithm createJWTAlgorithm() throws UnsupportedEncodingException {
//    return Algorithm.HMAC256(jwtSecret);
//  }
//  
//  @Bean 
//  public JWTVerifier createJWTVerifier(Algorithm algorithm) {
//    return JWT.require(algorithm).withIssuer(JwtTokenService.JWT_ISSUER).build();
//  }
  
  @Bean
  AuthenticationProvider authenticationProvider() {
      PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider = new PreAuthenticatedAuthenticationProvider();
      preAuthenticatedAuthenticationProvider.setPreAuthenticatedUserDetailsService(new UserDetailsByNameServiceWrapper<>(jwtUserExtractor));
      return preAuthenticatedAuthenticationProvider;
  }
  
  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/swagger-ui.html");
  }
  
  private static String[] toRoleStrings(Permission ...userPermissions) {
    String[] result = new String[userPermissions.length + 1];
    result[0] = JwtTokenService.ROLENAME_ALL_CLUBS_ADMIN;
    
    for(int i = 0; i < userPermissions.length; i++) {
      result[i+1] = userPermissions[i].name();
    }
    
    return result;
  }
}

package com.clubdynamics.core.rest.auth;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * Extracts JWT-token from HTTP request header.
 * 
 * @author henning@byteshaper.com
 *
 */
public class JwtTokenExtractor {
  
  private static final String HEADER_NAME = "Authorization";
  
  private static final String HEADER_VALUE_PREFIX = "Bearer ";
  
  public static String extractToken(HttpServletRequest request) {
    String header = request.getHeader(HEADER_NAME);
    
    if(header != null) {
      return header.replace(HEADER_VALUE_PREFIX, "");
    }
    
    return null;
  }
}

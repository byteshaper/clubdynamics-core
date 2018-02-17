package com.clubdynamics.core.rest.auth;

import com.clubdynamics.core.auth.JwtTokenService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserExtractor implements UserDetailsService {

  @Autowired
  private HttpServletRequest request;
  
  @Autowired
  private JwtTokenService jwtTokenService;

  @Autowired
  private ClubIdFromUrlExtractor clubIdFromUrlExtractor;
  
  @Override
  public UserDetails loadUserByUsername(String jwtToken) throws UsernameNotFoundException {
    final String url = request.getRequestURI().toString();
    long clubIdFromUrl = clubIdFromUrlExtractor
        .extractClubId(url)
        .orElseThrow(() -> new UsernameNotFoundException("No clubId found in URL: " + url));
    return jwtTokenService.parseFromJwtToken(jwtToken, clubIdFromUrl);  
  }
}

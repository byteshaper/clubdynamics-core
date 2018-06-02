package com.clubdynamics.core.rest.auth;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import com.clubdynamics.core.restcontroller.auth.JwtTokenExtractor;
import javax.servlet.http.HttpServletRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class JwtTokenExtractorTest {

  private static final String TOKEN = "abcdef123";
  
  @Mock
  private HttpServletRequest request;
  
  @Test
  public void extractTokenFromAppropriateHeader() {
    when(request.getHeader("Authorization")).thenReturn("Bearer " + TOKEN);
    String extractedToken = JwtTokenExtractor.extractToken(request);
    assertThat(extractedToken, equalTo(TOKEN));
  }
  
  @Test
  public void dontExtractTokenWhenHeaderMissing() {
    when(request.getHeader("Authorization")).thenReturn(null);
    String extractedToken = JwtTokenExtractor.extractToken(request);
    assertNull(extractedToken);
  }
}

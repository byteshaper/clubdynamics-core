package com.clubdynamics.core.rest.auth;

import com.clubdynamics.core.rest.ApiPaths;
import java.util.OptionalLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class ClubIdFromUrlExtractor {
  
  private static final String PATTERN_STRING = ApiPaths.BASE_PATH_CLUBS + "(\\d+?)/.*";
  
  private static final Pattern PATTERN = Pattern.compile(PATTERN_STRING);

  /**
   * Tries to extract club id from a given url path.
   * 
   * @param path relativ path in request url starting after <PORT>, e.g. .../api/v1/secured/clubs/3/whatever/more 
   * @return
   */
  public OptionalLong extractClubId(String path) {
    Matcher matcher = PATTERN.matcher(path);
    
    if(matcher.matches()) {
      return OptionalLong.of(Long.parseLong(matcher.group(1)));
    }
    
    return OptionalLong.empty();
  }
}

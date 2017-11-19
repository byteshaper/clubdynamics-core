package com.clubdynamics.core;

import com.clubdynamics.core.auth.CryptoUtil;
import com.clubdynamics.core.domain.club.ClubService;
import com.clubdynamics.dto.UserCreateDto;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Temporary quick and dirty solution to make sure our club exists.
 * 
 * @author Henning Schütz
 *
 */
@Component
@Profile("!test")
public class BsvKBootstrapper {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(BsvKBootstrapper.class);
  
  private static final String CLUB_NAME = "BSV Kreuzberg e.V.";
  
  private static final String URL_ALIAS = "bsvk";
  
  private static final String DEFAULT_USERNAME = "admin";
  
  @Autowired
  public ClubService clubService;  
  
  @Autowired
  private Environment environment;
  
  @PostConstruct
  public void makeSureBsvKExists() {
    LOGGER.info("Detecting profiles...");
    Arrays.stream(environment.getActiveProfiles()).forEach(p -> LOGGER.info("Active profile: {}", p));
    
    UserCreateDto userCreateDto = new UserCreateDto();
    userCreateDto.username = DEFAULT_USERNAME;
    userCreateDto.password = CryptoUtil.generateSalt(); // that will do for a temp password
    
    if (!clubService.clubExists(CLUB_NAME)) {
      clubService.createClub(CLUB_NAME, URL_ALIAS, userCreateDto);
      LOGGER.info("Created club {}", CLUB_NAME);
    } else {
      LOGGER.info("Club {} already exists", CLUB_NAME);
    }
  }
}

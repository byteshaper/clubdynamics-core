package com.clubdynamics.core.rest.auth;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.OptionalLong;

import org.junit.Before;
import org.junit.Test;

public class ClubIdFromUrlExtractorTest {
  
  private ClubIdFromUrlExtractor clubIdFromUrlExtractor;

  @Before
  public void setup() {
    clubIdFromUrlExtractor = new ClubIdFromUrlExtractor();
  }
  
  @Test
  public void clubIdIsParsedFromStringWithClubIdWithin() {
    OptionalLong clubId = clubIdFromUrlExtractor.extractClubId("/clubdynamics/api/v1/clubs/7/some/other/path");
    assertTrue(clubId.isPresent());
    assertThat(clubId.getAsLong(), equalTo(7L));
  }
  
  @Test
  public void clubIdIsNotParsedFromShorterStringWithClubIdWithin() {
    OptionalLong clubId = clubIdFromUrlExtractor.extractClubId("/clubs/7/some/other/path");
    assertFalse(clubId.isPresent());
  }
  
  @Test
  public void clubIdIsParsedFromStringWithClubIdAtTheEndWithSlash() {
    OptionalLong clubId = clubIdFromUrlExtractor.extractClubId("/clubdynamics/api/v1/clubs/3/");
    assertTrue(clubId.isPresent());
    assertThat(clubId.getAsLong(), equalTo(3L));
  }
  
  @Test
  public void clubIdIsParsedFromStringWithClubIdAndOtherIds() {
    OptionalLong clubId = clubIdFromUrlExtractor.extractClubId("/clubdynamics/api/v1/clubs/3/persons/5/");
    assertTrue(clubId.isPresent());
    assertThat(clubId.getAsLong(), equalTo(3L));
  }
  
  @Test
  public void clubIdIsParsedFromStringWithClubIdAtTheEndWithoutSlash() {
    OptionalLong clubId = clubIdFromUrlExtractor.extractClubId("/clubdynamics/api/v1/clubs/5/");
    assertTrue(clubId.isPresent());
    assertThat(clubId.getAsLong(), equalTo(5L));
  }
  
  @Test
  public void clubIdIsNotParsedWhenClubIsMissingInPath() {
    OptionalLong clubId = clubIdFromUrlExtractor.extractClubId("/clubdynamics/api/v1/persons/7/some/other/path");
    assertFalse(clubId.isPresent());
  }
}

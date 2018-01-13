package com.clubdynamics.core.domain.club;

import com.clubdynamics.core.domain.user.UserService;
import com.clubdynamics.core.exception.NotFoundException;
import com.clubdynamics.dto.user.UserCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClubService {
  
  @Autowired
  private ClubRepository clubRepository;
  
  @Autowired
  private UserService userService;
  
  /**
   * Creates a new club with a certain name and optional url-alias and creates all other necessary objects associated with
   * the new club:
   * - an admin role
   * - a user with that role
   * @param name
   * @param urlAlias
   */
  public Club createClub(String name, String urlAlias, UserCreateDto defaultUser) {
    Club club = new Club();
    club.setName(name);
    club.setUrlAlias(urlAlias);
    club = clubRepository.save(club);
    userService.createClubDefaultUser(defaultUser, club.getId());
    return club;
  }
  
  public boolean clubExists(String clubName) {
    return clubRepository.findByName(clubName).isPresent();
  }
  
  public Club findClub(long clubId) {
    return clubRepository.findById(clubId).orElseThrow(() -> new NotFoundException("No club found with id " + clubId));
  }
}

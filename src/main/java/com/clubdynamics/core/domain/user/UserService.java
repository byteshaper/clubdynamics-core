package com.clubdynamics.core.domain.user;

import com.clubdynamics.core.auth.CryptoUtil;
import com.clubdynamics.core.domain.contact.ContactType;
import com.clubdynamics.core.domain.contact.Email;
import com.clubdynamics.core.domain.userrole.UserRole;
import com.clubdynamics.core.domain.userrole.UserRoleService;
import com.clubdynamics.core.exception.UnexpectedServerException;
import com.clubdynamics.dto.user.UserCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  
  @Autowired
  private UserRepository userRepository;
  
  @Autowired
  private UserRoleService userRoleService;

  public User createClubDefaultUser(UserCreateDto userCreateDto, long clubId) {
    User clubDefaultUser = fillUser(userCreateDto, clubId, true);
    UserRole clubDefaultRole = userRoleService.ensureClubDefaultRole(clubId);
    clubDefaultUser.addRole(clubDefaultRole);
    return userRepository.save(clubDefaultUser);
  }
  
  public User createNormalUser(UserCreateDto userCreateDto, long clubId) {
    User clubDefaultUser = fillUser(userCreateDto, clubId, false);
    return userRepository.save(clubDefaultUser);
  }
  
  public User getClubDefaultUser(long clubId) {
    User clubDefaultUsers = userRepository.getUserByClubIdAndClubDefaultUser(clubId, true).orElse(null);
    
    if(clubDefaultUsers == null) {
      throw new UnexpectedServerException("No default user found for club with id " + clubId);
    }
    
    return clubDefaultUsers;
  }
  
  private User fillUser(UserCreateDto userCreateDto, long clubId, boolean defaultUser) {
    User user = new User();
    user.setClubId(clubId);
    user.setClubDefaultUser(defaultUser);
    user.setUsername(userCreateDto.username);
    user.setEmail(new Email(userCreateDto.email, ContactType.valueOf(userCreateDto.emailContactType.name())));
    user.setSalt(CryptoUtil.generateSalt());
    user.setPasswordHash(CryptoUtil.encryptPassword(userCreateDto.password, user.getSalt()));
    return user;
  }

}

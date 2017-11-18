package com.clubdynamics.core.domain.user;

import com.clubdynamics.core.auth.CryptoUtil;
import com.clubdynamics.core.domain.userrole.UserRole;
import com.clubdynamics.core.domain.userrole.UserRoleService;
import com.clubdynamics.dto.UserCreateDto;
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
  
  private User fillUser(UserCreateDto userCreateDto, long clubId, boolean defaultUser) {
    User user = new User();
    user.setClubId(clubId);
    user.setClubDefaultUser(defaultUser);
    user.setUsername(userCreateDto.username);
    // TODO email
    user.setSalt(CryptoUtil.generateSalt());
    user.setPasswordHash(CryptoUtil.encryptPassword(userCreateDto.password, user.getSalt()));
    return user;
  }

}

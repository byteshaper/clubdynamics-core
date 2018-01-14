package com.clubdynamics.core.domain.userrole;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {
  
  /**
   * Initial name of the default role that is supposed to be created when a new club is created. 
   */
  private static final String INITIAL_NAME_DEFAULT_ROLE = "superadmin";
  
  @Autowired
  private UserRoleRepository userRoleRepository;

  public UserRole ensureClubDefaultRole(long clubId) {
    
    Optional<UserRole> defaultUserRole = userRoleRepository.getByClubIdAndClubDefaultRole(clubId, true);
    
    return defaultUserRole.orElseGet(() -> {
      UserRole userRole = new UserRole();
      userRole.setClubId(clubId);
      userRole.setClubDefaultRole(true);
      userRole.setName(INITIAL_NAME_DEFAULT_ROLE);
      return userRoleRepository.save(userRole);  
    });
  }
}

package com.clubdynamics.core.domain.user;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Long>{

  Optional<User> getUserByClubIdAndClubDefaultUser(long clubId, boolean clubDefaultUser);
  
  Optional<User> getUserByClubIdAndUsername(long clubId, String username);
}

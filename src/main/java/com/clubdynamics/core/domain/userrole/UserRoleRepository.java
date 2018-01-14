package com.clubdynamics.core.domain.userrole;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {

  Optional<UserRole> getByClubIdAndClubDefaultRole(long clubId, boolean clubDefaultRole);
}

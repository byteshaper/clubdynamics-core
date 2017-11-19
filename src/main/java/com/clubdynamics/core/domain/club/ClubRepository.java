package com.clubdynamics.core.domain.club;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRepository extends CrudRepository<Club, Long> {
  
  Optional<Club> findByName(String clubName);
  
  Optional<Club> findById(long id);
}

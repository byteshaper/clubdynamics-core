package com.clubdynamics.core.domain;

import com.clubdynamics.core.domain.club.Club;
import com.clubdynamics.core.exception.UnexpectedServerException;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * Abstract superclass for all JPA-entities, providing an unique auto-generated id and enforcing a club-id (so {@link Club} 
 * can NOT inherit from this class).
 * 
 * @author henning.schuetz
 *
 */
@MappedSuperclass
public class AbstractEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  
  @NotNull
  protected long clubId;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    if (this.id == 0L) {
      this.id = id;
    }
  }
  
  public long getClubId() {
    return clubId;
  }

  public void setClubId(long clubId) {
    
    if(this.clubId == 0) {
      this.clubId = clubId;  
    } else {
      throw new UnexpectedServerException("Cannot change club-association of an entity, it's already set to " + clubId);
    }
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (id ^ (id >>> 32));
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    AbstractEntity other = (AbstractEntity) obj;
    
    if (id != other.id) {
      return false;
    }
    return true;
  }
}

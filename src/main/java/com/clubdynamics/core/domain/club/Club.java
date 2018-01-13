package com.clubdynamics.core.domain.club;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "club")
public class Club {
  
  @NotEmpty
  private String name;
  
  @Pattern(regexp = "[a-z0-9A-Z\\-]{3,20}")
  private String urlAlias;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  
  public long getId() {
    return id;
  }

  public void setId(long id) {
    if (this.id == 0L) {
      this.id = id;
    }
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrlAlias() {
    return urlAlias;
  }

  public void setUrlAlias(String urlAlias) {
    this.urlAlias = urlAlias;
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
    Club other = (Club) obj;
    if (id != other.id) {
      return false;
    }
    return true;
  }
  
  @Override
  public String toString() {
    //return new ToStringb
    return "TODO tostringbuilder";
  }
}

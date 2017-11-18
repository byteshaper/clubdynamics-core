package com.clubdynamics.core.domain.club;

import com.clubdynamics.core.domain.AbstractEntity;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "club")
public class Club extends AbstractEntity {
  
  @NotEmpty
  private String name;
  
  @Pattern(regexp = "[a-z0-9A-Z\\-]{3,20}")
  private String urlAlias;

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
  
  
}

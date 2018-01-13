package com.clubdynamics.core.domain.contact;

import com.clubdynamics.core.domain.AbstractEntity;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "phone")
public class Phone extends AbstractEntity {

  @NotNull
  @Enumerated(EnumType.STRING)
  private ContactType contactType;
  
  private boolean main;
  
  private String comment;
  
  public ContactType getContactType() {
    return contactType;
  }

  public void setContactType(ContactType contactType) {
    this.contactType = contactType;
  }
  
  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }
  
  public boolean isMain() {
    return main;
  }

  public void setMain(boolean main) {
    this.main = main;
  }
}

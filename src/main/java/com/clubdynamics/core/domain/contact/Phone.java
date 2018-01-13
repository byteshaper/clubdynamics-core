package com.clubdynamics.core.domain.contact;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

public class Phone {

  // TODO number
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

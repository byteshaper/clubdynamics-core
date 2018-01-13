package com.clubdynamics.core.domain.contact;

import com.clubdynamics.core.domain.AbstractEntity;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "email")
public class Email extends AbstractEntity {

  @NotNull
  @Enumerated(EnumType.STRING)
  private ContactType contactType;
  
  @NotBlank
  @org.hibernate.validator.constraints.Email
  private String address;
  
  private boolean main;
  
  private String comment;
  
  public ContactType getContactType() {
    return contactType;
  }

  public void setContactType(ContactType contactType) {
    this.contactType = contactType;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public boolean isMain() {
    return main;
  }

  public void setMain(boolean main) {
    this.main = main;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }
}

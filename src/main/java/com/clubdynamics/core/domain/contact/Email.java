package com.clubdynamics.core.domain.contact;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotBlank;

@Embeddable
public class Email {

  @NotNull
  @Enumerated(EnumType.STRING)
  private ContactType contactType;
  
  @NotBlank
  @org.hibernate.validator.constraints.Email
  private String address;
  
  private boolean main = false;
  
  private String comment;
  
  /**
   * Creates instance with {@link #main} == false.
   */
  public Email() {}
  
  /**
   * Creates instance with {@link #main} == false.
   * @param address
   * @param contactType
   */
  public Email(String address, ContactType contactType) {
    this.address = address;
    this.contactType = contactType;
  }
  
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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((address == null) ? 0 : address.hashCode());
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
    Email other = (Email) obj;
    if (address == null) {
      if (other.address != null) {
        return false;
      }
    } else if (!address.equals(other.address)) {
      return false;
    }
    return true;
  }
  
  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("address", address)
      .append("contactType", contactType)
      .append("main", main)
      .build();
  }
}

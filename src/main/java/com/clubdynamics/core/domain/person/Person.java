package com.clubdynamics.core.domain.person;

import com.clubdynamics.core.domain.AbstractEntity;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "person", uniqueConstraints = {
    @UniqueConstraint(columnNames = {
        "firstName", "lastName", "middleNames", "birthday"
    })
})
public class Person extends AbstractEntity {

  @NotBlank
  private String firstName;
  
  @NotBlank
  private String lastName;
  
  private String middleNames;
  
  private LocalDate birthday;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getMiddleNames() {
    return middleNames;
  }

  public void setMiddleNames(String middleNames) {
    this.middleNames = middleNames;
  }

  public LocalDate getBirthday() {
    return birthday;
  }

  public void setBirthday(LocalDate birthday) {
    this.birthday = birthday;
  }
}

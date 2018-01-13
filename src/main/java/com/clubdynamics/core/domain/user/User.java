package com.clubdynamics.core.domain.user;

import com.clubdynamics.core.domain.AbstractEntity;
import com.clubdynamics.core.domain.contact.Email;
import com.clubdynamics.core.domain.userrole.UserRole;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "user", uniqueConstraints = {
    @UniqueConstraint(columnNames = {
        "username", "clubId"
    })
})
public class User extends AbstractEntity {

  @NotEmpty
  private String username;
  
  @NotEmpty
  private String passwordHash;
  
  @NotEmpty
  private String salt;
  
  @NotNull
  @Embedded
  private Email email;
  
  private boolean clubDefaultUser;
  
  @NotNull
  @ManyToMany
  @CollectionTable(name = "user_roles")
  @Column(name = "role")
  private Set<UserRole> userRoles = new HashSet<>();
  
  public Set<UserRole> getUserRoles() {
    return Collections.unmodifiableSet(userRoles);
  }
  
  public void addRole(UserRole userRole) {
    userRoles.add(userRole);
  }

  public boolean isClubDefaultUser() {
    return clubDefaultUser;
  }

  public void setClubDefaultUser(boolean clubDefaultUser) {
    this.clubDefaultUser = clubDefaultUser;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

  public void setUserRoles(Set<UserRole> userRoles) {
    this.userRoles = userRoles;
  }

  public Email getEmail() {
    return email;
  }

  public void setEmail(Email email) {
    this.email = email;
  }
}

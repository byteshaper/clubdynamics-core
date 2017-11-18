package com.clubdynamics.core.domain.userrole;

import com.clubdynamics.core.auth.Permission;
import com.clubdynamics.core.domain.AbstractEntity;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "userrole", uniqueConstraints = {
    @UniqueConstraint(columnNames = {
        "name", "clubId"
    })
})
public class UserRole extends AbstractEntity {
  
  @NotNull
  private Long clubId;

  @NotEmpty
  private String name;

  private boolean clubDefaultRole;

  @NotNull
  @ElementCollection(targetClass = Permission.class)
  @Enumerated(EnumType.STRING)
  @CollectionTable(name = "userrole_permission")
  @Column(name = "permission")
  private Set<Permission> permissions = new HashSet<>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isClubDefaultRole() {
    return clubDefaultRole;
  }

  public void setClubDefaultRole(boolean clubDefaultRole) {
    this.clubDefaultRole = clubDefaultRole;
  }

  public Set<Permission> getPermissions() {
    return permissions;
  }

  public void setPermissions(Set<Permission> permissions) {
    this.permissions = permissions;
  }
  
  @Transient
  public void addPermission(Permission permission) {
    permissions.add(permission);
  }

  public long getClubId() {
    return clubId;
  }

  public void setClubId(long clubId) {
    this.clubId = clubId;
  }
}

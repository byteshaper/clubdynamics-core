package com.clubdynamics.core.auth;

import com.clubdynamics.core.config.SecurityConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * User as required by Spring-Security - should be kept separate from the 
 * business entity {@link com.byteshaper.clubdynamic.domain.authorization.User}.
 * 
 * @author henning@byteshaper.com
 *
 */
public class SecurityUser extends User {

  private static final long serialVersionUID = 695583895623677816L;

  private final boolean clubDefaultUser;

  private List<Permission> userPermissions;

  public SecurityUser(String username, boolean clubDefaultUser) { // TODO clubDefaultUser necessary here? probably yes!
    super(username, "no-password", new ArrayList<>());
    this.clubDefaultUser = clubDefaultUser;
  }

  @Override
  public Collection<GrantedAuthority> getAuthorities() {
    if (clubDefaultUser) {
      return Arrays.asList(new SimpleGrantedAuthority(SecurityConfig.ROLENAME_ALL_CLUBS_ADMIN));
    } else {
      return toAuthorities(userPermissions);
    }
  }

  public void setUserPermissions(List<Permission> userPermissions) {
    this.userPermissions = userPermissions;
  }

  private Collection<GrantedAuthority> toAuthorities(Collection<Permission> permissions) {
    return permissions.stream().map(p -> new SimpleGrantedAuthority(p.name())).collect(Collectors.toList());
  }

}

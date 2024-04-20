package refugee.helper.back.service.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import refugee.helper.back.database.entity.VolunteerEntity.Type;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Getter
@Setter
public class SecurityUserModel implements UserDetails {
  private String id;
  private String email;
  private String name;
  private String surname;
  private String phone;
  private String photo;
  private String password;
  private Type type;
  private Boolean enabled;
  private LocalDateTime createdOn;
  private Roles role;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(role);
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return null;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  public enum Roles implements GrantedAuthority {
    ROLE_VOLUNTEER;

    @Override
    public String getAuthority() {
      return name();
    }
  }
}

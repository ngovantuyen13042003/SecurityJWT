package nvt.st.securityjwt.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import nvt.st.securityjwt.model.Role;
import nvt.st.securityjwt.model.User;
import org.apache.catalina.LifecycleState;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class CustomUserDetail implements UserDetails {
    private Long id;
    private String fullname;
    private String email;
    @JsonIgnore
    private String password;
    private Boolean enabled;
    private Collection<? extends GrantedAuthority> roles;


    public static CustomUserDetail mapUserToUserDetails(User user) {

        List<GrantedAuthority> authenties = user.getRoles().stream()
                .map(roles -> new SimpleGrantedAuthority(roles.getName().toString()))
                .collect(Collectors.toList());

        return new CustomUserDetail(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getPassword(),
                user.getEnabled(),
                authenties
        );
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
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
        return true;
    }
}

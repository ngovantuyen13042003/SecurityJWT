package nvt.st.securityjwt.model.authentication;

import jakarta.persistence.*;
import lombok.*;
import nvt.st.securityjwt.model.BaseEntity;

import java.util.ArrayList;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class User extends BaseEntity {
    private String fullName;
    @Column(unique = true)
    private String email;
    private String password;
    private Boolean enabled;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles = new ArrayList<>();
}

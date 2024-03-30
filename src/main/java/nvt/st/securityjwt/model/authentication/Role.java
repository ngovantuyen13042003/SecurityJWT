package nvt.st.securityjwt.model.authentication;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nvt.st.securityjwt.model.BaseEntity;

import java.util.Collection;

@Getter
@Setter
@Entity
public class Role extends BaseEntity {

    @Enumerated(value = EnumType.STRING)
    private ERole name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;
}

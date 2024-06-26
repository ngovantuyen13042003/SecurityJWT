package nvt.st.securityjwt.service;

import nvt.st.securityjwt.model.authentication.ERole;
import nvt.st.securityjwt.model.authentication.Role;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IRoleService {
    Optional<Role> findByRoleName(ERole name);

    Role save(Role role);

    List<Role> saveAll(Collection<Role>  roles);
}

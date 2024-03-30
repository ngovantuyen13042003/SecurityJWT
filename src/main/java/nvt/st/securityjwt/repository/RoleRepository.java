package nvt.st.securityjwt.repository;

import nvt.st.securityjwt.model.authentication.ERole;
import nvt.st.securityjwt.model.authentication.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole role);
}

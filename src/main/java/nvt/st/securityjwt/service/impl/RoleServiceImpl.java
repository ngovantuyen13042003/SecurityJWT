package nvt.st.securityjwt.service.impl;

import nvt.st.securityjwt.model.authentication.ERole;
import nvt.st.securityjwt.model.authentication.Role;
import nvt.st.securityjwt.repository.RoleRepository;
import nvt.st.securityjwt.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Optional<Role> findByRoleName(ERole name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<Role> saveAll(Collection<Role> roles) {
        return roleRepository.saveAll(roles);
    }


}

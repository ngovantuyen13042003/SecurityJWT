package nvt.st.securityjwt.service;

import nvt.st.securityjwt.model.authentication.User;

import java.util.Optional;

public interface IUserService {
    Optional<User> findByEmail(String email);
}

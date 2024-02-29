package nvt.st.securityjwt.service.impl;

import nvt.st.securityjwt.model.User;
import nvt.st.securityjwt.repository.UserRepository;
import nvt.st.securityjwt.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    UserRepository userRepository;
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}

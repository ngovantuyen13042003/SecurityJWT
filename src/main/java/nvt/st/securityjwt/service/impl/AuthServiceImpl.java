package nvt.st.securityjwt.service.impl;

import nvt.st.securityjwt.jwt.JwtTokenProvider;
import nvt.st.securityjwt.model.authentication.ERole;
import nvt.st.securityjwt.model.authentication.Role;
import nvt.st.securityjwt.model.authentication.User;
import nvt.st.securityjwt.payload.request.LoginRequest;
import nvt.st.securityjwt.payload.request.SignupRequest;
import nvt.st.securityjwt.payload.response.AuthenticationResponse;
import nvt.st.securityjwt.repository.UserRepository;
import nvt.st.securityjwt.service.IAuthService;
import nvt.st.securityjwt.service.IRoleService;
import nvt.st.securityjwt.service.authentication.IRefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class AuthServiceImpl implements IAuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final IRefreshTokenService refreshTokenService;

    @Autowired
    private IRoleService roleService;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager, IRefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
    }

    public AuthenticationResponse register(SignupRequest request) {


        User user = new User();

        user.setEmail(request.getEmail());
        user.setEnabled(request.getEnabled());
        user.setFullName(request.getFullName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Collection<String> strRoles = request.getRoles();
        Collection<Role> listRoles = new ArrayList<>();
        Collection<User> listUsers = new ArrayList<>() ;
        listUsers.add(user);
        if(listRoles == null) {
            // set role default : user
            Role userRole = roleService.findByRoleName(ERole.USER).orElseThrow(() -> new RuntimeException("Error: Role if not found!"));
            listRoles.add(userRole);
        }else {
            strRoles.forEach(role->{
                switch (role){
                    case "admin":
                        Role adminRole = roleService.findByRoleName(ERole.ADMIN)
                                .orElseThrow(()-> new RuntimeException("Error: Role is not found!"));
                        listRoles.add(adminRole);
                    case "moderator":
                        Role mdRole = roleService.findByRoleName(ERole.MODERATOR)
                                .orElseThrow(()->new RuntimeException("Error: Role is not found!"));
                        listRoles.add(mdRole);
                    case "user":
                        Role userRole = roleService.findByRoleName(ERole.USER)
                                .orElseThrow(()->new RuntimeException("Error: Role is not found!"));
                        listRoles.add(userRole);
                }
            });
        }
        user.setRoles(listRoles);
        user = userRepository.save(user);
        String jwt = jwtTokenProvider.generateToken(request.getEmail());
        return new AuthenticationResponse(jwt, "","User login was successful!", Instant.now());
    }

    @Override
    public AuthenticationResponse authenticate(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new BadCredentialsException("Login information is incorrect!"));
        String jwt = jwtTokenProvider.generateToken(user.getEmail());
        String refreshToken = refreshTokenService.createRefreshToken(authentication).getToken();
        return new AuthenticationResponse(jwt,refreshToken, "User login was successfully", Instant.now());
    }





}

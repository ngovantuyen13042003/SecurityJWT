package nvt.st.securityjwt.service;

import nvt.st.securityjwt.payload.request.LoginRequest;
import nvt.st.securityjwt.payload.request.SignupRequest;
import nvt.st.securityjwt.payload.response.AuthenticationResponse;

public interface IAuthService {
    AuthenticationResponse register(SignupRequest signupRequest);
    AuthenticationResponse authenticate(LoginRequest loginRequest);
}

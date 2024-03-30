package nvt.st.securityjwt.controller;

import nvt.st.securityjwt.dto.authentication.RefreshTokenRequest;
import nvt.st.securityjwt.exception.RefreshTokenException;
import nvt.st.securityjwt.jwt.JwtTokenProvider;
import nvt.st.securityjwt.model.authentication.User;
import nvt.st.securityjwt.model.authentication.RefreshToken;
import nvt.st.securityjwt.payload.request.LoginRequest;
import nvt.st.securityjwt.payload.request.SignupRequest;
import nvt.st.securityjwt.payload.response.AuthenticationResponse;
import nvt.st.securityjwt.service.IAuthService;
import nvt.st.securityjwt.service.authentication.IRefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController

@RequestMapping("api/auth")
public class AuthController {

    private final IAuthService authService;
    private final IRefreshTokenService refreshTokenService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthController(IAuthService authService, IRefreshTokenService refreshTokenService, JwtTokenProvider jwtTokenProvider) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody SignupRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();

        String jwt = refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(User::getEmail)
                .map(jwtTokenProvider::generateToken)
                .orElseThrow(() -> new RefreshTokenException("Refresh token was expired. Please make a new signin request!"));
        return ResponseEntity.ok(new AuthenticationResponse("Refresh token", jwt, refreshToken, Instant.now()));
    }


}

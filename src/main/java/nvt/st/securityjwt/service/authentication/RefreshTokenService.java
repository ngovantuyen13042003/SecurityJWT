package nvt.st.securityjwt.service.authentication;

import lombok.RequiredArgsConstructor;
import nvt.st.securityjwt.exception.RefreshTokenException;
import nvt.st.securityjwt.exception.ResourceNotFoundException;
import nvt.st.securityjwt.model.authentication.RefreshToken;
import nvt.st.securityjwt.repository.RefreshTokenRepository;
import nvt.st.securityjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService implements IRefreshTokenService{
    @Value("${com.app.jwt.jwtRefreshExpiration}")
    private int jwtRefreshExpirationMs;
    private RefreshTokenRepository refreshTokenRepository;
    private UserRepository userRepository;
    @Override
    public RefreshToken createRefreshToken(Authentication authentication) {
        String username = authentication.getName();
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username)));
        refreshToken.setExpiryDate(Instant.now().plusMillis(jwtRefreshExpirationMs));
        refreshToken.setToken(UUID.randomUUID().toString());
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if(refreshToken.getExpiryDate().compareTo(Instant.now())<0) {
            refreshTokenRepository.delete(refreshToken);
            throw new RefreshTokenException("Refresh token was expired. Please make a new signin request!");
        }
        return refreshToken;
    }
}

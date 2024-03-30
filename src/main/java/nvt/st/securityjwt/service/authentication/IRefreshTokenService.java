package nvt.st.securityjwt.service.authentication;

import nvt.st.securityjwt.model.authentication.RefreshToken;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface IRefreshTokenService {
    RefreshToken createRefreshToken(Authentication authentication);
    Optional<RefreshToken> findByToken(String token);

    RefreshToken verifyExpiration(RefreshToken refreshToken);
}

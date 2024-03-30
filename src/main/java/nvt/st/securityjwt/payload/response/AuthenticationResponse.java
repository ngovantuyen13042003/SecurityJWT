package nvt.st.securityjwt.payload.response;

import java.time.Instant;

public class AuthenticationResponse {
    private String accessToken;
    private String refreshToken;
    private String message;
    private Instant createdAt;

    public AuthenticationResponse(String accessToken, String refreshToken, String message, Instant createdAt) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.message = message;
        this.createdAt = createdAt;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}

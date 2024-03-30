package nvt.st.securityjwt.payload.request;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.Collection;

@Data
public class SignupRequest {
    private String fullName;
    @Column(unique = true)
    private String email;
    private String password;
    private Boolean enabled;
    Collection<String> roles;
}

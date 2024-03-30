package nvt.st.securityjwt.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BaseResponse {
    private int code;
    private String message;
}

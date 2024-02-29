package nvt.st.securityjwt.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter@Setter
@Builder@AllArgsConstructor@NoArgsConstructor
public class ExceptionResponseDTO {
    private String path;
    private String errorCode;
    private String errorMessage;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime time;
}

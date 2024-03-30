package nvt.st.securityjwt.exception;

import lombok.Data;

@Data
public class ResourceNotFoundException extends RuntimeException{
    private String name;
    private String value;
    private String resourceName;


    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public ResourceNotFoundException(String name, String value, String resourceName) {
        super(String.format("% not found with %s: %s", resourceName, name, value));
        this.name = name;
        this.value = value;
        this.resourceName = resourceName;
    }
}

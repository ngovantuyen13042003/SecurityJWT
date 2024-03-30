package nvt.st.securityjwt.exception;

import lombok.Data;

@Data
public class ResourceExistsException extends RuntimeException{
    private String resourceName;
    private String name;
    private String value;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public ResourceExistsException(String resourceName, String name, String value) {
        super(String.format("%s with %s '%s' already exists. Please choose a different %s", resourceName, name, value, name));
        this.resourceName = resourceName;
        this.name = name;
        this.value = value;
    }
}

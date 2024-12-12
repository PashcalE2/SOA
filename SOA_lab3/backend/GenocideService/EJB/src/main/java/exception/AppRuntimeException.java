package exception;

import jakarta.ws.rs.core.Response;
import lombok.Getter;

@Getter
public class AppRuntimeException extends RuntimeException {
    private final Response.Status status;
    private final String extendedClass;

    public AppRuntimeException(Response.Status status, String message) {
        super(message);
        this.status = status;
        this.extendedClass = AppRuntimeException.class.getName();
    }
}

package exception;

import jakarta.ws.rs.core.Response;
import lombok.Getter;

@Getter
public class AppException extends Exception {
    private final Response.Status status;
    private final String extendedClass;

    public AppException(Response.Status status, String message) {
        super(message);
        this.status = status;
        this.extendedClass = AppException.class.getName();
    }
}

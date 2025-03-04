package exception;

import entity.dto.ErrorDto;
import jakarta.ws.rs.core.Response;
import jakarta.xml.ws.WebFault;
import lombok.Getter;

@Getter
public class AppRuntimeException extends RuntimeException {
    private final ErrorDto error  = new ErrorDto();
    private final String extendedClass;

    public AppRuntimeException(Response.Status status, String message) {
        super(message);
        this.error.setStatus(status.getStatusCode());
        this.error.setMessage(message);
        this.extendedClass = AppRuntimeException.class.getName();
    }
}

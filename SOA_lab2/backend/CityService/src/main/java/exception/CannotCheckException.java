package exception;

import jakarta.ws.rs.core.Response;

public class CannotCheckException extends AppException {
    public CannotCheckException(String message) {
        super(Response.Status.BAD_REQUEST, message, CannotCheckException.class.getName());
    }
}

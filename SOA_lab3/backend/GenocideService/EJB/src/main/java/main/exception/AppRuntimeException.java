package main.exception;

import lombok.Getter;
import main.entity.HttpStatus;

@Getter
public class AppRuntimeException extends RuntimeException {
    private final HttpStatus status;

    public AppRuntimeException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}

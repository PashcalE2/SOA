package main.exception;

import lombok.Getter;
import main.entity.HttpStatus;

@Getter
public class AppException extends Exception {
    private final HttpStatus status;

    public AppException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}

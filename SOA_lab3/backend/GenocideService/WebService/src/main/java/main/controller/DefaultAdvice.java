package main.controller;

import lombok.extern.slf4j.Slf4j;
import main.exception.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class DefaultAdvice {
    @ExceptionHandler(AppException.class)
    public ResponseEntity<?> handleAppException(AppException e) {
        log.error(e.getMessage(), e.getStatus());
        return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.valueOf(e.getStatus().value()));
    }
}

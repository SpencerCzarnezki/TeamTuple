package learn.events.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler
    public ResponseEntity<String> handle(Exception ex) {
        return new ResponseEntity<>("Big Problem.",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handle(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>(
                List.of(ex.getMostSpecificCause().getMessage()),
                HttpStatus.BAD_REQUEST);
    }

}

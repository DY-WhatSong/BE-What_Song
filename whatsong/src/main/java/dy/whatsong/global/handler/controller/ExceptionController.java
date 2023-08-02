package dy.whatsong.global.handler.controller;

import dy.whatsong.global.handler.exception.InvalidRequestAPIException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(InvalidRequestAPIException.class)
    public ResponseEntity<String> handleUserNotFoundException(InvalidRequestAPIException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}

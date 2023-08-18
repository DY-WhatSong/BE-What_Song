package dy.whatsong.global.handler.controller;

import dy.whatsong.global.handler.exception.ErrorResponse;
import dy.whatsong.global.handler.exception.InvalidRequestAPIException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(InvalidRequestAPIException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(InvalidRequestAPIException ex) {
        ErrorResponse returnErrRes=new ErrorResponse(ex.getErrorCode(),ex.getMessage());
        return ResponseEntity.status(ex.getErrorCode()).body(returnErrRes);
    }
}

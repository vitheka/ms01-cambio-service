package br.com.vitor.ms01cambioservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalErrorHandlerAdvice {

    @ExceptionHandler(CurrencyUnsupportedException.class)
    public ResponseEntity<DefaultErrorMessage> handleBookNotFoundException(CurrencyUnsupportedException ex) {
        var errorResponse = new DefaultErrorMessage(HttpStatus.BAD_REQUEST.value(), ex.getReason());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


}

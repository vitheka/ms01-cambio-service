package br.com.vitor.ms01cambioservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CurrencyUnsupportedException extends ResponseStatusException {
    public CurrencyUnsupportedException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }
}

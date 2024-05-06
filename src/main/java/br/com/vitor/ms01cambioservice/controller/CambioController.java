package br.com.vitor.ms01cambioservice.controller;

import br.com.vitor.ms01cambioservice.domain.Cambio;
import br.com.vitor.ms01cambioservice.service.CambioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Tag(name = "Cambio endpoint")
@RestController
@RequestMapping("/v1/cambio-service")
@RequiredArgsConstructor
public class CambioController {

    private final CambioService cambioService;

    @Operation(summary = "Find a specific book by you id")
    @GetMapping("/{amount}/{from}/{to}")
    public ResponseEntity<Cambio> getCambio(@PathVariable("amount") BigDecimal amount,
                                           @PathVariable("from") String from,
                                           @PathVariable("to") String to
    ) {

        return ResponseEntity.ok(cambioService.getCambio(from,to,amount));
    }
}

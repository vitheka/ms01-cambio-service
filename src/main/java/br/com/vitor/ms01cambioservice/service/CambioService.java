package br.com.vitor.ms01cambioservice.service;

import br.com.vitor.ms01cambioservice.domain.Cambio;
import br.com.vitor.ms01cambioservice.exception.CurrencyUnsupportedException;
import br.com.vitor.ms01cambioservice.repository.CambioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class CambioService {

    private final Environment environment;
    private final CambioRepository repository;
    public Cambio getCambio(String from, String to, BigDecimal amount) {

        var cambio = repository.findByFromAndTo(from, to);
        if (cambio == null) throw new CurrencyUnsupportedException("Currency Unsupported");

        var port = environment.getProperty("local.server.port");

        var conversionFactor = cambio.getConversionFactor();
        var convertedValue = conversionFactor.multiply(amount);
        cambio.setConvertedValue(convertedValue.setScale(2, RoundingMode.CEILING));
        cambio.setEnvironment(port);

        return cambio;
    }
}

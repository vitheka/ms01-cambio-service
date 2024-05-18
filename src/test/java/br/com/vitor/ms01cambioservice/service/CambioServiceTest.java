package br.com.vitor.ms01cambioservice.service;

import br.com.vitor.ms01cambioservice.domain.Cambio;
import br.com.vitor.ms01cambioservice.exception.CurrencyUnsupportedException;
import br.com.vitor.ms01cambioservice.repository.CambioRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CambioServiceTest {

    @InjectMocks
    private CambioService cambioService;

    @Mock
    private Environment environment;

    @Mock
    private CambioRepository cambioRepository;

    private Cambio cambioInit;

    @BeforeEach
    void init() {

        cambioInit = Cambio.builder()
                .id(1L)
                .from("USD")
                .to("BRL")
                .conversionFactor(BigDecimal.valueOf(5.0))
                .build();
    }

    @Test
    @DisplayName("getCambio() return cambio when successful")
    @Order(1)
    void getCambio_ReturnCambio_WhenSuccessful() {

        var from = "USD";
        var to = "BRL";

        when(cambioRepository.findByFromAndTo(from, to)).thenReturn(cambioInit);

        var cambioFound = cambioService.getCambio(from, to, BigDecimal.valueOf(5.0));

        assertThat(cambioFound).isNotNull().isEqualTo(cambioInit);
    }

    @Test
    @DisplayName("getCambio() return cambio when successful")
    @Order(2)
    void getCambio_ReturnCurrencyUnsupportedException_WhenCurrencyNotSupported() {

        var from = "USD";
        var to = "LLL";

        when(cambioRepository.findByFromAndTo(from, to)).thenReturn(null);

        Assertions.assertThatException().isThrownBy(() -> cambioService.getCambio(from,to,BigDecimal.valueOf(10)))
                .isInstanceOf(CurrencyUnsupportedException.class);
    }
}
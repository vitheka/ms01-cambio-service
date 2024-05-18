package br.com.vitor.ms01cambioservice.controller;

import br.com.vitor.ms01cambioservice.commons.FileUtils;
import br.com.vitor.ms01cambioservice.config.IntegrationTestContainers;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.javacrumbs.jsonunit.assertj.JsonAssertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CambioControllerTestIT extends IntegrationTestContainers {


    @Autowired
    private FileUtils fileUtils;

    @LocalServerPort
    private int port;

    @BeforeEach
    void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    @DisplayName("getCambio() return cambio when successful")
    @Sql(value = "/sql/init_one_cambio.sql")
    @Order(1)
    void getCambio_ReturnCambio_WhenSuccessful() throws Exception {

        var expectedResponse = fileUtils.readResourceFile("get-one-cambio-200.json");
        var amount = 10;
        var from = "USD";
        var to = "BRL";


        var response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .when()
                .get("/v1/cambio-service/{amount}/{from}/{to}", amount, from, to)
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().response().body().asString();

        JsonAssertions.assertThatJson(response)
                .whenIgnoringPaths("environment")
                .isEqualTo(expectedResponse);

    }

}
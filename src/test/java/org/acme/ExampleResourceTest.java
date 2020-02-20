package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ExampleResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/hello")
          .then()
             .statusCode(200)
                .body(is("hello!")); // Modified line
    }

    @Test
    public void testHelloEndpointFail() {
        given()
                .when().get("/hello")
                .then()
                .statusCode(200)
                .body(is("fail!")); // Modified line
    }

}
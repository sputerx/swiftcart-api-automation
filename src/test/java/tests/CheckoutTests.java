package tests;

import base.BaseTest;
import constants.Endpoints;
import constants.StatusCodes;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CheckoutTests extends BaseTest {

    @Test
    public void checkout_withValidPayload_returns201() {
        String body = "{"
            + "\"firstName\":\"John\","
            + "\"lastName\":\"Doe\","
            + "\"email\":\"john@example.com\","
            + "\"address\":\"123 Main St\","
            + "\"city\":\"New York\","
            + "\"zip\":\"10001\","
            + "\"cardNumber\":\"4111111111111111\","
            + "\"expiry\":\"12/26\","
            + "\"cvv\":\"123\""
            + "}";

        given()
            .body(body)
        .when()
            .post(Endpoints.CHECKOUT)
        .then()
            .statusCode(StatusCodes.CREATED)
            .body("orderId", notNullValue());
    }

    @Test
    public void checkout_withMissingEmail_returns400() {
        String body = "{"
            + "\"name\":\"John Doe\","
            + "\"address\":\"123 Main St\","
            + "\"cardNumber\":\"4111111111111111\""
            + "}";

        given()
            .body(body)
        .when()
            .post(Endpoints.CHECKOUT)
        .then()
            .statusCode(StatusCodes.BAD_REQUEST);
    }

    @Test
    public void checkout_withEmptyBody_returns400() {
        given()
            .body("{}")
        .when()
            .post(Endpoints.CHECKOUT)
        .then()
            .statusCode(StatusCodes.BAD_REQUEST);
    }
}

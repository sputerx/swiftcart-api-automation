package tests;

import base.BaseTest;
import constants.Endpoints;
import constants.StatusCodes;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class HealthTests extends BaseTest {

    @Test
    public void healthCheck_returns200WithStatusOk() {
        given()
        .when()
            .get(Endpoints.HEALTH)
        .then()
            .statusCode(StatusCodes.OK)
            .body("status", equalTo("ok"))
            .body("service", equalTo("swiftcart-api"));
    }
}

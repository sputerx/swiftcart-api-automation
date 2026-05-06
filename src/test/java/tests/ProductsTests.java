package tests;

import base.BaseTest;
import constants.Endpoints;
import constants.StatusCodes;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ProductsTests extends BaseTest {

    @Test
    public void getProducts_returns200AndNonEmptyList() {
        given()
        .when()
            .get(Endpoints.PRODUCTS)
        .then()
            .statusCode(StatusCodes.OK)
            .body("$", not(empty()));
    }

    @Test
    public void getProducts_eachItemHasIdNameAndPrice() {
        given()
        .when()
            .get(Endpoints.PRODUCTS)
        .then()
            .statusCode(StatusCodes.OK)
            .body("products[0].id", notNullValue())
            .body("products[0].name", notNullValue())
            .body("products[0].price", notNullValue());
    }

    @Test
    public void getProductById_withValidId_returns200() {
        Integer productId = given()
            .when()
                .get(Endpoints.PRODUCTS)
            .then()
                .statusCode(StatusCodes.OK)
                .extract()
                .path("products[0].id");

        given()
            .pathParam("id", productId)
        .when()
            .get(Endpoints.PRODUCT_BY_ID)
        .then()
            .statusCode(StatusCodes.OK)
            .body("id", equalTo(productId))
            .body("name", notNullValue());

    }

    @Test
    public void getProductById_withInvalidId_returns404() {
        given()
            .pathParam("id", "nonexistent-id-99999")
        .when()
            .get(Endpoints.PRODUCT_BY_ID)
        .then()
            .statusCode(StatusCodes.NOT_FOUND);
    }
}

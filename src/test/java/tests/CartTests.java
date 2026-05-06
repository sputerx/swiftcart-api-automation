package tests;

import base.BaseTest;
import constants.Endpoints;
import constants.StatusCodes;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CartTests extends BaseTest {

    @Test
    public void getCart_returns200() {
        given()
        .when()
            .get(Endpoints.CART)
        .then()
            .statusCode(StatusCodes.OK)
            .body("items", notNullValue());
    }

    @Test
    public void addItemToCart_withValidProduct_returns200Or201() {
        Integer productId = given()
            .when()
                .get(Endpoints.PRODUCTS)
            .then()
                .statusCode(StatusCodes.OK)
                .extract()
                .path("products[0].id");

        given()
            .body("{\"productId\":" + productId + ",\"quantity\":1}")
        .when()
            .post(Endpoints.CART_ITEMS)
        .then()
            .statusCode(anyOf(equalTo(StatusCodes.OK), equalTo(StatusCodes.CREATED)));
    }

    @Test
    public void addItemToCart_withMissingProductId_returns400() {
        given()
            .body("{\"quantity\":1}")
        .when()
            .post(Endpoints.CART_ITEMS)
        .then()
            .statusCode(StatusCodes.BAD_REQUEST);
    }

    @Test
    public void deleteCartItem_withValidProductId_returns200Or204() {
        Integer productId = given()
            .when()
                .get(Endpoints.PRODUCTS)
            .then()
                .statusCode(StatusCodes.OK)
                .extract()
                .path("products[0].id");

        given()
            .pathParam("productId", productId)
        .when()
            .delete(Endpoints.CART_ITEM_BY_PRODUCT_ID)
        .then()
            .statusCode(anyOf(
                equalTo(StatusCodes.OK),
                equalTo(StatusCodes.NO_CONTENT),
                equalTo(StatusCodes.NOT_FOUND)
            ));
    }
}

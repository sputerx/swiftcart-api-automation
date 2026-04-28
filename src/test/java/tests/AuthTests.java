package tests;

import base.BaseTest;
import config.ConfigManager;
import constants.Endpoints;
import constants.StatusCodes;
import models.LoginRequest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AuthTests extends BaseTest {

    @Test
    public void login_withValidCredentials_returns200AndToken() {
        LoginRequest body = LoginRequest.builder()
                .email(ConfigManager.getEmail())
                .password(ConfigManager.getPassword())
                .build();

        given()
            .body(body)
        .when()
            .post(Endpoints.LOGIN)
        .then()
            .statusCode(StatusCodes.OK)
            .body("token", notNullValue())
            .body("token", not(emptyString()));
    }

    @Test
    public void login_withInvalidPassword_returns401() {
        LoginRequest body = LoginRequest.builder()
                .email(ConfigManager.getEmail())
                .password("wrongpassword")
                .build();

        given()
            .body(body)
        .when()
            .post(Endpoints.LOGIN)
        .then()
            .statusCode(StatusCodes.UNAUTHORIZED);
    }

    @Test
    public void login_withUnknownEmail_returns401() {
        LoginRequest body = LoginRequest.builder()
                .email("notauser@example.com")
                .password("anypassword")
                .build();

        given()
            .body(body)
        .when()
            .post(Endpoints.LOGIN)
        .then()
            .statusCode(StatusCodes.UNAUTHORIZED);
    }
}

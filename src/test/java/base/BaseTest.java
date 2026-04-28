package base;

import config.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import org.testng.annotations.BeforeClass;

import static io.restassured.http.ContentType.JSON;

public abstract class BaseTest {

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        RestAssured.baseURI = ConfigManager.getBaseUri();
        RestAssured.requestSpecification = RestAssured
                .given()
                .contentType(JSON)
                .accept(JSON);
        RestAssured.config = RestAssured
                .config()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails());
    }
}

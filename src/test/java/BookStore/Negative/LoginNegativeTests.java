package BookStore.Negative;

import Utilities.POJO.response.BadRequest;
import Utilities.POJO.response.BadRequest;
import base.BaseClass;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static Utilities.ReUsableUtils.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class LoginNegativeTests extends BaseClass {    // Test for login with non-existent email
    @Test(priority = 1)
    public void GET_loginWithNonExistentEmail() throws Exception {
        Response response = 
                given()
                        .spec(bookStoreRequestSpec())
                        .body(getBookStoreRequestBody("login", "nonExistentEmail")).
                when()
                        .post("/login").
                then()
                        .statusCode(400) // Unauthorized
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.body().jsonPath().getString("detail")).isEqualTo("Incorrect email or password")
        );


    }
    @Test(priority = 2)
    public void GET_loginWithWrongPassword() throws Exception {
        Response response = 
                given()
                        .spec(bookStoreRequestSpec())
                        .body(getBookStoreRequestBody("login", "wrongPassword")).
                when()
                        .post("/login").
                then()
                        .statusCode(400)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.body().jsonPath().getString("detail")).isEqualTo("Incorrect email or password")
        );
    }    // Test for login with missing email
    @Test(priority = 3)
    public void GET_loginWithMissingEmail() throws Exception {
        Response response = 
                given()
                        .spec(bookStoreRequestSpec())
                        .body(getBookStoreRequestBody("login", "missingEmail")).
                when()
                        .post("/login").
                then()
                        .statusCode(400)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.body().jsonPath().getString("detail")).isEqualTo("Incorrect email or password")
        );
    }

    // Test for login with missing password
    @Test(priority = 4)
    public void GET_loginWithMissingPassword() throws Exception {        Response response = 
                given()
                        .spec(bookStoreRequestSpec())
                        .body(getBookStoreRequestBody("login", "missingPassword")).
                when()
                        .post("/login").
                then()
                        .statusCode(400)
                        .extract()
                        .response();


        logAssertionResult(
                assertThat(response.body().jsonPath().getString("detail")).isEqualTo("Incorrect email or password")
        );

    }

    // Test for login with empty email
     @Test(priority = 5)
    public void GET_loginWithEmptyEmail() throws Exception {
        Response response = 
                given()
                        .spec(bookStoreRequestSpec())
                        .body(getBookStoreRequestBody("login", "emptyEmail")).
                when()
                        .post("/login").
                then()
                        .statusCode(400)
                        .extract()
                        .response();

         logAssertionResult(
                 assertThat(response.body().jsonPath().getString("detail")).isEqualTo("Incorrect email or password")
         );
    }    // Test for login with empty password
    @Test(priority = 6)
    public void GET_loginWithEmptyPassword() throws Exception {
        Response response = 
                given()
                        .spec(bookStoreRequestSpec())
                        .body(getBookStoreRequestBody("login", "emptyPassword")).
                when()
                        .post("/login").
                then()
                        .statusCode(400)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.body().jsonPath().getString("detail")).isEqualTo("Incorrect email or password")
        );
    }

    // Test for login with null values
    @Test(priority = 7)
    public void GET_loginWithNullEmail() throws Exception {
        Response response = 
                given()
                        .spec(bookStoreRequestSpec())
                        .body(getBookStoreRequestBody("login", "nullEmail")).
                when()
                        .post("/login").
                then()
                        .statusCode(400)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.body().jsonPath().getString("detail")).isEqualTo("Incorrect email or password")
        );
    }    // Test for login with invalid email format
    @Test(priority = 8)
    public void GET_loginWithInvalidEmailFormat() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec())
                        .body(getBookStoreRequestBody("login", "invalidEmailFormat")).
                when()
                        .post("/login").
                then()
                        .statusCode(400)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.body().jsonPath().getString("detail")).isEqualTo("Incorrect email or password")
        );
    }

    // Test for case sensitivity in login email
    @Test(priority = 9)
    public void GET_loginWithCaseSensitiveEmail() {
        Response response = 
                given()
                        .spec(bookStoreRequestSpec())
                        .body("{\n" +
                                "    \"email\":\"ABC@TEST.COM\",\n" +
                                "    \"password\":\"asd\"\n" +
                                "}").
                when()
                        .post("/login").

                then()
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.body().jsonPath().getString("detail")).isEqualTo("Incorrect email or password")
        );
    }

    @Test(priority = 10)
    public void GET_loginWithExtremelyLongCredentials() {
        String longEmail = generateRandomString(100) + "@" + generateRandomString(100) + ".com";
        String longPassword = generateRandomString(200);
        
        Response response = 
                given()
                        .spec(bookStoreRequestSpec())
                        .body("{\n" +
                                "    \"email\":\"" + longEmail + "\",\n" +
                                "    \"password\":\"" + longPassword + "\"\n" +
                                "}").
                when()
                        .post("/login").
                then()
                        .statusCode(400)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.body().jsonPath().getString("detail")).isEqualTo("Incorrect email or password")
        );
    }

    // Test for malformed JSON in login
    @Test(priority = 11)
    public void GET_loginWithMalformedJSON() {
        Response response = 
                given()
                        .spec(bookStoreRequestSpec())
                        .body("{\n" +
                                "    email:\"abc@test.com\",\n" +
                                "    password:\"asd\"\n" +
                                "}").
                when()
                        .post("/login").
                then()
                        .statusCode(422)
                        .extract()
                        .response();

        logAssertionResult(
        assertThat(response.body().as(BadRequest.class).getDetail().get(0))
                .satisfies(detail -> {
                    assertThat(detail.getType()).isEqualTo("json_invalid");
                    assertThat(detail.getMsg()).isEqualTo("JSON decode error");
                    assertThat(detail.getCtx().getError()).isEqualTo("Expecting property name enclosed in double quotes");
                })
        );
    }

    // Test for empty JSON body in login
    @Test(priority = 12)
    public void GET_loginWithEmptyJSON() {
        Response response = 
                given()
                        .spec(bookStoreRequestSpec())
                        .body("{}").
                when()
                        .post("/login").
                then()
                        .statusCode(400)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.body().jsonPath().getString("detail")).isEqualTo("Incorrect email or password")
        );
    }

    // Test for wrong HTTP method on login
    @Test(priority = 13)
    public void GET_loginWithWrongHTTpRequestMethod() {
        Response response = 
                given()
                        .spec(bookStoreRequestSpec())
                        .body("{\n" +
                                "    \"email\":\"abc@test.com\",\n" +
                                "    \"password\":\"asd\"\n" +
                                "}").
                when()
                        .get("/login").
                then()
                        .statusCode(405) // Method Not Allowed
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.body().jsonPath().getString("detail")).isEqualTo("Method Not Allowed")
        );

    }

    // Test for no body in login request
    @Test(priority = 14)
    public void GET_loginWithNoBody() {
        Response response = 
                given()
                        .spec(bookStoreRequestSpec()).
                when()
                        .post("/login").
                then()
                        .statusCode(422)
                        .extract()
                        .response();

        logAssertionResult(
        assertThat(response.body().as(BadRequest.class).getDetail().get(0))
                .satisfies(detail -> {
                    assertThat(detail.getType()).isEqualTo("missing");
                    assertThat(detail.getMsg()).isEqualTo("Field required");
                })
        );
    }

    // Test for invalid content type in login
    @Test(priority = 15)
    public void GET_loginWithInvalidContentType() {
        Response response = 
                given()
                        .spec(bookStoreRequestSpec())
                        .contentType("text/plain")
                        .body("{\n" +
                                "    \"email\":\"abc@test.com\",\n" +
                                "    \"password\":\"asd\"\n" +
                                "}").
                when()
                        .post("/login").
                then()
                        .statusCode(422) // Unsupported Media Type
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.body().as(BadRequest.class).getDetail().get(0))
                        .satisfies(detail -> {
                            assertThat(detail.getType()).isEqualTo("model_attributes_type");
                            assertThat(detail.getLoc().get(0)).isEqualTo("body");
                            assertThat(detail.getMsg()).isEqualTo("Input should be a valid dictionary or object to extract fields from");
                        })
        );
    }

    @Test(priority = 16)
    public void GET_loginWithSpecialCharPassword() {
        Response response = 
                given()
                .spec(bookStoreRequestSpec())
                        .body("{\n" +
                                "    \"email\":\"abc@test.com\",\n" +
                                "    \"password\":\"!@#$%^&*()\"\n" +
                                "}").
                when()
                        .post("/login").
                then()
                        .statusCode(400)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.body().jsonPath().getString("detail")).isEqualTo("Incorrect email or password")
        );
    }

    // Test for login with Unicode characters
    @Test(priority = 17)
    public void GET_loginWithUnicodeCharacters() {
        Response response = 
                given()
                        .spec(bookStoreRequestSpec())
                        .body("{\n" +
                                "    \"email\":\"test🙂@example.com\",\n" +
                                "    \"password\":\"пароль123\"\n" +
                                "}").
                when()
                        .post("/login").
                then()
                        .statusCode(400)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.body().jsonPath().getString("detail")).isEqualTo("Incorrect email or password")
        );
    }

    // Test for login with extra fields
    @Test(priority = 18)
    public void GET_loginWithExtraFields() {
        Response response = 
                given()
                        .spec(bookStoreRequestSpec())
                        .body("{\n" +
                                "    \"email\":\"abc@test.com\",\n" +
                                "    \"password\":\"asd\",\n" +
                                "    \"rememberMe\":true,\n" +
                                "    \"deviceInfo\":\"test-device\"\n" +
                                "}").
                when()
                        .post("/login").
                then()
                        .extract()
                        .response();

        // This might be 200 if API ignores extra fields, or 400 if it validates strictly
        logAssertionResult(
                assertThat(response.statusCode()).isIn(200, 400),
                "Status code should be 200 or 400 for extra fields in login request"
        );
    }
}

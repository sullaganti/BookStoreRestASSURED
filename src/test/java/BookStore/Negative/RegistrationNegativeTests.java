package BookStore.Negative;

import Utilities.POJO.response.BadRequest;
import Utilities.POJO.response.BadRequest;
import base.BaseClass;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import static Utilities.ReUsableUtils.*;
import static Utilities.ReUsableUtils.generateRandomString;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class RegistrationNegativeTests extends BaseClass {
    @Test(priority = 1)
    public void POST_signUpWithMissingEmail() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec())
                        .body(getBookStoreRequestBody("registration", "missingEmail")).
                when()
                        .post("/signup").
                then()
                        .statusCode(500)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.body().asString()).isEqualTo("Internal Server Error")
        );
    }

    @Test(priority = 2)
    public void POST_signUpWithMissingPassword() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec())
                        .body(getBookStoreRequestBody("registration", "missingPassword")).
                when()
                        .post("/signup").
                then()
                        .statusCode(500)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.body().asString()).isEqualTo("Internal Server Error")
                );
    }

    @Test(priority = 3)
    public void POST_signUpWithEmptyEmail() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec())
                        .body(getBookStoreRequestBody("registration", "emptyEmail")).
                when()
                        .post("/signup").
                then()
                        .extract()
                        .response();

        logAssertionResult(()->SoftAssertions.assertSoftly(s->{
            s.assertThat(response.statusCode()).isEqualTo(400);
            s.assertThat(response.body().as(BadRequest.class).getMessage()).isNotEqualTo("User created successfully");
        })
        );


    }

    @Test(priority = 4)
    public void POST_signUpWithEmptyPassword() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec())
                        .body(getBookStoreRequestBody("registration", "emptyPassword")).
                when()
                        .post("/signup").
                then()
                        .extract()
                        .response();

        logAssertionResult(()->SoftAssertions.assertSoftly(s->{
                    s.assertThat(response.statusCode()).isEqualTo(400);
                    s.assertThat(response.body().as(BadRequest.class).getMessage()).isNotEqualTo("User created successfully");
                })
        );
    }

    @Test(priority = 5)
    public void POST_signUpWithNullEmail() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec())
                        .body(getBookStoreRequestBody("registration", "nullEmail")).
                when()
                        .post("/signup").
                then()
                        .statusCode(500)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.body().asString()).isEqualTo("Internal Server Error")
        );
    }

    @Test(priority = 6)
    public void POST_signUpWithNullPassword() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec())
                        .body(getBookStoreRequestBody("registration", "nullPassword")).
                when()
                        .post("/signup").
                then()
                        .statusCode(400)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.as(BadRequest.class).getDetail()).isEqualTo("Email already registered")
        );
    }

    @Test(priority = 7)
    public void POST_signUpWithInvalidEmailMissingAtTheRateSymbol() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec())
                        .body(getBookStoreRequestBody("registration", "invalidEmailMissingAt")).
                when()
                        .post("/signup").
                then()
                        .extract()
                        .response();

        logAssertionResult(()->SoftAssertions.assertSoftly(s->{
                    s.assertThat(response.statusCode()).isEqualTo(400);
                    s.assertThat(response.body().as(BadRequest.class).getMessage()).isNotEqualTo("User created successfully");
                })
        );
    }

    @Test(priority = 8)
    public void POST_signUpWithInvalidEmailMissingDomain() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec())
                        .body(getBookStoreRequestBody("registration", "invalidEmailMissingDomain")).
                when()
                        .post("/signup").
                then()
                        .extract()
                        .response();

        logAssertionResult(()->SoftAssertions.assertSoftly(s->{
                    s.assertThat(response.statusCode()).isEqualTo(400);
                    s.assertThat(response.body().as(BadRequest.class).getMessage()).isNotEqualTo("User created successfully");
                })
        );
    }

    @Test(priority = 9)
    public void POST_signUpWithInvalidEmailSpecialChars() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec())
                        .body(getBookStoreRequestBody("registration", "invalidEmailSpecialChars")).
                when()
                        .post("/signup").
                then()
                        .extract()
                        .response();

        logAssertionResult(()->SoftAssertions.assertSoftly(s->{
                    s.assertThat(response.statusCode()).isEqualTo(400);
                    s.assertThat(response.body().as(BadRequest.class).getMessage()).isNotEqualTo("User created successfully");
                })
        );
    }

    @Test(priority = 10)
    public void POST_signUpWithExtremelyLongEmail() {
        String longEmail = generateRandomString(100) + "@" + generateRandomString(100) + ".com";

        Response response = given()
                .spec(bookStoreRequestSpec())
                .body("{\n" +
                        "    \"email\":\"" + longEmail + "\",\n" +
                        "    \"password\":\"validPassword123\"\n" +
                        "}")
                .when()
                .post("/signup")
                .then()
                .extract()
                .response();

        logAssertionResult(()->SoftAssertions.assertSoftly(s->{
                    s.assertThat(response.statusCode()).isEqualTo(400);
                    s.assertThat(response.body().as(BadRequest.class).getMessage()).isNotEqualTo("User created successfully");
                })
        );
    }

    @Test(priority = 11)
    public void POST_signUpWithExtremelyLongPassword() {
        String longPassword = generateRandomString(200);
        Response response = given()
                .spec(bookStoreRequestSpec())
                .body("{\n" +
                        "    \"email\":\"test@example.com\",\n" +
                        "    \"password\":\"" + longPassword + "\"\n" +
                        "}")
                .when()
                .post("/signup")
                .then()
                .statusCode(400)
                .extract()
                .response();

        logAssertionResult(
                assertThat(response.statusCode()).isEqualTo(400),
                "Status code should be 400 for extremely long password"
        );
    }

    @Test(priority = 12)
    public void POST_signUpWithPasswordOnlySpaces() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec())
                        .body(getBookStoreRequestBody("registration", "passwordWithSpaces")).
                when()
                        .post("/signup").
                then()
                        .statusCode(400)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.statusCode()).isEqualTo(400),
                "Status code should be 400 for password with only spaces"
        );
    }

    @Test(priority = 13)
    public void POST_signUpWithDuplicateEmail() throws Exception {
        // First registration (assuming this email already exists from positive tests)
         Response response =
                given()
                        .spec(bookStoreRequestSpec())
                        .body(getBookStoreRequestBody("registration", "duplicateRegistration")).
                when()
                        .post("/signup").
                then()
                        .statusCode(400) // Conflict status code for duplicate
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.jsonPath().getString("detail")).isEqualTo("Email already registered")
        );
    }

    @Test(priority = 14)
    public void POST_signUpWithMalformedJSON() {

        Response response =

                given()
                        .spec(bookStoreRequestSpec())
                        .body("{\n" +
                                "    email:\"test@example.com\",\n" +
                                "    password:\"validPassword123\"\n" +
                                "}")
                .when()
                        .post("/signup")
                .then()
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

    @Test(priority = 15)
    public void POST_signUpWithEmptyJSON() {
        Response response =
                given()
                        .spec(bookStoreRequestSpec())
                        .body("{}").
                when()
                        .post("/signup").
                then()
                        .statusCode(500)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.body().asString()).isEqualTo("Internal Server Error")
        );
    }

    @Test(priority = 16)
    public void POST_signUpWithInvalidContentType() {
        Response response =
                given()
                        .spec(bookStoreRequestSpec())
                        .contentType("text/plain")
                        .body("{\n" +
                                "    \"email\":\"test@example.com\",\n" +
                                "    \"password\":\"validPassword123\"\n" +
                                "}").
                when()
                        .post("/signup").
                then()
                        .statusCode(422) // Unsupported Media Type
                        .extract()
                        .response();
        logAssertionResult(
                assertThat(response.jsonPath().getString("detail[0].type")).isEqualTo("model_attributes_type")
                );
        logAssertionResult(
                assertThat(response.jsonPath().getString("detail[0].msg")).isEqualTo("Input should be a valid dictionary or object to extract fields from")
        );

    }

    @Test(priority = 19)
    public void POST_signUpWithUnicodeEmail() {
        Response response =
                given()
                        .spec(bookStoreRequestSpec())
                        .body("{\n" +
                                "    \"email\":\"test😀@example.com\",\n" +
                                "    \"password\":\"validPassword123\"\n" +
                                "}").
                when()
                        .post("/signup").
                 then()
                        .extract()
                        .response();

        logAssertionResult(()->SoftAssertions.assertSoftly(s->{
                    s.assertThat(response.statusCode()).isEqualTo(400);
                    s.assertThat(response.body().as(BadRequest.class).getMessage()).isNotEqualTo("User created successfully");
                })
        );
    }

    @Test(priority = 20)
    public void POST_signUpWithSingleCharPassword() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec())
                        .body(getBookStoreRequestBody("registration", "shortPassword")).
                when()
                        .post("/signup").
                then()
                        .statusCode(400)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.statusCode()).isEqualTo(400),
                "Status code should be 400 for single character password"
        );
    }

    @Test(priority = 21)
    public void POST_signUpWithSpecialCharPasswordOnly() {
        Response response =
                given()
                        .spec(bookStoreRequestSpec())
                        .body("{\n" +
                                "    \"email\":\"test@example.com\",\n" +
                                "    \"password\":\"!@#$%^&*()\"\n" +
                                "}").
                when()
                        .post("/signup").
                then()
                        .statusCode(400)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.statusCode()).isEqualTo(400),
                "Status code should be 400 for password with only special characters"
        );
    }

    @Test(priority = 22)
    public void POST_signUpWithNumericOnlyPassword() {
        Response response =
                given()
                        .spec(bookStoreRequestSpec())
                        .body("{\n" +
                                "    \"email\":\"test@example.com\",\n" +
                                "    \"password\":\"123456789\"\n" +
                                "}").
                when()
                        .post("/signup").
                then()
                        .statusCode(400)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.statusCode()).isEqualTo(400),
                "Status code should be 400 for numeric only password"
        );
    }

    @Test(priority = 23)
    public void POST_signUpWithExtraFields() {
        Response response =
                given()
                        .spec(bookStoreRequestSpec())
                        .body("{\n" +
                                "    \"email\":\"test@example.com\",\n" +
                                "    \"password\":\"validPassword123\",\n" +
                                "    \"extraField\":\"extraValue\",\n" +
                                "    \"anotherField\":123\n" +
                                "}").
                when()
                        .post("/signup").
                then()
                        .extract()
                        .response();

        // This might be 200 if API ignores extra fields, or 400 if it validates strictly
        logAssertionResult(
                assertThat(response.statusCode()).isIn(200, 400),
                "Status code should be 200 or 400 for extra fields in request body"
        );
    }

    @Test(priority = 24)
    public void GET_signUpWithValidCredentials() {
        Response response =
                given()
                        .spec(bookStoreRequestSpec())
                        .body("{\n" +
                                "    \"email\":\"test@example.com\",\n" +
                                "    \"password\":\"validPassword123\"\n" +
                                "}").
                when()
                        .get("/signup"). // Using GET instead of POST
                then()
                        .statusCode(405) // Method Not Allowed
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.statusCode()).isEqualTo(405),
                "Status code should be 405 for wrong HTTP method"
        );
    }

    @Test(priority = 25)
    public void POST_signUpWithNoBody() {
        Response response =
                given()
                        .spec(bookStoreRequestSpec())
                .when()
                        .post("/signup")
                .then()
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

    @Test(priority = 26)
    public void POST_signUpWithCaseSensitiveEmail() {
        // First register with lowercase
                given()
                        .spec(bookStoreRequestSpec())
                        .body("{\n" +
                                "    \"email\":\"casetest@example.com\",\n" +
                                "    \"password\":\"validPassword123\"\n" +
                                "}")
                .when()
                        .post("/signup")
                .then()
                        .statusCode(200);

        // Try to register with uppercase version of same email
        Response response =
                given()
                        .spec(bookStoreRequestSpec())
                        .body("{\n" +
                                "    \"email\":\"CASETEST@EXAMPLE.COM\",\n" +
                                "    \"password\":\"validPassword123\"\n" +
                                "}")
                .when()
                        .post("/signup")
                .then()
                        .extract()
                        .response();

        // Should either accept (treat as different) or reject (treat as same)
        logAssertionResult(
                assertThat(response.statusCode()).isIn(200, 409),
                "Status code should be 200 or 409 for case sensitive email test"
        );
    }
}

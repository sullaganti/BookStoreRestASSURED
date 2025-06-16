package BookStore.Positive;

import Utilities.POJO.response.GET_SigninWithValidCredentials;
import Utilities.POJO.response.POST_signUpWithValidCredentials;
import base.BaseClass;
import org.testng.annotations.Test;

import static Utilities.ReUsableUtils.getBookStoreRequestBody;
import static Utilities.ReUsableUtils.logAssertionResult;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class RegistrationTests extends BaseClass {

    @Test(priority = 1)
    public void POST_signUpWithValidCredentials() throws Exception {

        POST_signUpWithValidCredentials response =
                given()
                        .spec(bookStoreRequestSpec())
                        .body(getBookStoreRequestBody("registration", "valid")).
                when()
                        .post("/signup").
                then()
                        .statusCode(200)
                        .extract()
                        .response()
                        .as(POST_signUpWithValidCredentials.class);

        assertThat(response.getMessage()).isEqualTo("User created successfully");

    }

    @Test(priority = 2,dependsOnMethods = "POST_signUpWithValidCredentials")
    public void POST_SigninWithValidCredentials() throws Exception {

        GET_SigninWithValidCredentials response =
                given()
                        .spec(bookStoreRequestSpec())
                        .body(getBookStoreRequestBody("login", "valid")).
                when()
                        .post("/login").
                then()
                        .statusCode(200)
                        .extract()
                        .response()
                        .as(GET_SigninWithValidCredentials.class);

        logAssertionResult(
                assertThat(response.getAccess_token()).isNotEmpty(),"Access Token Should not be not empty");
        logAssertionResult(
                assertThat(response.getToken_type()).isEqualTo("bearer"),"Token Type Should be bearer");

    }

}

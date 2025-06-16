package BookStore.Negative;

import Utilities.POJO.response.BadRequest;
import base.BaseClass;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static Utilities.ReUsableUtils.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class BooksNegativeTests extends BaseClass {

    @Test(priority = 1)
    public void POST_createBookWithMissingName() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated())
                        .body(getBookStoreRequestBody("books", "missingName")).
                when()
                        .post("/books/").
                then()
                        .statusCode(422)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.jsonPath().getString("detail")).isEqualTo("Name is Required")
        );
    }

    @Test(priority = 2)
    public void POST_createBookWithMissingAuthor() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated())
                        .body(getBookStoreRequestBody("books", "missingAuthor")).
                when()
                        .post("/books/").
                then()
                        .statusCode(422)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.jsonPath().getString("detail")).isEqualTo("Author cannot be Missing")
        );
    }

    @Test(priority = 3)
    public void POST_createBookWithMissingYear() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated())
                        .body(getBookStoreRequestBody("books", "missingYear")).
                when()
                        .post("/books/").
                then()
                        .statusCode(422)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.jsonPath().getString("detail")).isEqualTo("Year field is required")
        );
    }

    @Test(priority = 4)
    public void POST_createBookWithMissingSummary() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated())
                        .body(getBookStoreRequestBody("books", "missingSummary")).
                when()
                        .post("/books/").
                then()
                        .statusCode(422)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.jsonPath().getString("detail")).isEqualTo("Summary Field is required")
        );
    }

    @Test(priority = 6)
    public void POST_createBookWithEmptyName() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated())
                        .body(getBookStoreRequestBody("books", "emptyName")).
                when()
                        .post("/books/").
                then()
                        .statusCode(422)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.jsonPath().getString("detail")).isEqualTo("Name cannot be Empty")
        );
    }

    @Test(priority = 7)
    public void POST_createBookWithEmptyAuthor() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated())
                        .body(getBookStoreRequestBody("books", "emptyAuthor")).
                when()
                        .post("/books/").
                then()
                        .statusCode(422)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.jsonPath().getString("detail")).isEqualTo("Author cannot be empty")
        );
    }

    @Test(priority = 8)
    public void POST_createBookWithEmptySummary() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated())
                        .body(getBookStoreRequestBody("books", "emptySummary")).
                when()
                        .post("/books/").
                then()
                        .statusCode(422)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.jsonPath().getString("detail")).isEqualTo("Summary cannot be null")
        );
    }

    @Test(priority = 9)
    public void POST_createBookWithNullName() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated())
                        .body(getBookStoreRequestBody("books", "nullName")).
                when()
                        .post("/books/").
                then()
                        .statusCode(422)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.jsonPath().getString("detail")).isEqualTo("Name cannot be null")
        );
    }

    @Test(priority = 10)
    public void POST_createBookWithNullAuthor() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated())
                        .body(getBookStoreRequestBody("books", "nullAuthor")).
                when()
                        .post("/books/").
                then()
                        .statusCode(422)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.jsonPath().getString("detail")).isEqualTo("Author cannot be null")
        );
    }

    @Test(priority = 11)
    public void POST_createBookWithInvalidYear() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated())
                        .body(getBookStoreRequestBody("books", "invalidYear")).
                when()
                        .post("/books/").
                then()
                        .statusCode(422)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.jsonPath().getString("detail")).isEqualTo("Year Should not a negative number")
        );
    }

    @Test(priority = 12)
    public void POST_createBookWithFutureYear() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated())
                        .body(getBookStoreRequestBody("books", "futureYear")).
                when()
                        .post("/books/").
                then()
                        .statusCode(422)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.jsonPath().getString("detail")).isEqualTo("Year Should not be in the future")
        );
    }

    @Test(priority = 13)
    public void POST_createBookWithStringYear() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated())
                        .body(getBookStoreRequestBody("books", "stringYear")).
                when()
                        .post("/books/").
                then()
                        .statusCode(422)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.jsonPath().getString("detail")).isEqualTo("Year should be a valid integer")
        );
    }

    @Test(priority = 14)
    public void POST_createBookWithNegativeId() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated())
                        .body(getBookStoreRequestBody("books", "negativeId")).
                when()
                        .post("/books/").
                then()
                        .statusCode(422)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.jsonPath().getString("detail")).isEqualTo("Book ID should be a valid integer")
        );
    }

    @Test(priority = 15)
    public void POST_createBookWithStringId() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated())
                        .body(getBookStoreRequestBody("books", "stringId")).
                when()
                        .post("/books/").
                then()
                        .statusCode(422)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.jsonPath().getString("detail")).isEqualTo("Book ID should be a valid integer")
        );
    }

    @Test(priority = 16)
    public void POST_createBookWithLongName() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated())
                        .body(getBookStoreRequestBody("books", "longName")).
                when()
                        .post("/books/").
                then()
                        .statusCode(422)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.jsonPath().getString("detail")).isEqualTo("Name is too long, maximum length is 100 characters")
        );
    }

    @Test(priority = 19)
    public void POST_createBookWithEmptyJSON() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated())
                        .body("{}").
                when()
                        .post("/books/").
                then()
                        .statusCode(422)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.jsonPath().getString("detail")).isEqualTo("Invalid request body")
        );
    }

    @Test(priority = 20)
    public void POST_createBookWithMalformedJSON() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated())
                        .body("{ invalid json }").
                when()
                        .post("/books/").
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

    // GET /books/{book_id} negative tests
    @Test(priority = 21)
    public void GET_getBookByInvalidId() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated()).
                when()
                        .get("/books/"+generateRandomNumericString(10)).
                then()
                        .statusCode(404)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.jsonPath().getString("detail")).isEqualTo("Book not found")
        );
    }

    @Test(priority = 22)
    public void GET_getBookByStringId() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated()).
                when()
                        .get("/books/abc").
                then()
                        .statusCode(422)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.body().as(BadRequest.class).getDetail().get(0))
                        .satisfies(detail -> {
                            assertThat(detail.getType()).isEqualTo("int_parsing");
                            assertThat(detail.getMsg()).isEqualTo("Input should be a valid integer, unable to parse string as an integer");
                            assertThat(detail.getInput()).isEqualTo("abc");
                        })
        );
    }

    @Test(priority = 23)
    public void GET_getBookByNegativeId() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated()).
                when()
                        .get("/books/-1").
                then()
                        .statusCode(422)
                        .extract()
                        .response();
        logAssertionResult(
                assertThat(response.jsonPath().getString("detail")).isEqualTo("Book ID cant be negative")
        );

    }

    @Test(priority = 24)
    public void GET_getBookByZeroId() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated()).
                when()
                        .get("/books/0").
                then()
                        .statusCode(404)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.jsonPath().getString("detail")).isEqualTo("Book not found")
        );
    }

    @Test(priority = 25)
    public void PUT_updateBookWithInvalidId() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated())
                        .body(getBookStoreRequestBody("books", "valid")).
                when()
                        .put("/books/999999").
                then()
                        .statusCode(404)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.jsonPath().getString("detail")).isEqualTo("Book not found")
        );
    }

    @Test(priority = 26)
    public void PUT_updateBookWithStringId() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated())
                        .body(getBookStoreRequestBody("books", "valid")).
                when()
                        .put("/books/abc").
                then()
                        .statusCode(422)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.body().as(BadRequest.class).getDetail().get(0))
                        .satisfies(detail -> {
                            assertThat(detail.getType()).isEqualTo("int_parsing");
                            assertThat(detail.getMsg()).isEqualTo("Input should be a valid integer, unable to parse string as an integer");
                            assertThat(detail.getInput()).isEqualTo("abc");
                        })
        );
    }

    @Test(priority = 27)
    public void PUT_updateBookWithMissingName() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated())
                        .body(getBookStoreRequestBody("books", "missingName")).
                when()
                        .put("/books/1").
                then()
                        .statusCode(422)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.jsonPath().getString("detail")).isEqualTo("Invalid request body")
        );
    }

    @Test(priority = 28)
    public void PUT_updateBookWithEmptyJSON() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated())
                        .body("{}").
                when()
                        .put("/books/1").
                then()
                        .statusCode(422)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.jsonPath().getString("detail")).isEqualTo("Invalid request body")
        );
    }

    @Test(priority = 29)
    public void PUT_updateBookWithInvalidYear() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated())
                        .body(getBookStoreRequestBody("books", "invalidYear")).
                when()
                        .put("/books/1").
                then()
                        .statusCode(422)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.jsonPath().getString("detail")).isEqualTo("Invalid year")
        );
    }

    // DELETE /books/{book_id} negative tests
    @Test(priority = 30)
    public void DELETE_deleteBookWithInvalidId() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated()).
                when()
                        .delete("/books/999999").
                then()
                        .statusCode(404)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.jsonPath().getString("detail")).isEqualTo("Book not found")
        );
    }

    @Test(priority = 31)
    public void DELETE_deleteBookWithStringId() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated()).
                when()
                        .delete("/books/abc").
                then()
                        .statusCode(422)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.body().as(BadRequest.class).getDetail().get(0))
                        .satisfies(detail -> {
                            assertThat(detail.getType()).isEqualTo("int_parsing");
                            assertThat(detail.getMsg()).isEqualTo("Input should be a valid integer, unable to parse string as an integer");
                            assertThat(detail.getInput()).isEqualTo("abc");
                        })
        );
    }

    @Test(priority = 32)
    public void DELETE_deleteBookWithNegativeId() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated()).
                when()
                        .delete("/books/-1").
                then()
                        .statusCode(422)
                        .extract()
                        .response();

    }

    @Test(priority = 33)
    public void DELETE_deleteBookWithZeroId() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated()).
                when()
                        .delete("/books/0").
                then()
                        .statusCode(404)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.statusCode()).isEqualTo(404),
                "Status code should be 404 for deleting book with zero ID"
        );
    }

    @Test(priority = 34)
    public void DELETE_deleteAlreadyDeletedBook() throws Exception {
        // First create a book
        Response createResponse =
                given()
                        .spec(bookStoreRequestSpec_Authenticated())
                        .body(getBookStoreRequestBody("books", "valid")).
                when()
                        .post("/books/").
                then()
                        .statusCode(200)
                        .extract()
                        .response();

        int bookId = createResponse.jsonPath().getInt("id");

        // Delete the book
        given()
                .spec(bookStoreRequestSpec_Authenticated()).
        when()
                .delete("/books/" + bookId).
        then()
                .statusCode(200);

        // Try to delete the same book again
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated()).
                when()
                        .delete("/books/" + bookId).
                then()
                        .statusCode(404)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.jsonPath().getString("detail")).isEqualTo("Book not found")
        );
    }

    // Additional edge case tests
    @Test(priority = 35)
    public void POST_createBookWithWrongContentType() throws Exception {
        try {
            given()
                    .spec(bookStoreRequestSpec_Authenticated())
                    .header("Content-Type", "application/xml")
                    .body(getBookStoreRequestBody("books", "valid")).
            when()
                    .post("/books/").
            then()
                    .statusCode(422)
                    .extract()
                    .response();
        }
        catch (IllegalStateException e)
        {
            logAssertionResult(
                    assertThat(e.getMessage()).contains("java.lang.IllegalStateException: Cannot serialize object because no XML serializer found in classpath. Please put a JAXB or JakartaEE compliant object mapper in classpath."),
                    "Exception should be thrown for wrong content type"
            );
        }

        logAssertionResult(
                assertThat(true).isFalse(),
                "Wrong Content-Type should not be accepted by the API"
        );

    }

    @Test(priority = 36)
    public void GET_getBooksWithInvalidEndpoint() {
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated()).
                when()
                        .get("/book/").  // Note: singular instead of plural
                then()
                        .statusCode(404)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.statusCode()).isEqualTo(404),
                "Status code should be 404 for invalid endpoint"
        );
    }


}

package BookStore.Positive;

import base.BaseClass;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static Utilities.ReUsableUtils.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class BooksTests extends BaseClass {

    private static int createdBookId;

    @Test(priority = 1)
    public void POST_createBookWithValidData() throws Exception {
        System.setProperty("restassured.log.request.headers", "true");
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated())
                        .body(getBookStoreRequestBody("books", "valid")).
                when()
                        .post("/books/").
                then()
                        .statusCode(200)
                        .extract()
                        .response();

        // Store the created book ID for subsequent tests
        createdBookId = response.jsonPath().getInt("id");
        
        logAssertionResult(
                assertThat(createdBookId).isNotNull(),
                "Created book ID should not be null"
        );
        
        logAssertionResult(
                assertThat(response.jsonPath().getString("name")).isEqualTo("KJ"),
                "Book name should be 'KJ'"
        );
        
        logAssertionResult(
                assertThat(response.jsonPath().getString("author")).isEqualTo("Srin"),
                "Book author should be 'Srin'"
        );
        
        logAssertionResult(
                assertThat(response.jsonPath().getInt("published_year")).isEqualTo(2024),
                "Book published year should be 2024"
        );
    }

    @Test(priority = 2, dependsOnMethods = "POST_createBookWithValidData")
    public void GET_getAllBooks() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated()).
                when()
                        .get("/books/").
                then()
                        .statusCode(200)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.jsonPath().getList("$")).isNotNull(),
                "Books list should not be null"
        );
    }

    @Test(priority = 3, dependsOnMethods = "POST_createBookWithValidData")
    public void GET_getBookById() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated()).
                when()
                        .get("/books/" + createdBookId).
                then()
                        .statusCode(200)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.jsonPath().getInt("id")).isEqualTo(createdBookId),
                "Retrieved book ID should match created book ID"
        );
        
        logAssertionResult(
                assertThat(response.jsonPath().getString("name")).isEqualTo("KJ"),
                "Retrieved book name should be 'KJ'"
        );
        
        logAssertionResult(
                assertThat(response.jsonPath().getString("author")).isEqualTo("Srin"),
                "Retrieved book author should be 'Srin'"
        );
    }

    @Test(priority = 4, dependsOnMethods = "GET_getBookById")
    public void PUT_updateBookWithValidData() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated())
                        .body(getBookStoreRequestBody("books", "validUpdate")).
                when()
                        .put("/books/" + createdBookId).
                then()
                        .statusCode(200)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.jsonPath().getString("name")).isEqualTo("Updated Book"),
                "Updated book name should be 'Updated Book'"
        );
        
        logAssertionResult(
                assertThat(response.jsonPath().getString("author")).isEqualTo("Updated Author"),
                "Updated book author should be 'Updated Author'"
        );
        
        logAssertionResult(
                assertThat(response.jsonPath().getInt("published_year")).isEqualTo(2025),
                "Updated book published year should be 2025"
        );
    }

    @Test(priority = 5, dependsOnMethods = "PUT_updateBookWithValidData")
    public void GET_getUpdatedBook() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated()).
                when()
                        .get("/books/" + createdBookId).
                then()
                        .statusCode(200)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.jsonPath().getString("name")).isEqualTo("Updated Book"),
                "Retrieved updated book name should be 'Updated Book'"
        );
        
        logAssertionResult(
                assertThat(response.jsonPath().getString("author")).isEqualTo("Updated Author"),
                "Retrieved updated book author should be 'Updated Author'"
        );
        
        logAssertionResult(
                assertThat(response.jsonPath().getInt("published_year")).isEqualTo(2025),
                "Retrieved updated book published year should be 2025"
        );
    }

    @Test(priority = 6, dependsOnMethods = "GET_getUpdatedBook")
    public void DELETE_deleteBookById() {
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated()).
                when()
                        .delete("/books/" + createdBookId).
                then()
                        .statusCode(200)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.jsonPath().getString("message")).isEqualTo("Book deleted successfully")
        );
    }

    @Test(priority = 7, dependsOnMethods = "DELETE_deleteBookById")
    public void GET_getDeletedBook_shouldReturn404() {
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated()).
                when()
                        .get("/books/" + createdBookId).
                then()
                        .statusCode(404)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.statusCode()).isEqualTo(404),
                "Status code should be 404 for deleted book"
        );
    }

    @Test(priority = 8)
    public void POST_createMultipleBooks() throws Exception {
        // Create first book
        Response response1 =
                given()
                        .spec(bookStoreRequestSpec_Authenticated())
                        .body(getBookStoreRequestBody("books", "valid")).
                when()
                        .post("/books/").
                then()
                        .statusCode(200)
                        .extract()
                        .response();

        // Create second book with different data
        Response response2 =
                given()
                        .spec(bookStoreRequestSpec_Authenticated())
                        .body(getBookStoreRequestBody("books", "unicodeCharacters")).
                when()
                        .post("/books/").
                then()
                        .statusCode(200)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response1.jsonPath().getInt("id")).isNotNull(),
                "First book ID should not be null"
        );
        
        logAssertionResult(
                assertThat(response2.jsonPath().getInt("id")).isNotNull(),
                "Second book ID should not be null"
        );
    }

    @Test(priority = 9)
    public void GET_getAllBooksAfterMultipleCreation() {
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated()).
                when()
                        .get("/books/").
                then()
                        .statusCode(200)
                        .extract()
                        .response();

        logAssertionResult(
                assertThat(response.jsonPath().getList("$")).isNotNull(),
                "Books list should not be null"
        );
        
        logAssertionResult(
                assertThat(response.jsonPath().getList("$").size()).isGreaterThanOrEqualTo(2),
                "Should have at least 2 books in the list"
        );
    }


    @Test(priority = 10)
    public void POST_createBookWithUnicodeCharacters() throws Exception {
        Response response =
                given()
                        .spec(bookStoreRequestSpec_Authenticated())
                        .body(getBookStoreRequestBody("books", "unicodeCharacters")).
                when()
                        .post("/books/").
                then()
                        .statusCode(200)
                        .extract()
                        .response();

    }
}

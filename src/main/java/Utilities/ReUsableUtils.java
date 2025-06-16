package Utilities;

import base.BaseClass;
import io.qameta.allure.Allure;
import io.restassured.path.json.JsonPath;
import lombok.SneakyThrows;
import org.assertj.core.api.AbstractAssert;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;

import static io.qameta.allure.model.Status.FAILED;
import static io.qameta.allure.model.Status.PASSED;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ReUsableUtils extends BaseClass {

    private static final SecureRandom random = new SecureRandom();

    public static String generateRandomString(int length) {

        String text = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            sb.append(text.charAt(random.nextInt(text.length())));
        return sb.toString();

    }

    public static String generateRandomNumericString(int length) {
        String textnumber = "0123456789";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            sb.append(textnumber.charAt(random.nextInt(textnumber.length())));
        return sb.toString();

    }

    public static JSONObject Convert_Json_To_JsonObj(String Filename) throws IOException, ParseException {
        String filePath="src/test/resources/requestJson/";
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(filePath+Filename));
        JSONObject inputpayload = (JSONObject) obj;
        return inputpayload;
    }


    public static JsonPath Convert_Json_To_JsonPath(String Filename) throws Exception {

        String json = new String(Files.readAllBytes(Paths.get(Filename)));

        JsonPath js = new JsonPath(json);
        return js;
    }


    @SneakyThrows
    public static <T> void logAssertionResult(AbstractAssert<?, T> assertion, String description) {
        try {
            assertThat(assertion);  // Perform the assertion

            // If the assertion passes, log a success step in Allure
            Allure.step("Assertion Passed: " + description,PASSED);
        } catch (AssertionError e) {
            // If the assertion fails, log a failure step in Allure
            Allure.step("Assertion Failed: " + description + "\n" + e.getMessage(),FAILED);
            throw e;  // Re-throw the assertion error to mark the test as failed
        }
    }

    @SneakyThrows
    public static <T> void logAssertionResult(AbstractAssert<?, T> assertion) {
        logAssertionResult(assertion, "");
    }

    @SneakyThrows
    public static void logAssertionResult(Runnable assertion) {
        try {
            assertion.run();
            Allure.step("Assertion Passed", PASSED);
        } catch (AssertionError e) {
            Allure.step("Assertion Failed: " + e.getMessage(), FAILED);
            throw e;
        }
    }

    /**
     * Gets a specific request body from BookStoreRequests.json
     * @param category The category (e.g., "login", "registration")
     * @param testType The test type (e.g., "valid", "missingEmail", etc.)
     * @return JSONObject representing the request body
     */
    public static JSONObject getBookStoreRequestBody(String category, String testType) throws IOException, ParseException {
        JSONObject allRequests = Convert_Json_To_JsonObj("BookStoreRequests.json");
        JSONObject categoryRequests = (JSONObject) allRequests.get(category);
        JSONObject requestBody = (JSONObject) categoryRequests.get(testType);
        
        // Ensure the JSONObject is properly formatted for RestAssured
        return requestBody;
    }

    /**
     * Gets a specific request body from BookStoreRequests.json as a String
     * @param category The category (e.g., "login", "registration")
     * @param testType The test type (e.g., "valid", "missingEmail", etc.)
     * @return String representing the JSON request body
     */
    public static String getBookStoreRequestBodyAsString(String category, String testType) throws IOException, ParseException {
        JSONObject requestBody = getBookStoreRequestBody(category, testType);
        return requestBody.toJSONString();
    }

}

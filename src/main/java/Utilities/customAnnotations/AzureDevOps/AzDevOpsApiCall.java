package Utilities.customAnnotations.AzureDevOps;

import io.restassured.response.Response;

import java.io.File;

import static io.restassured.RestAssured.given;

public class AzDevOpsApiCall {
    private static final String ISSUE_TRACKER_API_BASE_URL = "https://dev.azure.com/ORG_NAME/PROJECT_NAME/_apis/wit/workitems";
    private static final String Username = "YOUR_EMAILID";
    public static final String Token = "YOUR_API_TOKEN";


    public static String getWorkItemStatus(int IssueID) {
        Response response = given()
                .header("Accept", "application/json")
                .queryParam("api-version","6.1-preview.3")
                .auth().preemptive().basic(Username, Token)
                .baseUri(ISSUE_TRACKER_API_BASE_URL)
                .when()
                .get(IssueID+"?api-version=6.1-preview.3")
                .then()
                .statusCode(200).extract().response();
        return response.jsonPath().getString("fields.'System.State'");


    }

    public static String getWorkItemType(String IssueID) {
        Response response = given()
                .header("Accept", "application/json")
                .queryParam("api-version","6.1-preview.3")
                .auth().preemptive().basic(Username, Token)
                .baseUri(ISSUE_TRACKER_API_BASE_URL)
                .when()
                .get(IssueID+"?api-version=6.1-preview.3")
                .then()
                .statusCode(200).extract().response();
     return response.jsonPath().getString("fields.'System.WorkItemType'");



    }

    public static void AddScreenshot(String IssueID,String filename)
    {
       given().log().all()
                .header("X-Atlassian-Token","no-check")
                .auth().preemptive().basic(Username,Token)
                .queryParam("fields","status")
                .baseUri(ISSUE_TRACKER_API_BASE_URL)
                .multiPart(new File(filename))
                .when()
                .post(IssueID+"/attachments")
                .then()
                .log().all()
                .statusCode(200).extract().response();


    }

}

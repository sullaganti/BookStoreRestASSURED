package listeners.MicrosoftTeams;

import io.restassured.RestAssured;
import org.testng.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static listeners.MicrosoftTeams.AdaptiveCardReqBody.FactsItem;

public class TeamsListener implements ITestListener, ISuiteListener, IInvokedMethodListener {

    Integer PassedCount;
    Integer FailedCount;

    @Override
    public void onTestSuccess(ITestResult result) {
        PassedCount++;
    }

    @Override
    public void onTestFailure(ITestResult result) {
        FailedCount++;
    }

    @Override
    public void onStart(ISuite suite) {
        ISuiteListener.super.onStart(suite);
        PassedCount=0;
        FailedCount=0;
    }

    @Override
    public void onFinish(ISuite suite) {
        long testCount = 0 ;
        List<ITestNGMethod> testMethods = null;
        testMethods  = suite.getAllMethods();
        testCount = testMethods.size();

        Calendar calendar = Calendar.getInstance();
        List<FactsItem> factsItems = new ArrayList<>();
        factsItems.add(FactsItem
                .builder()
                .title("MODULE")
                .value("**API**").build());

        factsItems.add(FactsItem
                .builder()
                .title("RUN DATE")
                .value("**"+calendar.getTime()+"**").build());

        factsItems.add( FactsItem
                .builder()
                .title("TOTAL")
                .value("**"+testCount+"**").build());

        factsItems.add( FactsItem
                .builder()
                .title("PASSED")
                .value("**"+PassedCount+"**").build());

        factsItems.add( FactsItem
                .builder()
                .title("FAILED")
                .value("**"+FailedCount+"**").build());

        Builder builder = new Builder(factsItems);
        RestAssured.baseURI="https://your.webhook.office.com/webhookb2/4af5c1b";
        RestAssured.given().log().body()
                .body(builder.reqBody())
                .when()
                .post()
                .then().log().body()
                .statusCode(200);
    }

}

package listeners.Extent;

import Utilities.customAnnotations.AzureDevOps.AzureDevOps;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import lombok.SneakyThrows;
import org.testng.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ExtentListener implements ITestListener, ISuiteListener,IInvokedMethodListener {
    ExtentReports extent = ExtentReporterCls.ReportGenerator();
    ExtentTest test;
    ExtentTest node;
    private ByteArrayOutputStream request = new ByteArrayOutputStream();
    private  ByteArrayOutputStream response = new ByteArrayOutputStream();
    private PrintStream requestVar = new PrintStream(request,true);
    private PrintStream responseVar = new PrintStream(response,true);

    private static final ThreadLocal<ExtentTest> LocalThread = new ThreadLocal<ExtentTest>();

    public void logInfo(String message)
    {
        LocalThread.get().info(message);
    }

    public void logJsoninfo(String json)
    {
        LocalThread.get().info(MarkupHelper.createCodeBlock(json, CodeLanguage.JSON));
    }

    public void logReqAndResponse(String json1,String json2)
    {
        LocalThread.get().info(MarkupHelper.createCodeBlocks(new String[]{json1, json2}));
    }

    @SneakyThrows
    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        IInvokedMethodListener.super.afterInvocation(method, testResult);
        addLinkInTest(method);
    }

    @Override
    public void onTestStart(ITestResult result) {
        node = test.createNode(result.getMethod().getMethodName());
        LocalThread.set(node);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LocalThread.get().log(Status.PASS,"------------TEST CASE PASSED------------");
        LocalThread.get().info(MarkupHelper.createCodeBlocks(new String[]{request.toString().trim(),response.toString().trim()}));
        //   request.reset();
        //   response.reset();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LocalThread.get().info(MarkupHelper.createCodeBlocks(new String[]{request.toString().trim(), response.toString().trim()}));
        LocalThread.get().fail(result.getThrowable());
        //  request.reset();
        // response.reset();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LocalThread.get().log(Status.INFO,"------------TEST CASE SKIPPED------------");
        LocalThread.get().skip(result.getThrowable());

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        LocalThread.get().log(Status.INFO,"-------TEST CASE FAILED DUE TO TIMEOUT------");

    }

    @Override
    public void onStart(ITestContext context) {

        RestAssured.filters(new RequestLoggingFilter(LogDetail.ALL,requestVar),new ResponseLoggingFilter(LogDetail.ALL,responseVar));
        test= extent.createTest(context.getName());
    }

    public void addLinkInTest(IInvokedMethod method) {
        AzureDevOps AzureDevOps = method.getTestMethod()
                .getConstructorOrMethod()
                .getMethod()
                .getAnnotation(AzureDevOps.class);
        try {
            LocalThread.get().log(Status.INFO,"<a href=https://dev.azure.com/ORG/Project/_workitems/edit/"+ AzureDevOps.workItemID()+" target='_blank'>AZ-WorkItem</a>");
        }
        catch (Exception e){
            //Do Nothing
        }
    }

    @SneakyThrows
    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
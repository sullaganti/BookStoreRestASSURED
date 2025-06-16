package listeners.Allure;

import Utilities.customAnnotations.AzureDevOps.AzureDevOps;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import lombok.SneakyThrows;
import org.testng.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static io.qameta.allure.Allure.step;
import static io.qameta.allure.model.Status.FAILED;

public class AllureListener extends TestListenerAdapter implements ISuiteListener, ITestListener, IInvokedMethodListener {
	private ByteArrayOutputStream request = new ByteArrayOutputStream();
	private final PrintStream requestVar = new PrintStream(request, true);
	private ByteArrayOutputStream response = new ByteArrayOutputStream();
	private final PrintStream responseVar = new PrintStream(response, true);

	@SneakyThrows
	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		IInvokedMethodListener.super.afterInvocation(method, testResult);
		if (!method.isConfigurationMethod()) {
			logRequest(request);
			logResponse(response);
		}
	}

	@Attachment(value = "request", type = "text/plain")
	public byte[] logRequest(final ByteArrayOutputStream stream) {
		return attach(stream);
	}

	@Attachment(value = "response", type = "text/plain")
	public byte[] logResponse(final ByteArrayOutputStream stream) {
		return attach(stream);
	}

	public byte[] attach(final ByteArrayOutputStream log) {
		final byte[] array = log.toByteArray();
		log.reset();
		return array;
	}


	@Override
	public void onStart(ISuite suite) {
		ISuiteListener.super.onStart(suite);
		RestAssured.filters(new RequestLoggingFilter(LogDetail.ALL, requestVar), new ResponseLoggingFilter(LogDetail.ALL, responseVar));
		//RestAssured.filters(new ReportPortalRestAssuredLoggingFilter(42, LogLevel.INFO));
	}

	@Override
	public void onFinish(ISuite suite) {
		ISuiteListener.super.onFinish(suite);
		RestAssured.reset();
	}

	@Override
	public void onTestFailure(ITestResult result) {
		Throwable throwable = result.getThrowable();
		if (throwable instanceof AssertionError) {
			step("Assertion Failed: " + throwable.getMessage(), FAILED);
		}
	}

}
package Utilities.customAnnotations.AzureDevOps;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

public class AzIssueStatusListener implements IInvokedMethodListener {

    public static final String str_READY_TO_TEST ="Ready To Test";
    public static final String New ="New";
    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {

    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {

        AzureDevOps AzIssueID = method.getTestMethod()
                .getConstructorOrMethod()
                .getMethod()
                .getAnnotation(AzureDevOps.class);

        if (null != AzIssueID) {
            if(str_READY_TO_TEST.equalsIgnoreCase(
                    AzDevOpsApiCall.getWorkItemStatus(AzIssueID.bugID()))){
                switch(testResult.getStatus()){
                    case ITestResult.FAILURE:
                        // no need to fail as we might have expected this already.
                        testResult.setStatus(ITestResult.SKIP);
                        break;
                    case ITestResult.SUCCESS:
                        // TO-DO Write Code to Move the AzDevOps Ticket from Ready to Test to Tested/Done
                        break;
                }
            }

            if(New.equalsIgnoreCase(
                    AzDevOpsApiCall.getWorkItemStatus(AzIssueID.bugID()))){
                switch(testResult.getStatus()){
                    case ITestResult.FAILURE:
                        // no need to fail as we might have expected this already.
                        testResult.setStatus(ITestResult.SKIP);
                        break;
                    case ITestResult.SUCCESS:
                        // TO-DO Write Code to Move the AzDevOps Ticket from Ready to Test to Tested/Done
                        break;
                }
            }
        }
    }


}

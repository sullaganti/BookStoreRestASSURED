package listeners.Extent;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.ViewName;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReporterCls {
    static ExtentReports extent;    public static ExtentReports ReportGenerator()
    {
        Date d = new Date();
        SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MMM-dd_HH_mm");
        String ReportFolderName = SDF.format(d);
        
        // Create ExtentReports directory if it doesn't exist
        String reportDir = System.getProperty("user.dir") + "\\ExtentReports";
        File dir = new File(reportDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        String path = reportDir + "\\ExtentReport.html";

        ExtentSparkReporter ESR = new ExtentSparkReporter(path).viewConfigurer()
                .viewOrder()
                .as(new ViewName[] { ViewName.DASHBOARD, ViewName.TEST, ViewName.EXCEPTION,ViewName.LOG  })
                .apply();
        ESR.config().setReportName("Test Automation Results");
        // ESR.config().setDocumentTitle("Test Results");
        extent = new ExtentReports();
        extent.attachReporter(ESR);
        extent.setSystemInfo("Tester",System.getProperty("user.name"));
        return extent;

    }

}

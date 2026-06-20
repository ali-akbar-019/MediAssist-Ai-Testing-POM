package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.ReportPage;

public class TC_007_ReportTest extends BaseClass {

    @Test(groups = { "smoke", "regression" })
    public void TC_007_Report_Smoke() {
        String testCase = "TC_007_Report_Smoke";
        logInfo("TEST STARTED: " + testCase);

        driver.get(p.getProperty("appURL") + "/reports");

        Assert.assertTrue(waitForUrlContains("reports", 5), "Reports route not reached");

        ReportPage rp = new ReportPage(driver);
        Assert.assertTrue(rp.isHeadingVisible(), "Reports heading not visible via page object");

        logInfo("TEST PASSED: " + testCase);
    }

}

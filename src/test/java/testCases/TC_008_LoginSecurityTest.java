package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.LoginPage;

public class TC_008_LoginSecurityTest extends BaseClass {

    LoginPage lp;

    @Test(groups = { "regression" })
    public void TC_008_Login_SQLi_Email() {
        String testCase = "TC_008_Login_SQLi_Email";
        logInfo("TEST STARTED: " + testCase);

        driver.get(p.getProperty("appURL") + "/login");
        lp = new LoginPage(driver);

        // SQLi-like email payload
        lp.login("' OR '1'='1@example.com", "somepassword");
        Assert.assertFalse(waitForUrlContains("dashboard", 5), "SQLi in email should not bypass login");

        String err = lp.getErrorMessage();
        Assert.assertTrue(err.length() > 0, "Error message expected for SQLi attempt");

        logInfo("TEST PASSED: " + testCase);
    }

    @Test(groups = { "regression" })
    public void TC_009_Login_SQLi_Password() {
        String testCase = "TC_009_Login_SQLi_Password";
        logInfo("TEST STARTED: " + testCase);

        driver.get(p.getProperty("appURL") + "/login");
        lp = new LoginPage(driver);

        lp.login("nonexistent" + System.currentTimeMillis() + "@example.com", "' OR '1'='1");
        Assert.assertFalse(waitForUrlContains("dashboard", 5), "SQLi in password should not bypass login");

        String err = lp.getErrorMessage();
        Assert.assertTrue(err.length() > 0, "Error message expected for SQLi attempt");

        logInfo("TEST PASSED: " + testCase);
    }

}

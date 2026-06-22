package testCases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.LoginPage;

public class TC_008_LoginSecurityTest extends BaseClass {

	LoginPage lp;
@BeforeMethod
public void setupTest() {
    login();

    try {
        Thread.sleep(2000);
    } catch (InterruptedException e) {
    }

    driver.get(p.getProperty("appURL") + "/login");
    lp = new LoginPage(driver);

    boolean loginPageLoaded = waitForUrlContains("login", 10);

    if (!loginPageLoaded && driver.getCurrentUrl().contains("login")) {
        logInfo("Redirected to login, trying to login again...");
        login();
        driver.get(p.getProperty("appURL") + "/login");
        waitForUrlContains("login", 10);
    }
}

	@Test(groups = { "regression", "edge-case" })
	public void TC_008_Login_SQLi_Email() {
		String testCase = "TC_008_Login_SQLi_Email";
		logInfo("TEST STARTED: " + testCase);

		
		

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

		
		

		lp.login("nonexistent" + System.currentTimeMillis() + "@example.com", "' OR '1'='1");
		Assert.assertFalse(waitForUrlContains("dashboard", 5), "SQLi in password should not bypass login");

		String err = lp.getErrorMessage();
		Assert.assertTrue(err.length() > 0, "Error message expected for SQLi attempt");

		logInfo("TEST PASSED: " + testCase);
	}

}

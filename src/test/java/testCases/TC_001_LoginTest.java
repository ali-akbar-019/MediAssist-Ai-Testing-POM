package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.LoginPage;

public class TC_001_LoginTest extends BaseClass {

	LoginPage lp;

	// =========================
	// TC_001 - VALID LOGIN
	// =========================
	@Test(groups = { "smoke", "regression" })
	public void TC_001_Login_ValidCredentials() {

		String testCase = "TC_001_Login_ValidCredentials";

		logInfo("TEST STARTED: Login Test - " + testCase);

		lp = new LoginPage(driver);

		String email = p.getProperty("email");
		String password = p.getProperty("password");

		logInfo("Entering valid credentials");

		lp.login(email, password);

		logInfo("Login form submitted");

		try {
			Thread.sleep(2000);
		} catch (Exception e) {
		}

		String currentUrl = driver.getCurrentUrl();

		logInfo("Current URL after login: " + currentUrl);

		Assert.assertTrue(currentUrl.contains("dashboard"), "User not redirected to Dashboard");

		logInfo("TEST PASSED: " + testCase);
	}

	// =========================
	// TC_002 - INVALID LOGIN
	// =========================
	@Test(groups = { "regression" })
	public void TC_002_Login_InvalidCredentials() {

		String testCase = "TC_002_Login_InvalidCredentials";

		logInfo("TEST STARTED: Login Test - " + testCase);

		lp = new LoginPage(driver);

		logInfo("Entering invalid credentials");

		lp.login("wrong@test.com", "wrongpass");

		try {
			Thread.sleep(2000);
		} catch (Exception e) {
		}

		String currentUrl = driver.getCurrentUrl();

		logInfo("Current URL: " + currentUrl);

		Assert.assertFalse(currentUrl.contains("dashboard"), "Invalid login should not redirect");

		String error = lp.getErrorMessage();

		logInfo("Error message: " + error);

		Assert.assertTrue(error.length() > 0, "Error message not displayed");

		logInfo("TEST PASSED: " + testCase);
	}

	// =========================
	// TC_003 - EMPTY LOGIN
	// =========================
	@Test(groups = { "regression" })
	public void TC_003_Login_EmptyFields() {

		String testCase = "TC_003_Login_EmptyFields";

		logInfo("TEST STARTED: Login Test - " + testCase);

		lp = new LoginPage(driver);

		logInfo("Submitting empty login form");

		lp.login("", "");

		try {
			Thread.sleep(2000);
		} catch (Exception e) {
		}

		String currentUrl = driver.getCurrentUrl();

		logInfo("Current URL: " + currentUrl);

		Assert.assertFalse(currentUrl.contains("dashboard"), "Empty login should not allow access");

		logInfo("TEST PASSED: " + testCase);
	}
}
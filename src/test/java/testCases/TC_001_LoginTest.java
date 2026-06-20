package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.LoginPage;
import org.openqa.selenium.By;
public class TC_001_LoginTest extends BaseClass {

	LoginPage lp;

	// =========================
	// TC_001 - VALID LOGIN
	// =========================
	@Test(groups = { "smoke", "regression" })
	public void TC_001_Login_ValidCredentials() {

		String testCase = "TC_001_Login_ValidCredentials";

		logInfo("TEST STARTED: Login Test - " + testCase);

		driver.get(p.getProperty("appURL") + "/login");
		lp = new LoginPage(driver);

		String email = p.getProperty("email");
		String password = p.getProperty("password");

		logInfo("Entering valid credentials");

		lp.login(email, password);

		logInfo("Login form submitted");

		boolean redirected = waitForUrlContains("dashboard", 10);
		Assert.assertTrue(redirected, "User not redirected to Dashboard");


		logInfo("TEST PASSED: " + testCase);
	}

	// =========================
	// TC_002 - INVALID LOGIN
	// =========================
	@Test(groups = { "regression" })
	public void TC_002_Login_InvalidCredentials() {

		String testCase = "TC_002_Login_InvalidCredentials";

		logInfo("TEST STARTED: Login Test - " + testCase);

		driver.get(p.getProperty("appURL") + "/login");
		lp = new LoginPage(driver);

		logInfo("Entering invalid credentials");

		lp.login("wrong@test.com", "wrongpass");

		Assert.assertFalse(waitForUrlContains("dashboard", 5), "Invalid login should not redirect");

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

		driver.get(p.getProperty("appURL") + "/login");
		lp = new LoginPage(driver);

		logInfo("Submitting empty login form");

		lp.login("", "");

		Assert.assertFalse(waitForUrlContains("dashboard", 5), "Empty login should not allow access");

		Assert.assertTrue(driver.getCurrentUrl().contains("/login"), "Should remain on login page for empty submission");


		logInfo("TEST PASSED: " + testCase);
	}
	@Test(groups = { "regression" })
public void TC_004_Login_ClientSideValidation() {
    driver.get(p.getProperty("appURL") + "/login");
    lp = new LoginPage(driver);
    
    // Submit empty form
    lp.clickLogin(); // or lp.login("", "");
    
    // Wait for validation errors to appear
    waitForElementVisible(By.cssSelector("[data-testid='login-email-error']"), 5);
    waitForElementVisible(By.cssSelector("[data-testid='login-password-error']"), 5);
    
    Assert.assertTrue(driver.findElement(By.cssSelector("[data-testid='login-email-error']")).isDisplayed());
    Assert.assertTrue(driver.findElement(By.cssSelector("[data-testid='login-password-error']")).isDisplayed());
}
}
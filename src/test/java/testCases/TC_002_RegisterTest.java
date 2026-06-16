package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.RegisterPage;

public class TC_002_RegisterTest extends BaseClass {

	RegisterPage rp;

	// ==============================
	// TC_002 - VALID REGISTRATION
	// ==============================
	@Test(groups = { "smoke", "regression" })
	public void TC_002_Register_ValidUser() {

		String testCase = "TC_002_Register_ValidUser";

		logInfo("TEST STARTED: " + testCase);

		rp = new RegisterPage(driver);

		String uniqueEmail = "user" + System.currentTimeMillis() + "@gmail.com";

		rp.enterName("Ali Akbar");
		rp.enterEmail(uniqueEmail);
		rp.enterPassword("Test@12345");
		rp.clickContinue();

		logInfo("Step 1 completed");

		try {
			Thread.sleep(2000);
		} catch (Exception e) {
		}

		rp.enterAge("25");
		rp.selectGender("//*[@data-testid='gender-option-Male']");
		rp.selectBloodGroup("//*[@data-testid='bloodgroup-option-O+']");
		rp.clickCreateAccount();

		logInfo("Registration submitted");

		try {
			Thread.sleep(3000);
		} catch (Exception e) {
		}

		String url = driver.getCurrentUrl();

		Assert.assertFalse(url.contains("register"), "User should NOT stay on register page after valid signup");

		logInfo("TEST PASSED: " + testCase);
	}

	// ==============================
	// TC_003 - INVALID EMAIL
	// ==============================
	@Test(groups = { "regression" })
	public void TC_003_Register_InvalidEmail() {

		String testCase = "TC_003_Register_InvalidEmail";

		logInfo("TEST STARTED: " + testCase);

		rp = new RegisterPage(driver);

		rp.enterName("Test User");
		rp.enterEmail("invalidemail"); // wrong format
		rp.enterPassword("Test@12345");
		rp.clickContinue();

		try {
			Thread.sleep(2000);
		} catch (Exception e) {
		}

		String url = driver.getCurrentUrl();

		Assert.assertTrue(url.contains("register"), "Invalid email should not proceed to next step");

		logInfo("TEST PASSED: " + testCase);
	}

	// ==============================
	// TC_004 - WEAK PASSWORD
	// ==============================
	@Test(groups = { "regression" })
	public void TC_004_Register_WeakPassword() {

		String testCase = "TC_004_Register_WeakPassword";

		logInfo("TEST STARTED: " + testCase);

		rp = new RegisterPage(driver);

		rp.enterName("Test User");
		rp.enterEmail("user" + System.currentTimeMillis() + "@gmail.com");
		rp.enterPassword("123"); // weak password
		rp.clickContinue();

		try {
			Thread.sleep(2000);
		} catch (Exception e) {
		}

		String url = driver.getCurrentUrl();

		Assert.assertTrue(url.contains("register"), "Weak password should not allow step completion");

		logInfo("TEST PASSED: " + testCase);
	}

	// ==============================
	// TC_005 - EMPTY FIELDS
	// ==============================
	@Test(groups = { "regression" })
	public void TC_005_Register_EmptyFields() {

		String testCase = "TC_005_Register_EmptyFields";

		logInfo("TEST STARTED: " + testCase);

		rp = new RegisterPage(driver);

		rp.enterName("");
		rp.enterEmail("");
		rp.enterPassword("");
		rp.clickContinue();

		try {
			Thread.sleep(2000);
		} catch (Exception e) {
		}

		String url = driver.getCurrentUrl();

		Assert.assertTrue(url.contains("register"), "Empty fields should not proceed");

		logInfo("TEST PASSED: " + testCase);
	}

	// ==============================
	// TC_006 - INVALID NAME (NUMBERS ONLY)
	// ==============================
	@Test(groups = { "regression" })
	public void TC_006_Register_InvalidName() {

		String testCase = "TC_006_Register_InvalidName";

		logInfo("TEST STARTED: " + testCase);

		rp = new RegisterPage(driver);

		rp.enterName("123456"); // invalid name
		rp.enterEmail("user" + System.currentTimeMillis() + "@gmail.com");
		rp.enterPassword("Test@12345");
		rp.clickContinue();

		try {
			Thread.sleep(2000);
		} catch (Exception e) {
		}

		String url = driver.getCurrentUrl();

		Assert.assertTrue(url.contains("register"), "Numeric-only name should be rejected");

		logInfo("TEST PASSED: " + testCase);
	}

	// ==============================
	// TC_007 - MINIMUM NAME LENGTH
	// ==============================
	@Test(groups = { "regression" })
	public void TC_007_Register_ShortName() {

		String testCase = "TC_007_Register_ShortName";

		logInfo("TEST STARTED: " + testCase);

		rp = new RegisterPage(driver);

		rp.enterName("A"); // too short
		rp.enterEmail("user" + System.currentTimeMillis() + "@gmail.com");
		rp.enterPassword("Test@12345");
		rp.clickContinue();

		try {
			Thread.sleep(2000);
		} catch (Exception e) {
		}

		String url = driver.getCurrentUrl();

		Assert.assertTrue(url.contains("register"), "Short name should not be accepted");

		logInfo("TEST PASSED: " + testCase);
	}

	// ==============================
	// TC_008 - SKIP PROFILE FLOW
	// ==============================
	@Test(groups = { "regression" })
	public void TC_008_Register_SkipProfile() {

		String testCase = "TC_008_Register_SkipProfile";

		logInfo("TEST STARTED: " + testCase);

		rp = new RegisterPage(driver);

		String uniqueEmail = "skip" + System.currentTimeMillis() + "@gmail.com";

		rp.enterName("Ali Akbar");
		rp.enterEmail(uniqueEmail);
		rp.enterPassword("Test@12345");
		rp.clickContinue();

		try {
			Thread.sleep(2000);
		} catch (Exception e) {
		}

		logInfo("Skipping profile setup (if allowed by UI)");

		rp.clickCreateAccount(); // directly skip optional fields

		try {
			Thread.sleep(3000);
		} catch (Exception e) {
		}

		String url = driver.getCurrentUrl();

		Assert.assertFalse(url.contains("register"), "User should complete registration even after skipping profile");

		logInfo("TEST PASSED: " + testCase);
	}
}
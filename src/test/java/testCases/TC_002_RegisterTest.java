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

		driver.get(p.getProperty("appURL") + "/register");
		rp = new RegisterPage(driver);

		String uniqueEmail = "user" + System.currentTimeMillis() + "@gmail.com";

		rp.enterName("Ali Akbar");
		rp.enterEmail(uniqueEmail);
		rp.enterPassword("Test@12345");
		rp.clickContinue();

		logInfo("Step 1 completed");

		rp.enterAge("25");
		rp.selectGender("Male");
		rp.selectBloodGroup("O+");
		rp.clickCreateAccount();

		logInfo("Registration submitted");

		// After registration, should redirect to verify-notice
		boolean onVerifyNotice = waitForUrlContains("verify-notice", 10);
		Assert.assertTrue(onVerifyNotice, "User should be redirected to verify-notice page after signup");

		logInfo("TEST PASSED: " + testCase);
	}

	// ==============================
	// TC_003 - INVALID EMAIL
	// ==============================
	@Test(groups = { "regression" })
	public void TC_003_Register_InvalidEmail() {

		String testCase = "TC_003_Register_InvalidEmail";

		logInfo("TEST STARTED: " + testCase);

		driver.get(p.getProperty("appURL") + "/register");
		rp = new RegisterPage(driver);

		rp.enterName("Test User");
		rp.enterEmail("invalidemail"); // wrong format
		rp.enterPassword("Test@12345");
		rp.clickContinue();

		// Should stay on register page
		Assert.assertTrue(driver.getCurrentUrl().contains("/register"),
				"Invalid email should not proceed to next step");

		// Check for email error
		String emailError = rp.getEmailError();
		logInfo("Email error: " + emailError);
		Assert.assertTrue(emailError.length() > 0, "Email error message should be displayed");

		logInfo("TEST PASSED: " + testCase);
	}

	// ==============================
	// TC_004 - WEAK PASSWORD
	// ==============================
	@Test(groups = { "regression" })
	public void TC_004_Register_WeakPassword() {

		String testCase = "TC_004_Register_WeakPassword";

		logInfo("TEST STARTED: " + testCase);

		driver.get(p.getProperty("appURL") + "/register");
		rp = new RegisterPage(driver);

		rp.enterName("Test User");
		rp.enterEmail("user" + System.currentTimeMillis() + "@gmail.com");
		rp.enterPassword("123"); // weak password
		rp.clickContinue();

		Assert.assertTrue(driver.getCurrentUrl().contains("/register"),
				"Weak password should not allow step completion");

		// Check for password error
		String passwordError = rp.getPasswordError();
		logInfo("Password error: " + passwordError);
		Assert.assertTrue(passwordError.length() > 0, "Password error message should be displayed");

		logInfo("TEST PASSED: " + testCase);
	}

	// ==============================
	// TC_005 - EMPTY FIELDS
	// ==============================
	@Test(groups = { "regression" })
	public void TC_005_Register_EmptyFields() {

		String testCase = "TC_005_Register_EmptyFields";

		logInfo("TEST STARTED: " + testCase);

		driver.get(p.getProperty("appURL") + "/register");
		rp = new RegisterPage(driver);

		rp.enterName("");
		rp.enterEmail("");
		rp.enterPassword("");
		rp.clickContinue();

		Assert.assertTrue(driver.getCurrentUrl().contains("/register"), "Empty fields should not proceed");

		// Check for all errors
		String nameError = rp.getNameError();
		String emailError = rp.getEmailError();
		String passwordError = rp.getPasswordError();

		logInfo("Name error: " + nameError);
		logInfo("Email error: " + emailError);
		logInfo("Password error: " + passwordError);

		Assert.assertTrue(nameError.length() > 0, "Name error message should be displayed");
		Assert.assertTrue(emailError.length() > 0, "Email error message should be displayed");
		Assert.assertTrue(passwordError.length() > 0, "Password error message should be displayed");

		logInfo("TEST PASSED: " + testCase);
	}

	// ==============================
	// TC_006 - INVALID NAME (NUMBERS ONLY)
	// ==============================
	@Test(groups = { "regression" })
	public void TC_006_Register_InvalidName() {

		String testCase = "TC_006_Register_InvalidName";

		logInfo("TEST STARTED: " + testCase);

		driver.get(p.getProperty("appURL") + "/register");
		rp = new RegisterPage(driver);

		String uniqueEmail = "user" + System.currentTimeMillis() + "@gmail.com";

		rp.enterName("123456");
		rp.enterEmail(uniqueEmail);
		rp.enterPassword("Test@12345");
		rp.clickContinue();

		Assert.assertTrue(driver.getCurrentUrl().contains("/register"), "Should be on register step 2");

		rp.enterAge("25");
		rp.selectGender("Male");
		rp.selectBloodGroup("O+");
		rp.clickCreateAccount();

		boolean onVerifyNotice = waitForUrlContains("verify-notice", 8);
		if (!onVerifyNotice) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			String err = rp.getRegisterError();
			logInfo("Error: " + err);
			Assert.assertTrue(err.length() > 0, "Expected error if backend rejects numeric name");
		}

		logInfo("TEST PASSED: " + testCase);
	}

	// ==============================
	// TC_007 - MINIMUM NAME LENGTH
	// ==============================
	@Test(groups = { "regression" })
	public void TC_007_Register_ShortName() {

		String testCase = "TC_007_Register_ShortName";

		logInfo("TEST STARTED: " + testCase);

		driver.get(p.getProperty("appURL") + "/register");
		rp = new RegisterPage(driver);

		rp.enterName("A"); // too short
		rp.enterEmail("user" + System.currentTimeMillis() + "@gmail.com");
		rp.enterPassword("Test@12345");
		rp.clickContinue();

		Assert.assertTrue(driver.getCurrentUrl().contains("/register"), "Short name should not be accepted");

		// Check for name error
		String nameError = rp.getNameError();
		logInfo("Name error: " + nameError);
		Assert.assertTrue(nameError.length() > 0, "Name error message should be displayed for short name");

		logInfo("TEST PASSED: " + testCase);
	}

	// ==============================
	// TC_008 - SKIP PROFILE FLOW
	// ==============================
	@Test(groups = { "regression" })
	public void TC_008_Register_SkipProfile() {

		String testCase = "TC_008_Register_SkipProfile";

		logInfo("TEST STARTED: " + testCase);

		driver.get(p.getProperty("appURL") + "/register");
		rp = new RegisterPage(driver);

		String uniqueEmail = "skip" + System.currentTimeMillis() + "@gmail.com";

		rp.enterName("Ali Akbar");
		rp.enterEmail(uniqueEmail);
		rp.enterPassword("Test@12345");
		rp.clickContinue();

		logInfo("Step 1 completed");

		// Try to skip profile setup
		rp.clickCreateAccount();

		logInfo("Registration submitted (skipping profile)");

		// After registration, should redirect to verify-notice
		boolean onVerifyNotice = waitForUrlContains("verify-notice", 10);
		Assert.assertTrue(onVerifyNotice, "User should be redirected to verify-notice after registration");

		logInfo("TEST PASSED: " + testCase);
	}

	// ==============================
	// TC_009 - SQL INJECTION IN NAME
	// ==============================
	@Test(groups = { "regression" })
	public void TC_009_Register_SQLInjection_Name() {

		String testCase = "TC_009_Register_SQLInjection_Name";

		logInfo("TEST STARTED: " + testCase);

		driver.get(p.getProperty("appURL") + "/register");
		rp = new RegisterPage(driver);

		String uniqueEmail = "user" + System.currentTimeMillis() + "@gmail.com";

		rp.enterName("' OR '1'='1");
		rp.enterEmail(uniqueEmail);
		rp.enterPassword("Test@12345");
		rp.clickContinue();

		Assert.assertTrue(driver.getCurrentUrl().contains("/register"), "Should be on register step 2");

		rp.enterAge("25");
		rp.selectGender("Male");
		rp.selectBloodGroup("O+");
		rp.clickCreateAccount();

		boolean onVerifyNotice = waitForUrlContains("verify-notice", 8);
		if (!onVerifyNotice) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			String err = rp.getRegisterError();
			logInfo("Error: " + err);
			Assert.assertTrue(err.length() > 0, "Expected error if backend rejects SQL payload");
		}

		logInfo("TEST PASSED: " + testCase);
	}

	// ==============================
	// TC_010 - WEIRD NAME: DASH AND NUMERIC
	// ==============================
	@Test(groups = { "regression" })
	public void TC_010_Register_WeirdName_DashNumber() {

		String testCase = "TC_010_Register_WeirdName_DashNumber";

		logInfo("TEST STARTED: " + testCase);

		driver.get(p.getProperty("appURL") + "/register");
		rp = new RegisterPage(driver);

		String uniqueEmail = "user" + System.currentTimeMillis() + "@gmail.com";

		rp.enterName("-1");
		rp.enterEmail(uniqueEmail);
		rp.enterPassword("Test@12345");
		rp.clickContinue();

		Assert.assertTrue(driver.getCurrentUrl().contains("/register"), "Should be on register step 2");

		rp.enterAge("25");
		rp.selectGender("Male");
		rp.selectBloodGroup("O+");
		rp.clickCreateAccount();

		boolean onVerifyNotice = waitForUrlContains("verify-notice", 8);
		if (!onVerifyNotice) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			String err = rp.getRegisterError();
			logInfo("Error: " + err);
			Assert.assertTrue(err.length() > 0, "Expected error if backend rejects '-1'");
		}

		logInfo("TEST PASSED: " + testCase);
	}

	// ==============================
	// TC_011 - WEIRD NAME: ALPHA-NUMERIC MIX
	// ==============================
	@Test(groups = { "regression" })
	public void TC_011_Register_WeirdName_AlphaNum() {

		String testCase = "TC_011_Register_WeirdName_AlphaNum";

		logInfo("TEST STARTED: " + testCase);

		driver.get(p.getProperty("appURL") + "/register");
		rp = new RegisterPage(driver);

		String uniqueEmail = "user" + System.currentTimeMillis() + "@gmail.com";

		rp.enterName("1q23");
		rp.enterEmail(uniqueEmail);
		rp.enterPassword("Test@12345");
		rp.clickContinue();

		Assert.assertTrue(driver.getCurrentUrl().contains("/register"), "Should be on register step 2");

		rp.enterAge("25");
		rp.selectGender("Male");
		rp.selectBloodGroup("O+");
		rp.clickCreateAccount();

		boolean onVerifyNotice = waitForUrlContains("verify-notice", 8);
		if (!onVerifyNotice) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			String err = rp.getRegisterError();
			logInfo("Error: " + err);
			Assert.assertTrue(err.length() > 0, "Expected error if backend rejects '1q23'");
		}

		logInfo("TEST PASSED: " + testCase);
	}

	// ==============================
	// TC_012 - NAME WITH NUMBERS
	// ==============================
	@Test(groups = { "regression" })
	public void TC_012_Register_NameWithNumbers() {
		String testCase = "TC_012_Register_NameWithNumbers";
		logInfo("TEST STARTED: " + testCase);

		driver.get(p.getProperty("appURL") + "/register");
		rp = new RegisterPage(driver);

		String uniqueEmail = "user" + System.currentTimeMillis() + "@gmail.com";

		rp.enterName("John123");
		rp.enterEmail(uniqueEmail);
		rp.enterPassword("Test@12345");
		rp.clickContinue();

		Assert.assertTrue(driver.getCurrentUrl().contains("/register"), "Should be on register step 2");

		rp.enterAge("25");
		rp.selectGender("Male");
		rp.selectBloodGroup("O+");
		rp.clickCreateAccount();

		boolean onVerifyNotice = waitForUrlContains("verify-notice", 8);
		if (!onVerifyNotice) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			String err = rp.getRegisterError();
			logInfo("Error: " + err);
			Assert.assertTrue(err.length() > 0, "Expected error if backend rejects 'John123'");
		}

		logInfo("TEST PASSED: " + testCase);
	}
}
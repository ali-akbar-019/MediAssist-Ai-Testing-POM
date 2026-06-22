package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.By;
public class RegisterPage {

	WebDriver driver;

	// Constructor
	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// ================= STEP 1 LOCATORS =================

	@FindBy(css = "[data-testid='register-name']")
	WebElement txtName;

	@FindBy(css = "[data-testid='register-email']")
	WebElement txtEmail;

	@FindBy(css = "[data-testid='register-password']")
	WebElement txtPassword;

	@FindBy(css = "[data-testid='register-continue']")
	WebElement btnContinue;

	// ================= STEP 2 LOCATORS =================

	@FindBy(css = "[data-testid='register-age']")
	WebElement txtAge;

	@FindBy(css = "[data-testid='gender-trigger']")
	WebElement ddlGender;

	@FindBy(css = "[data-testid='bloodgroup-trigger']")
	WebElement ddlBloodGroup;

	@FindBy(css = "[data-testid='register-create']")
	WebElement btnCreateAccount;

	// ================= ACTION METHODS =================

	// Step 1 actions
	public void enterName(String name) {
		txtName.clear();
		txtName.sendKeys(name);
	}

	public void enterEmail(String email) {
		txtEmail.clear();
		txtEmail.sendKeys(email);
	}

	public void enterPassword(String password) {
		txtPassword.clear();
		txtPassword.sendKeys(password);
	}

	public void clickContinue() {
		btnContinue.click();
	}

	public void fillStep1(String name, String email, String password) {
		enterName(name);
		enterEmail(email);
		enterPassword(password);
		clickContinue();
	}

	// Step 2 actions
	public void enterAge(String age) {
		txtAge.clear();
		txtAge.sendKeys(age);
	}

// Change these selectors - using label instead of value
public void selectGender(String genderLabel) {
    ddlGender.click();
    driver.findElement(By.cssSelector("[data-testid='gender-option-" + genderLabel + "']")).click();
}

public void selectBloodGroup(String bloodGroup) {
    ddlBloodGroup.click();
    driver.findElement(By.cssSelector("[data-testid='bloodgroup-option-" + bloodGroup + "']")).click();
}

	public void clickCreateAccount() {
		btnCreateAccount.click();
	}

	public void fillStep2(String age, String genderXpath, String bloodGroupXpath) {
		enterAge(age);
		selectGender(genderXpath);
		selectBloodGroup(bloodGroupXpath);
		clickCreateAccount();
	}

	// Full registration flow
	public void registerFull(String name, String email, String password, String age, String genderXpath,
			String bloodGroupXpath) {

		fillStep1(name, email, password);
		fillStep2(age, genderXpath, bloodGroupXpath);
	}
	// ================= GENERAL ERROR =================
	public String getRegisterError() {
		try {
			return driver.findElement(By.cssSelector("[data-testid='register-error']")).getText();
		} catch (Exception e) {
			return "";
		}
	}

	// ================= ERROR GETTERS =================
	public String getNameError() {
		try {
			return driver.findElement(org.openqa.selenium.By.cssSelector("[data-testid='register-name-error']")).getText();
		} catch (Exception e) {
			return "";
		}
	}

	public String getEmailError() {
		try {
			return driver.findElement(org.openqa.selenium.By.cssSelector("[data-testid='register-email-error']")).getText();
		} catch (Exception e) {
			return "";
		}
	}

	public String getPasswordError() {
		try {
			return driver.findElement(org.openqa.selenium.By.cssSelector("[data-testid='register-password-error']")).getText();
		} catch (Exception e) {
			return "";
		}
	}
}
package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

	WebDriver driver;

	// Constructor
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// ================= LOCATORS =================

	@FindBy(name = "email")
	WebElement txtEmail;

	@FindBy(name = "password")
	WebElement txtPassword;

	@FindBy(xpath = "//button[@type='submit']")
	WebElement btnLogin;

	@FindBy(xpath = "//button[contains(@class,'text-medical-muted')]")
	WebElement btnTogglePassword;

	@FindBy(xpath = "//*[contains(@class,'text-red')]")
	WebElement errorMessage;

	// ================= ACTION METHODS =================

	// Enter email
	public void enterEmail(String email) {
		txtEmail.clear();
		txtEmail.sendKeys(email);
	}

	// Enter password
	public void enterPassword(String password) {
		txtPassword.clear();
		txtPassword.sendKeys(password);
	}

	// Click login button
	public void clickLogin() {
		btnLogin.click();
	}

	// Toggle password visibility
	public void togglePasswordVisibility() {
		btnTogglePassword.click();
	}

	// Full login action (BEST PRACTICE)
	public void login(String email, String password) {
		enterEmail(email);
		enterPassword(password);
		clickLogin();
	}

	// Get error message text
	public String getErrorMessage() {
		return errorMessage.getText();
	}
}
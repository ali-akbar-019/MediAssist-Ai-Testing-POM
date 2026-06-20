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

	@FindBy(css = "[data-testid='login-email']")
	WebElement txtEmail;

	@FindBy(css = "[data-testid='login-password']")
	WebElement txtPassword;

	@FindBy(css = "[data-testid='login-submit']")
	WebElement btnLogin;

	@FindBy(css = "[data-testid='login-toggle-password']")
	WebElement btnTogglePassword;

	@FindBy(css = "[data-testid='login-error']")
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
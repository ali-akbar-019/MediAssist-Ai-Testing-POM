package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterPage {

	WebDriver driver;

	// Constructor
	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// ================= STEP 1 LOCATORS =================

	@FindBy(xpath = "//input[@placeholder='Ali Akbar']")
	WebElement txtName;

	@FindBy(xpath = "//input[@placeholder='you@example.com']")
	WebElement txtEmail;

	@FindBy(xpath = "//input[@placeholder='Min. 8 characters']")
	WebElement txtPassword;

	@FindBy(xpath = "//button[normalize-space()='Continue']")
	WebElement btnContinue;

	// ================= STEP 2 LOCATORS =================

	@FindBy(xpath = "//input[@placeholder='Your age']")
	WebElement txtAge;

	@FindBy(xpath = "//*[@data-testid='gender-trigger']")
	WebElement ddlGender;

	@FindBy(xpath = "//*[@data-testid='bloodgroup-trigger']")
	WebElement ddlBloodGroup;

	@FindBy(xpath = "//button[normalize-space()='Create Account']")
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
		txtAge.sendKeys(age);
	}

	public void selectGender(String genderOptionXpath) {
		ddlGender.click();
		driver.findElement(org.openqa.selenium.By.xpath(genderOptionXpath)).click();
	}

	public void selectBloodGroup(String bloodGroupXpath) {
		ddlBloodGroup.click();
		driver.findElement(org.openqa.selenium.By.xpath(bloodGroupXpath)).click();
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
}
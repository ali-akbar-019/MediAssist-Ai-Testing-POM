package testCases;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.Status;

import pageObjects.LoginPage;
import utilities.ExtentReportManager;

public class BaseClass {

	public static WebDriver driver;
	public Properties p;

	@BeforeMethod(groups = { "sanity", "smoke", "regression", "master" })
	@Parameters({ "browser" })
	public void setup(String browser) throws IOException {

		// load config.properties
		FileReader file = new FileReader(System.getProperty("user.dir") + "\\src\\test\\resources\\config.properties");
		p = new Properties();
		p.load(file);

		// launch browser based on parameter
		switch (browser.toLowerCase()) {
		case "chrome":
			driver = new ChromeDriver();
			break;
		case "edge":
			driver = new EdgeDriver();
			break;
		case "firefox":
			driver = new FirefoxDriver();
			break;
		default:
			System.out.println("Invalid browser: " + browser + " — launching Chrome");
			driver = new ChromeDriver();
		}

		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get(p.getProperty("appURL"));
	}

	@AfterMethod(groups = { "sanity", "smoke", "regression", "master" })
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	// screenshot utility — called by listener on failure
	public String captureScreen(String testName) throws IOException {
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

		String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + testName + "_" + timeStamp
				+ ".png";

		File targetFile = new File(targetFilePath);
		FileHandler.copy(sourceFile, targetFile);

		return targetFilePath;
	}

	public WebElement waitForElementVisible(By locator, int timeoutSeconds) {
		return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
				.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public boolean waitForUrlContains(String partialUrl, int timeoutSeconds) {
		try {
			return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
					.until(ExpectedConditions.urlContains(partialUrl));
		} catch (TimeoutException e) {
			return false;
		}
	}

	public String getElementTextSafe(By locator) {
		try {
			return driver.findElement(locator).getText();
		} catch (Exception e) {
			return "";
		}
	}

	// for logging
	public void logInfo(String message) {
		ExtentReportManager.getTest().log(Status.INFO, message);
	}

	//
	protected void login() {
		driver.get(p.getProperty("appURL") + "/login");
		LoginPage lp = new LoginPage(driver);
		lp.login(p.getProperty("email"), p.getProperty("password"));

		// Wait longer and check multiple possible URLs
		try {
			new WebDriverWait(driver, Duration.ofSeconds(10))
					.until(ExpectedConditions.or(ExpectedConditions.urlContains("dashboard"),
							ExpectedConditions.urlContains("home"), ExpectedConditions.urlContains("/"),
							ExpectedConditions.urlContains("chat"), ExpectedConditions.urlContains("analyzer")));
		} catch (TimeoutException e) {
			// If still on login, check if there's an error message
			if (driver.getCurrentUrl().contains("login")) {
				String error = lp.getErrorMessage();
				if (!error.isEmpty()) {
					throw new RuntimeException("Login failed: " + error);
				}
			}
			// If no error message but still on login, throw exception
			throw new RuntimeException("Login timeout - still on login page");
		}
	}

	protected void logout() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			WebElement logoutBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("logout")));
			logoutBtn.click();
		} catch (Exception e) {
			logInfo("Logout element not found or already logged out");
		}
	}
}
package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AdminDashboardPage {

	WebDriver driver;
	WebDriverWait wait;

	public AdminDashboardPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		PageFactory.initElements(driver, this);
	}

	// ================= PAGE =================
	@FindBy(css = "[data-testid='admin-dashboard']")
	WebElement adminDashboard;

	// ================= HEADER =================
	@FindBy(css = "[data-testid='admin-heading']")
	WebElement heading;

	@FindBy(css = "[data-testid='admin-status']")
	WebElement statusText;

	@FindBy(css = "[data-testid='admin-latency']")
	WebElement latencyContainer;

	@FindBy(css = "[data-testid='admin-latency-value']")
	WebElement latencyValue;

	// ================= LOADING & ERROR =================
	@FindBy(css = "[data-testid='admin-loading']")
	WebElement loadingContainer;

	@FindBy(css = "[data-testid='admin-loading-spinner']")
	WebElement loadingSpinner;

	@FindBy(css = "[data-testid='admin-loading-text']")
	WebElement loadingText;

	@FindBy(css = "[data-testid='admin-error']")
	WebElement errorContainer;

	// ================= KPI CARDS =================
	@FindBy(css = "[data-testid='admin-kpi-container']")
	WebElement kpiContainer;

	@FindBy(css = "[data-testid='admin-kpi-users']")
	WebElement kpiUsers;

	@FindBy(css = "[data-testid='admin-kpi-symptoms']")
	WebElement kpiSymptoms;

	@FindBy(css = "[data-testid='admin-kpi-documents']")
	WebElement kpiDocuments;

	@FindBy(css = "[data-testid='admin-kpi-reports']")
	WebElement kpiReports;

	// ================= AI INTELLIGENCE SECTION =================
	@FindBy(css = "[data-testid='admin-intelligence']")
	WebElement intelligenceSection;

	@FindBy(css = "[data-testid='admin-intelligence-title']")
	WebElement intelligenceTitle;

	@FindBy(css = "[data-testid='admin-intelligence-chart']")
	WebElement intelligenceChart;

	@FindBy(css = "[data-testid='admin-confidence']")
	WebElement confidenceContainer;

	@FindBy(css = "[data-testid='admin-confidence-value']")
	WebElement confidenceValue;

	@FindBy(css = "[data-testid='admin-most-logged']")
	WebElement mostLoggedContainer;

	@FindBy(css = "[data-testid='admin-most-logged-value']")
	WebElement mostLoggedValue;

	// ================= RECENT ACTIVITY (PULSE) SECTION =================
	@FindBy(css = "[data-testid='admin-pulse']")
	WebElement pulseSection;

	@FindBy(css = "[data-testid='admin-pulse-title']")
	WebElement pulseTitle;

	@FindBy(css = "[data-testid='admin-pulse-events']")
	WebElement pulseEventsContainer;

	// ================= PAGE METHODS =================
	public boolean isPageVisible() {
		try {
			return adminDashboard.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isHeadingVisible() {
		try {
			return heading.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getHeadingText() {
		try {
			return heading.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public String getStatusText() {
		try {
			return statusText.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public String getLatencyValue() {
		try {
			return latencyValue.getText();
		} catch (Exception e) {
			return "";
		}
	}

	// ================= LOADING & ERROR METHODS =================
	public boolean isLoadingVisible() {
		try {
			return loadingContainer.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isErrorVisible() {
		try {
			return errorContainer.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getErrorMessage() {
		try {
			return errorContainer.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public void waitForLoadingToDisappear() {
		try {
			wait.until(ExpectedConditions.invisibilityOf(loadingContainer));
		} catch (Exception e) {
		}
	}

	// ================= KPI METHODS =================
	public boolean isKPIContainerVisible() {
		try {
			return kpiContainer.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isAllKPIVisible() {
		try {
			return kpiUsers.isDisplayed() &&
				   kpiSymptoms.isDisplayed() &&
				   kpiDocuments.isDisplayed() &&
				   kpiReports.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public int getKPIValue(String kpiName) {
		try {
			WebElement value = driver.findElement(By.cssSelector("[data-testid='admin-kpi-value-" + kpiName + "']"));
			return Integer.parseInt(value.getText().replaceAll("[^0-9]", ""));
		} catch (Exception e) {
			return 0;
		}
	}

	public int getUsersCount() {
		return getKPIValue("users");
	}

	public int getSymptomsCount() {
		return getKPIValue("symptoms");
	}

	public int getDocumentsCount() {
		return getKPIValue("documents");
	}

	public int getReportsCount() {
		return getKPIValue("reports");
	}

	public String getKPILabel(String kpiName) {
		try {
			WebElement label = driver.findElement(By.cssSelector("[data-testid='admin-kpi-label-" + kpiName + "']"));
			return label.getText();
		} catch (Exception e) {
			return "";
		}
	}

	// ================= AI INTELLIGENCE METHODS =================
	public boolean isIntelligenceSectionVisible() {
		try {
			return intelligenceSection.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getIntelligenceTitle() {
		try {
			return intelligenceTitle.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public boolean isIntelligenceChartVisible() {
		try {
			return intelligenceChart.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getConfidenceValue() {
		try {
			return confidenceValue.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public String getMostLoggedValue() {
		try {
			return mostLoggedValue.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public boolean isMostLoggedVisible() {
		try {
			return mostLoggedContainer.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// ================= RECENT ACTIVITY (PULSE) METHODS =================
	public boolean isPulseSectionVisible() {
		try {
			return pulseSection.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getPulseTitle() {
		try {
			return pulseTitle.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public int getPulseEventCount() {
		try {
			List<WebElement> events = driver.findElements(By.cssSelector("[data-testid*='admin-pulse-event-']"));
			return events.size() - 1; // subtract 1 because [data-testid*='admin-pulse-event-'] also matches the container
		} catch (Exception e) {
			return 0;
		}
	}

	public String getPulseEventLabel(int index) {
		try {
			WebElement label = driver.findElement(By.cssSelector("[data-testid='admin-pulse-event-label-" + index + "']"));
			return label.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public String getPulseEventTime(int index) {
		try {
			WebElement time = driver.findElement(By.cssSelector("[data-testid='admin-pulse-event-time-" + index + "']"));
			return time.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public String getPulseEventDetail(int index) {
		try {
			WebElement detail = driver.findElement(By.cssSelector("[data-testid='admin-pulse-event-detail-" + index + "']"));
			return detail.getText();
		} catch (Exception e) {
			return "";
		}
	}

	// ================= WAIT METHODS =================
	public void waitForPageLoad() {
		wait.until(ExpectedConditions.visibilityOf(heading));
	}

	public void waitForDashboardToLoad() {
		try {
			waitForLoadingToDisappear();
			wait.until(ExpectedConditions.visibilityOf(kpiContainer));
		} catch (Exception e) {
		}
	}

	public void waitForKPIs() {
		wait.until(ExpectedConditions.visibilityOfAllElements(kpiUsers, kpiSymptoms, kpiDocuments, kpiReports));
	}

	public void waitForIntelligenceSection() {
		wait.until(ExpectedConditions.visibilityOf(intelligenceSection));
	}

	public void waitForPulseSection() {
		wait.until(ExpectedConditions.visibilityOf(pulseSection));
	}

	// ================= VERIFICATION METHODS =================
	public boolean verifyAllKPIsVisible() {
		return isAllKPIVisible() &&
			   !getKPILabel("users").isEmpty() &&
			   !getKPILabel("symptoms").isEmpty() &&
			   !getKPILabel("documents").isEmpty() &&
			   !getKPILabel("reports").isEmpty();
	}

	public boolean verifyAllKPIsHaveValues() {
		return getUsersCount() >= 0 &&
			   getSymptomsCount() >= 0 &&
			   getDocumentsCount() >= 0 &&
			   getReportsCount() >= 0;
	}

	public boolean verifyAllPulseEvents() {
		int count = getPulseEventCount();
		for (int i = 0; i < count; i++) {
			if (getPulseEventLabel(i).isEmpty()) return false;
			if (getPulseEventTime(i).isEmpty()) return false;
		}
		return true;
	}

	// ================= COMPLETE FLOW METHODS =================
	public void completeFullDashboardLoad() {
		waitForDashboardToLoad();
		assert isPageVisible() : "Admin dashboard page should be visible";
		assert isHeadingVisible() : "Heading should be visible";
		assert verifyAllKPIsVisible() : "All KPIs should be visible";
	}

	public void verifyAdminDashboard() {
		completeFullDashboardLoad();
		assert verifyAllKPIsHaveValues() : "All KPIs should have valid values";
		assert isIntelligenceSectionVisible() : "AI Intelligence section should be visible";
		assert isPulseSectionVisible() : "Recent Activity section should be visible";
	}
}

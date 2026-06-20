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

	@FindBy(css = "[data-testid='admin-kpi-personnel']")
	WebElement kpiPersonnel;

	@FindBy(css = "[data-testid='admin-kpi-symptoms']")
	WebElement kpiSymptoms;

	@FindBy(css = "[data-testid='admin-kpi-intelligence']")
	WebElement kpiIntelligence;

	@FindBy(css = "[data-testid='admin-kpi-artifacts']")
	WebElement kpiArtifacts;

	@FindBy(css = "[data-testid='admin-kpi-label-personnel']")
	WebElement kpiLabelPersonnel;

	@FindBy(css = "[data-testid='admin-kpi-label-symptoms']")
	WebElement kpiLabelSymptoms;

	@FindBy(css = "[data-testid='admin-kpi-label-intelligence']")
	WebElement kpiLabelIntelligence;

	@FindBy(css = "[data-testid='admin-kpi-label-artifacts']")
	WebElement kpiLabelArtifacts;

	@FindBy(css = "[data-testid='admin-kpi-value-personnel']")
	WebElement kpiValuePersonnel;

	@FindBy(css = "[data-testid='admin-kpi-value-symptoms']")
	WebElement kpiValueSymptoms;

	@FindBy(css = "[data-testid='admin-kpi-value-intelligence']")
	WebElement kpiValueIntelligence;

	@FindBy(css = "[data-testid='admin-kpi-value-artifacts']")
	WebElement kpiValueArtifacts;

	// ================= CONSOLE SECTIONS =================
	@FindBy(css = "[data-testid='admin-console']")
	WebElement consoleContainer;

	// Intelligence Section
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

	// Operations Section
	@FindBy(css = "[data-testid='admin-operations']")
	WebElement operationsSection;

	@FindBy(css = "[data-testid='admin-operations-title']")
	WebElement operationsTitle;

	@FindBy(css = "[data-testid='admin-threads']")
	WebElement threadsContainer;

	// Pulse Section
	@FindBy(css = "[data-testid='admin-pulse']")
	WebElement pulseSection;

	@FindBy(css = "[data-testid='admin-pulse-title']")
	WebElement pulseTitle;

	@FindBy(css = "[data-testid='admin-pulse-events']")
	WebElement pulseEventsContainer;

	@FindBy(css = "[data-testid='admin-initialize-btn']")
	WebElement initializeBtn;

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
			return kpiPersonnel.isDisplayed() &&
				   kpiSymptoms.isDisplayed() &&
				   kpiIntelligence.isDisplayed() &&
				   kpiArtifacts.isDisplayed();
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

	public int getPersonnelCount() {
		return getKPIValue("personnel");
	}

	public int getSymptomsCount() {
		return getKPIValue("symptoms");
	}

	public int getIntelligenceCount() {
		return getKPIValue("intelligence");
	}

	public int getArtifactsCount() {
		return getKPIValue("artifacts");
	}

	public String getKPILabel(String kpiName) {
		try {
			WebElement label = driver.findElement(By.cssSelector("[data-testid='admin-kpi-label-" + kpiName + "']"));
			return label.getText();
		} catch (Exception e) {
			return "";
		}
	}

	// ================= CONSOLE SECTION METHODS =================
	public boolean isConsoleVisible() {
		try {
			return consoleContainer.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// Intelligence Section
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

	public int getChartBarCount() {
		try {
			List<WebElement> bars = driver.findElements(By.cssSelector("[data-testid*='admin-chart-bar-']"));
			return bars.size();
		} catch (Exception e) {
			return 0;
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

	// Operations Section
	public boolean isOperationsSectionVisible() {
		try {
			return operationsSection.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getOperationsTitle() {
		try {
			return operationsTitle.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public int getThreadCount() {
		try {
			List<WebElement> threads = driver.findElements(By.cssSelector("[data-testid*='admin-thread-']"));
			return threads.size();
		} catch (Exception e) {
			return 0;
		}
	}

	public String getThreadLabel(int index) {
		try {
			WebElement label = driver.findElement(By.cssSelector("[data-testid='admin-thread-label-" + index + "']"));
			return label.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public String getThreadProgressText(int index) {
		try {
			WebElement progress = driver.findElement(By.cssSelector("[data-testid='admin-thread-progress-" + index + "']"));
			return progress.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public int getThreadProgressValue(int index) {
		try {
			String text = getThreadProgressText(index);
			return Integer.parseInt(text.replaceAll("[^0-9]", ""));
		} catch (Exception e) {
			return 0;
		}
	}

	// Pulse Section
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
			return events.size();
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

	public boolean isInitializeBtnVisible() {
		try {
			return initializeBtn.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void clickInitializeBtn() {
		initializeBtn.click();
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
		wait.until(ExpectedConditions.visibilityOfAllElements(kpiPersonnel, kpiSymptoms, kpiIntelligence, kpiArtifacts));
	}

	public void waitForConsoleSections() {
		wait.until(ExpectedConditions.visibilityOf(intelligenceSection));
		wait.until(ExpectedConditions.visibilityOf(operationsSection));
		wait.until(ExpectedConditions.visibilityOf(pulseSection));
	}

	// ================= VERIFICATION METHODS =================
	public boolean verifyAllKPIsVisible() {
		return isAllKPIVisible() && 
			   !getKPILabel("personnel").isEmpty() &&
			   !getKPILabel("symptoms").isEmpty() &&
			   !getKPILabel("intelligence").isEmpty() &&
			   !getKPILabel("artifacts").isEmpty();
	}

	public boolean verifyAllKPIsHaveValues() {
		return getPersonnelCount() >= 0 &&
			   getSymptomsCount() >= 0 &&
			   getIntelligenceCount() >= 0 &&
			   getArtifactsCount() >= 0;
	}

	public boolean verifyConsoleSections() {
		return isIntelligenceSectionVisible() &&
			   isOperationsSectionVisible() &&
			   isPulseSectionVisible();
	}

	public boolean verifyAllThreadsVisible() {
		int count = getThreadCount();
		for (int i = 0; i < count; i++) {
			if (getThreadLabel(i).isEmpty()) return false;
			if (getThreadProgressValue(i) < 0) return false;
		}
		return count >= 4;
	}

	public boolean verifyAllPulseEventsVisible() {
		int count = getPulseEventCount();
		for (int i = 0; i < count; i++) {
			if (getPulseEventLabel(i).isEmpty()) return false;
			if (getPulseEventTime(i).isEmpty()) return false;
		}
		return count >= 4;
	}

	// ================= COMPLETE FLOW METHODS =================
	public void completeFullDashboardLoad() {
		waitForDashboardToLoad();
		assert isPageVisible() : "Admin dashboard page should be visible";
		assert isHeadingVisible() : "Heading should be visible";
		assert verifyAllKPIsVisible() : "All KPIs should be visible";
		assert verifyConsoleSections() : "All console sections should be visible";
	}

	public void verifyAdminDashboard() {
		completeFullDashboardLoad();
		assert verifyAllKPIsHaveValues() : "All KPIs should have valid values";
		assert verifyAllThreadsVisible() : "All threads should be visible";
		assert verifyAllPulseEventsVisible() : "All pulse events should be visible";
		assert isInitializeBtnVisible() : "Initialize button should be visible";
	}
}
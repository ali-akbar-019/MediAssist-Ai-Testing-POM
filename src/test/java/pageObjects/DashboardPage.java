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

public class DashboardPage {

	WebDriver driver;
	WebDriverWait wait;

	public DashboardPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);
	}

	// ================= PAGE =================
	@FindBy(css = "[data-testid='dashboard-page']")
	WebElement dashboardPage;

	@FindBy(css = "[data-testid='dashboard-heading']")
	WebElement heading;

	@FindBy(css = "[data-testid='dashboard-subtitle']")
	WebElement subtitle;

	@FindBy(css = "[data-testid='dashboard-badge']")
	WebElement badge;

	@FindBy(css = "[data-testid='dashboard-container']")
	WebElement dashboardContainer;

	// ================= COMMAND BAR =================
	@FindBy(css = "[data-testid='dashboard-command-bar']")
	WebElement commandBar;

	@FindBy(css = "[data-testid='dashboard-status']")
	WebElement statusText;

	@FindBy(css = "[data-testid='dashboard-initiate-analysis-btn']")
	WebElement initiateAnalysisBtn;

	// ================= HEALTH DASHBOARD =================
	@FindBy(css = "[data-testid='health-dashboard']")
	WebElement healthDashboard;

	// ================= STATS =================
	@FindBy(css = "[data-testid='dashboard-stats']")
	WebElement statsContainer;

	@FindBy(css = "[data-testid='dashboard-stats-loading']")
	WebElement statsLoading;

	// Individual Stat Cards
	@FindBy(css = "[data-testid='stat-card-total-analyses']")
	WebElement statTotalAnalyses;

	@FindBy(css = "[data-testid='stat-card-this-month']")
	WebElement statThisMonth;

	@FindBy(css = "[data-testid='stat-card-reports-generated']")
	WebElement statReportsGenerated;

	@FindBy(css = "[data-testid='stat-card-last-analysis']")
	WebElement statLastAnalysis;

	// Stat Values
	@FindBy(css = "[data-testid='stat-value-total-analyses']")
	WebElement statValueTotalAnalyses;

	@FindBy(css = "[data-testid='stat-value-this-month']")
	WebElement statValueThisMonth;

	@FindBy(css = "[data-testid='stat-value-reports-generated']")
	WebElement statValueReportsGenerated;

	@FindBy(css = "[data-testid='stat-value-last-analysis']")
	WebElement statValueLastAnalysis;

	// ================= CHARTS =================
	@FindBy(css = "[data-testid='dashboard-charts']")
	WebElement chartsContainer;

	@FindBy(css = "[data-testid='stats-chart-container']")
	WebElement statsChartContainer;

	@FindBy(css = "[data-testid='severity-pie-chart']")
	WebElement severityPieChart;

	@FindBy(css = "[data-testid='bodypart-bar-chart']")
	WebElement bodypartBarChart;

	// ================= HISTORY =================
	@FindBy(css = "[data-testid='dashboard-history']")
	WebElement historySection;

	@FindBy(css = "[data-testid='dashboard-history-title']")
	WebElement historyTitle;

	@FindBy(css = "[data-testid='dashboard-history-count']")
	WebElement historyCount;

	@FindBy(css = "[data-testid='dashboard-history-grid']")
	WebElement historyGrid;

	@FindBy(css = "[data-testid='dashboard-history-loading']")
	WebElement historyLoading;

	@FindBy(css = "[data-testid='dashboard-history-empty']")
	WebElement historyEmpty;

	@FindBy(css = "[data-testid='dashboard-empty-analyzer-link']")
	WebElement emptyAnalyzerLink;

	@FindBy(css = "[data-testid='dashboard-view-all-reports']")
	WebElement viewAllReportsBtn;

	// ================= USER PROFILE =================
	@FindBy(css = "[data-testid='dashboard-user-profile']")
	WebElement userProfileSection;

	@FindBy(css = "[data-testid='dashboard-profile-age']")
	WebElement profileAge;

	@FindBy(css = "[data-testid='dashboard-profile-age-value']")
	WebElement profileAgeValue;

	@FindBy(css = "[data-testid='dashboard-profile-gender']")
	WebElement profileGender;

	@FindBy(css = "[data-testid='dashboard-profile-gender-value']")
	WebElement profileGenderValue;

	@FindBy(css = "[data-testid='dashboard-profile-bloodgroup']")
	WebElement profileBloodGroup;

	@FindBy(css = "[data-testid='dashboard-profile-bloodgroup-value']")
	WebElement profileBloodGroupValue;

	@FindBy(css = "[data-testid='dashboard-profile-allergies']")
	WebElement profileAllergies;

	@FindBy(css = "[data-testid='dashboard-profile-allergies-value']")
	WebElement profileAllergiesValue;

	// ================= VITALITY INTELLIGENCE =================
	@FindBy(css = "[data-testid='vitality-intelligence']")
	WebElement vitalityIntelligence;

	@FindBy(css = "[data-testid='vitality-gauge']")
	WebElement vitalityGauge;

	@FindBy(css = "[data-testid='vitality-score']")
	WebElement vitalityScore;

	@FindBy(css = "[data-testid='vitality-label']")
	WebElement vitalityLabel;

	@FindBy(css = "[data-testid='vitality-status-text']")
	WebElement vitalityStatusText;

	@FindBy(css = "[data-testid='vitality-title']")
	WebElement vitalityTitle;

	@FindBy(css = "[data-testid='vitality-system-statuses']")
	WebElement vitalitySystemStatuses;

	@FindBy(css = "[data-testid='vitality-ai-insight']")
	WebElement vitalityAIInsight;

	@FindBy(css = "[data-testid='vitality-insight-text']")
	WebElement vitalityInsightText;

	// ================= HEADER METHODS =================
	public boolean isPageVisible() {
		try {
			return dashboardPage.isDisplayed();
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

	public boolean isSubtitleVisible() {
		try {
			return subtitle.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isBadgeVisible() {
		try {
			return badge.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getBadgeText() {
		try {
			return badge.getText();
		} catch (Exception e) {
			return "";
		}
	}

	// ================= COMMAND BAR METHODS =================
	public boolean isCommandBarVisible() {
		try {
			return commandBar.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getStatusText() {
		try {
			return statusText.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public void clickInitiateAnalysis() {
		initiateAnalysisBtn.click();
	}

	public boolean isInitiateAnalysisBtnVisible() {
		try {
			return initiateAnalysisBtn.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// ================= HEALTH DASHBOARD METHODS =================
	public boolean isHealthDashboardVisible() {
		try {
			return healthDashboard.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// ================= STATS METHODS =================
	public boolean isStatsVisible() {
		try {
			return statsContainer.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isStatsLoadingVisible() {
		try {
			return statsLoading.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public int getTotalAnalyses() {
		try {
			return Integer.parseInt(statValueTotalAnalyses.getText().replaceAll("[^0-9]", ""));
		} catch (Exception e) {
			return 0;
		}
	}

	public int getThisMonthCount() {
		try {
			return Integer.parseInt(statValueThisMonth.getText().replaceAll("[^0-9]", ""));
		} catch (Exception e) {
			return 0;
		}
	}

	public int getReportsGenerated() {
		try {
			return Integer.parseInt(statValueReportsGenerated.getText().replaceAll("[^0-9]", ""));
		} catch (Exception e) {
			return 0;
		}
	}

	public String getLastAnalysis() {
		try {
			return statValueLastAnalysis.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public boolean isStatCardVisible(String statName) {
		try {
			WebElement card = driver.findElement(By.cssSelector("[data-testid='stat-card-" + statName + "']"));
			return card.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// ================= CHARTS METHODS =================
	public boolean isChartsVisible() {
		try {
			return chartsContainer.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isStatsChartVisible() {
		try {
			return statsChartContainer.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isSeverityChartVisible() {
		try {
			return severityPieChart.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isBodypartChartVisible() {
		try {
			return bodypartBarChart.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public int getSeverityChartSegments() {
		try {
			List<WebElement> segments = driver.findElements(By.cssSelector("[data-testid*='severity-cell-']"));
			return segments.size();
		} catch (Exception e) {
			return 0;
		}
	}

	public int getBodypartBars() {
		try {
			List<WebElement> bars = driver.findElements(By.cssSelector("[data-testid*='bodypart-bar-']"));
			return bars.size();
		} catch (Exception e) {
			return 0;
		}
	}

	// ================= HISTORY METHODS =================
	public boolean isHistorySectionVisible() {
		try {
			return historySection.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getHistoryTitle() {
		try {
			return historyTitle.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public int getHistoryRecordCount() {
		try {
			String text = historyCount.getText();
			String[] parts = text.split(":");
			if (parts.length > 1) {
				return Integer.parseInt(parts[1].trim());
			}
			return 0;
		} catch (Exception e) {
			return 0;
		}
	}

	public boolean isHistoryGridVisible() {
		try {
			return historyGrid.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public int getHistoryCardCount() {
		try {
			List<WebElement> cards = driver.findElements(By.cssSelector("[data-testid*='history-card-']"));
			return cards.size();
		} catch (Exception e) {
			return 0;
		}
	}

	public boolean isHistoryLoadingVisible() {
		try {
			return historyLoading.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isEmptyStateVisible() {
		try {
			return historyEmpty.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void clickViewAllReports() {
		viewAllReportsBtn.click();
	}

	public boolean isViewAllReportsVisible() {
		try {
			return viewAllReportsBtn.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void clickEmptyAnalyzerLink() {
		emptyAnalyzerLink.click();
	}

	// ================= USER PROFILE METHODS =================
	public boolean isUserProfileVisible() {
		try {
			return userProfileSection.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getProfileAge() {
		try {
			return profileAgeValue.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public String getProfileGender() {
		try {
			return profileGenderValue.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public String getProfileBloodGroup() {
		try {
			return profileBloodGroupValue.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public String getProfileAllergies() {
		try {
			return profileAllergiesValue.getText();
		} catch (Exception e) {
			return "";
		}
	}

	// ================= VITALITY INTELLIGENCE METHODS =================
	public boolean isVitalityIntelligenceVisible() {
		try {
			return vitalityIntelligence.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isVitalityGaugeVisible() {
		try {
			return vitalityGauge.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public int getVitalityScore() {
		try {
			String scoreText = vitalityScore.getText();
			return Integer.parseInt(scoreText.replaceAll("[^0-9]", ""));
		} catch (Exception e) {
			return 0;
		}
	}

	public String getVitalityLabel() {
		try {
			return vitalityLabel.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public String getVitalityStatus() {
		try {
			return vitalityStatusText.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public String getVitalityTitle() {
		try {
			return vitalityTitle.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public boolean isVitalityAIInsightVisible() {
		try {
			return vitalityAIInsight.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getVitalityInsightText() {
		try {
			return vitalityInsightText.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public int getSystemStatusCount() {
		try {
			List<WebElement> statuses = driver.findElements(By.cssSelector("[data-testid*='system-status-']"));
			return statuses.size();
		} catch (Exception e) {
			return 0;
		}
	}

	public String getSystemStatusValue(String systemName) {
		try {
			WebElement status = driver.findElement(By.cssSelector("[data-testid='system-status-value-" + systemName.toLowerCase() + "']"));
			return status.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public boolean isSystemStatusOptimal(String systemName) {
		return getSystemStatusValue(systemName).equalsIgnoreCase("Optimal");
	}

	// ================= WAIT METHODS =================
	public void waitForPageLoad() {
		wait.until(ExpectedConditions.visibilityOf(heading));
	}

	public void waitForStatsToLoad() {
		try {
			wait.until(ExpectedConditions.invisibilityOf(statsLoading));
		} catch (Exception e) {
		}
	}

	public void waitForHistoryToLoad() {
		try {
			wait.until(ExpectedConditions.invisibilityOf(historyLoading));
		} catch (Exception e) {
		}
	}

	public void waitForChartsToLoad() {
		try {
			wait.until(ExpectedConditions.visibilityOf(statsChartContainer));
		} catch (Exception e) {
		}
	}

	public void waitForDashboardToLoad() {
		waitForPageLoad();
		waitForStatsToLoad();
		waitForHistoryToLoad();
		waitForChartsToLoad();
	}

	// ================= VERIFICATION METHODS =================
	public boolean verifyAllStatsVisible() {
		return isStatCardVisible("total-analyses") &&
			   isStatCardVisible("this-month") &&
			   isStatCardVisible("reports-generated") &&
			   isStatCardVisible("last-analysis");
	}

	public boolean verifyAllChartsVisible() {
		return isSeverityChartVisible() && isBodypartChartVisible();
	}

	public boolean verifyVitalitySection() {
		return isVitalityIntelligenceVisible() &&
			   isVitalityGaugeVisible() &&
			   getVitalityScore() > 0 &&
			   isVitalityAIInsightVisible() &&
			   !getVitalityInsightText().isEmpty();
	}

	public boolean verifyUserProfile() {
		return isUserProfileVisible() &&
			   !getProfileAge().isEmpty() &&
			   !getProfileGender().isEmpty() &&
			   !getProfileBloodGroup().isEmpty();
	}

	// ================= COMPLETE FLOW METHODS =================
	public void completeFullDashboardLoad() {
		waitForDashboardToLoad();
		assert isPageVisible() : "Dashboard page should be visible";
		assert isHeadingVisible() : "Heading should be visible";
		assert verifyAllStatsVisible() : "All stats should be visible";
		assert verifyAllChartsVisible() : "All charts should be visible";
		assert isVitalityIntelligenceVisible() : "Vitality intelligence should be visible";
	}

	public void navigateToAnalyzer() {
		clickInitiateAnalysis();
	}

	public void navigateToReports() {
		clickViewAllReports();
	}
}
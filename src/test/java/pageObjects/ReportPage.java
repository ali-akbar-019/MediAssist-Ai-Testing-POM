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

public class ReportPage {

	WebDriver driver;
	WebDriverWait wait;

	public ReportPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);
	}

	// ================= HEADER =================
	@FindBy(css = "[data-testid='reports-page']")
	WebElement reportsPage;

	@FindBy(css = "[data-testid='reports-heading']")
	WebElement heading;

	@FindBy(css = "[data-testid='reports-subtitle']")
	WebElement subtitle;

	@FindBy(css = "[data-testid='reports-back-btn']")
	WebElement backBtn;

	// ================= STATS =================
	@FindBy(css = "[data-testid='reports-stats']")
	WebElement statsContainer;

	@FindBy(css = "[data-testid='reports-total-count']")
	WebElement totalCount;

	// ================= GRID =================
	@FindBy(css = "[data-testid='reports-grid-container']")
	WebElement gridContainer;

	@FindBy(css = "[data-testid='reports-grid']")
	WebElement reportsGrid;

	@FindBy(css = "[data-testid='reports-loading']")
	WebElement loadingIndicator;

	@FindBy(css = "[data-testid='reports-empty']")
	WebElement emptyState;

	// ================= PAGINATION =================
	@FindBy(css = "[data-testid='reports-pagination']")
	WebElement paginationControls;

	@FindBy(css = "[data-testid='reports-prev-page']")
	WebElement prevPageBtn;

	@FindBy(css = "[data-testid='reports-page-numbers']")
	WebElement pageNumbersContainer;

	@FindBy(css = "[data-testid='reports-next-page']")
	WebElement nextPageBtn;

	// ================= HEADER METHODS =================
	public boolean isPageVisible() {
		try {
			return reportsPage.isDisplayed();
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

	public String getSubtitleText() {
		try {
			return subtitle.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public boolean isBackBtnVisible() {
		try {
			return backBtn.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void clickBackBtn() {
		backBtn.click();
	}

	// ================= STATS METHODS =================
	public boolean isStatsVisible() {
		try {
			return statsContainer.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public int getTotalRecordsCount() {
		try {
			return Integer.parseInt(totalCount.getText().replaceAll("[^0-9]", ""));
		} catch (Exception e) {
			return 0;
		}
	}

	public String getTotalRecordsText() {
		try {
			return totalCount.getText();
		} catch (Exception e) {
			return "0";
		}
	}

	// ================= GRID METHODS =================
	public boolean isGridVisible() {
		try {
			return reportsGrid.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public int getReportCardCount() {
		try {
			List<WebElement> cards = driver.findElements(By.cssSelector("[data-testid^='report-card-']"));
			return cards.size();
		} catch (Exception e) {
			return 0;
		}
	}

	public boolean hasReports() {
		return getReportCardCount() > 0;
	}

	public boolean isLoadingVisible() {
		try {
			return loadingIndicator.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isEmptyStateVisible() {
		try {
			return emptyState.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getEmptyStateText() {
		try {
			return emptyState.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public boolean isGridContainerVisible() {
		try {
			return gridContainer.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// ================= REPORT CARD METHODS =================
	public WebElement getReportCard(int index) {
		try {
			return driver.findElement(By.cssSelector("[data-testid='report-card-" + index + "']"));
		} catch (Exception e) {
			return null;
		}
	}

	public String getReportCardBodyPart(int index) {
		try {
			WebElement card = getReportCard(index);
			if (card != null) {
				WebElement bodyPart = card.findElement(By.cssSelector("[data-testid*='history-card-bodypart-" + index + "']"));
				return bodyPart.getText();
			}
			return "";
		} catch (Exception e) {
			return "";
		}
	}

	public String getReportCardSeverity(int index) {
		try {
			WebElement card = getReportCard(index);
			if (card != null) {
				WebElement severity = card.findElement(By.cssSelector("[data-testid*='history-card-severity-" + index + "']"));
				return severity.getText();
			}
			return "";
		} catch (Exception e) {
			return "";
		}
	}

	public String getReportCardSymptom(int index, int symptomIndex) {
		try {
			WebElement card = getReportCard(index);
			if (card != null) {
				List<WebElement> symptoms = card.findElements(By.cssSelector("[data-testid*='history-card-symptom-" + index + "-']"));
				if (symptomIndex < symptoms.size()) {
					return symptoms.get(symptomIndex).getText();
				}
			}
			return "";
		} catch (Exception e) {
			return "";
		}
	}

	public int getReportCardSymptomsCount(int index) {
		try {
			WebElement card = getReportCard(index);
			if (card != null) {
				List<WebElement> symptoms = card.findElements(By.cssSelector("[data-testid*='history-card-symptom-" + index + "-']"));
				return symptoms.size();
			}
			return 0;
		} catch (Exception e) {
			return 0;
		}
	}

	public String getReportCardMetric(int index, String metric) {
		try {
			WebElement card = getReportCard(index);
			if (card != null) {
				WebElement metricEl = card.findElement(By.cssSelector("[data-testid='history-card-metric-" + index + "-" + metric + "']"));
				return metricEl.getText();
			}
			return "";
		} catch (Exception e) {
			return "";
		}
	}

	public String getReportCardAIInsight(int index) {
		try {
			WebElement card = getReportCard(index);
			if (card != null) {
				WebElement insight = card.findElement(By.cssSelector("[data-testid*='history-card-ai-insight-" + index + "']"));
				return insight.getText();
			}
			return "";
		} catch (Exception e) {
			return "";
		}
	}

	public String getReportCardTime(int index) {
		try {
			WebElement card = getReportCard(index);
			if (card != null) {
				WebElement time = card.findElement(By.cssSelector("[data-testid*='history-card-time-" + index + "']"));
				return time.getText();
			}
			return "";
		} catch (Exception e) {
			return "";
		}
	}

	// ================= REPORT CARD ACTIONS =================
	public void downloadReport(int index) {
		try {
			WebElement card = getReportCard(index);
			if (card != null) {
				WebElement downloadBtn = card.findElement(By.cssSelector("[data-testid*='history-card-download-" + index + "']"));
				downloadBtn.click();
			}
		} catch (Exception e) {
		}
	}

	public void deleteReport(int index) {
		try {
			WebElement card = getReportCard(index);
			if (card != null) {
				WebElement deleteBtn = card.findElement(By.cssSelector("[data-testid*='history-card-delete-" + index + "']"));
				deleteBtn.click();
			}
		} catch (Exception e) {
		}
	}

	public void clickReportCard(int index) {
		try {
			WebElement card = getReportCard(index);
			if (card != null) {
				card.click();
			}
		} catch (Exception e) {
		}
	}

	public boolean isDownloadBtnVisible(int index) {
		try {
			WebElement card = getReportCard(index);
			if (card != null) {
				return card.findElement(By.cssSelector("[data-testid*='history-card-download-" + index + "']")).isDisplayed();
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isDeleteBtnVisible(int index) {
		try {
			WebElement card = getReportCard(index);
			if (card != null) {
				return card.findElement(By.cssSelector("[data-testid*='history-card-delete-" + index + "']")).isDisplayed();
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	// ================= PAGINATION METHODS =================
	public boolean isPaginationVisible() {
		try {
			return paginationControls.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void goToPage(int pageNumber) {
		try {
			WebElement pageBtn = driver.findElement(By.cssSelector("[data-testid='reports-page-" + pageNumber + "']"));
			pageBtn.click();
			waitForGridToLoad();
		} catch (Exception e) {
		}
	}

	public void goToNextPage() {
		nextPageBtn.click();
		waitForGridToLoad();
	}

	public void goToPreviousPage() {
		prevPageBtn.click();
		waitForGridToLoad();
	}

	public boolean isPrevPageDisabled() {
		return !prevPageBtn.isEnabled();
	}

	public boolean isNextPageDisabled() {
		return !nextPageBtn.isEnabled();
	}

	public boolean isPageActive(int pageNumber) {
		try {
			WebElement pageBtn = driver.findElement(By.cssSelector("[data-testid='reports-page-" + pageNumber + "']"));
			return pageBtn.getAttribute("class").contains("bg-navy-900");
		} catch (Exception e) {
			return false;
		}
	}

	public int getActivePage() {
		try {
			List<WebElement> pageBtns = driver.findElements(By.cssSelector("[data-testid^='reports-page-']"));
			for (WebElement btn : pageBtns) {
				if (btn.getAttribute("class").contains("bg-navy-900")) {
					String testId = btn.getAttribute("data-testid");
					return Integer.parseInt(testId.replace("reports-page-", ""));
				}
			}
			return 1;
		} catch (Exception e) {
			return 1;
		}
	}

	public int getTotalPageCount() {
		try {
			List<WebElement> pageBtns = driver.findElements(By.cssSelector("[data-testid^='reports-page-']"));
			return pageBtns.size();
		} catch (Exception e) {
			return 0;
		}
	}

	// ================= WAIT METHODS =================
	public void waitForPageLoad() {
		wait.until(ExpectedConditions.visibilityOf(heading));
	}

	public void waitForGridToLoad() {
		try {
			wait.until(ExpectedConditions.invisibilityOf(loadingIndicator));
			wait.until(ExpectedConditions.visibilityOf(reportsGrid));
		} catch (Exception e) {
		}
	}

	public void waitForReportsToLoad() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
	}

	public void waitForEmptyState() {
		wait.until(ExpectedConditions.visibilityOf(emptyState));
	}

	// ================= VERIFICATION METHODS =================
	public boolean verifyReportCardExists(int index) {
		return getReportCard(index) != null;
	}

	public boolean verifyReportCardHasData(int index) {
		return !getReportCardBodyPart(index).isEmpty() &&
			   !getReportCardSeverity(index).isEmpty() &&
			   getReportCardSymptomsCount(index) > 0;
	}

	public boolean verifyAllReportCardsHaveData() {
		int count = getReportCardCount();
		for (int i = 0; i < count; i++) {
			if (!verifyReportCardHasData(i)) {
				return false;
			}
		}
		return true;
	}

	// ================= COMPLETE FLOW METHODS =================
	public void completePaginationFlow() {
		int totalPages = getTotalPageCount();
		if (totalPages > 1) {
			for (int i = 1; i <= totalPages; i++) {
				goToPage(i);
				waitForGridToLoad();
				assert isPageActive(i) : "Page " + i + " should be active";
				assert getReportCardCount() > 0 : "Page " + i + " should have reports";
			}
		}
	}

	public void verifyReportCardDetails(int index) {
		assert verifyReportCardExists(index) : "Report card at index " + index + " should exist";
		assert !getReportCardBodyPart(index).isEmpty() : "Body part should not be empty";
		assert !getReportCardSeverity(index).isEmpty() : "Severity should not be empty";
		assert getReportCardSymptomsCount(index) > 0 : "Should have at least one symptom";
		assert !getReportCardTime(index).isEmpty() : "Time should not be empty";
	}
}
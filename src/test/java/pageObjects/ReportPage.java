package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

public class ReportPage {

	WebDriver driver;

	public ReportPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = "[data-testid='reports-heading']")
	WebElement heading;

	@FindBy(css = "[data-testid='reports-grid']")
	WebElement reportsGrid;

	@FindBy(css = "[data-testid='reports-pagination']")
	WebElement paginationControls;

	// ================= BASIC CHECKS =================
	public boolean isHeadingVisible() {
		try {
			return heading.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isReportsGridVisible() {
		try {
			return reportsGrid.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isPaginationVisible() {
		try {
			return paginationControls.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// ================= REPORT CARDS =================
	public int getReportCardCount() {
		try {
			List<WebElement> cards = driver.findElements(By.cssSelector("[data-testid^='report-card-']"));
			return cards.size();
		} catch (Exception e) {
			return 0;
		}
	}

	public boolean hasReports() {
		try {
			return getReportCardCount() > 0;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isNoReportsMessageVisible() {
		try {
			List<WebElement> noReports = driver.findElements(By.xpath("//*[contains(text(), 'No Reports Found')]"));
			return !noReports.isEmpty();
		} catch (Exception e) {
			return false;
		}
	}

	// ================= PAGINATION =================
	public void goToPage(int pageNumber) {
		try {
			List<WebElement> pageButtons = driver.findElements(By.xpath("//button[contains(., '" + pageNumber + "')]"));
			if (!pageButtons.isEmpty()) {
				pageButtons.get(0).click();
			}
		} catch (Exception e) {
		}
	}

	public void goToNextPage() {
		try {
			List<WebElement> nextButtons = driver.findElements(By.xpath("//button[contains(@class, 'right-6')]"));
			if (!nextButtons.isEmpty()) {
				nextButtons.get(nextButtons.size() - 1).click(); // Last button is typically "Next"
			}
		} catch (Exception e) {
		}
	}

	public void goToPreviousPage() {
		try {
			List<WebElement> prevButtons = driver.findElements(By.xpath("//button[contains(@class, 'left-6')]"));
			if (!prevButtons.isEmpty()) {
				prevButtons.get(0).click(); // First button is typically "Previous"
			}
		} catch (Exception e) {
		}
	}

	public boolean isPaginationDisabled() {
		try {
			List<WebElement> disabled = driver.findElements(By.xpath("//button[@disabled]"));
			return !disabled.isEmpty();
		} catch (Exception e) {
			return false;
		}
	}

	// ================= REPORT INTERACTION =================
	public void clickReportCard(int index) {
		try {
			List<WebElement> cards = driver.findElements(By.cssSelector("[data-testid^='report-card-']"));
			if (index >= 0 && index < cards.size()) {
				cards.get(index).click();
			}
		} catch (Exception e) {
		}
	}

	public void downloadReport(int index) {
		try {
			List<WebElement> downloadButtons = driver.findElements(By.xpath("//button[contains(., 'Download')]"));
			if (index >= 0 && index < downloadButtons.size()) {
				downloadButtons.get(index).click();
			}
		} catch (Exception e) {
		}
	}

	public void deleteReport(int index) {
		try {
			List<WebElement> deleteButtons = driver.findElements(By.xpath("//button[contains(., 'Delete')]"));
			if (index >= 0 && index < deleteButtons.size()) {
				deleteButtons.get(index).click();
			}
		} catch (Exception e) {
		}
	}

	// ================= TOTAL COUNT DISPLAY =================
	public String getTotalRecordsCount() {
		try {
			List<WebElement> count = driver.findElements(By.xpath("//*[contains(@class, 'text-xl font-black')]"));
			return !count.isEmpty() ? count.get(count.size() - 1).getText() : "0";
		} catch (Exception e) {
			return "0";
		}
	}

}

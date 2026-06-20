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

public class HospitalPage {

	WebDriver driver;
	WebDriverWait wait;

	public HospitalPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);
	}

	// ================= HEADER =================
	@FindBy(css = "[data-testid='hospital-heading']")
	WebElement heading;

	@FindBy(css = "[data-testid='hospital-page']")
	WebElement hospitalPage;

	@FindBy(css = "[data-testid='hospital-container']")
	WebElement hospitalContainer;

	// ================= COMPONENT =================
	@FindBy(css = "[data-testid='hospital-finder-component']")
	WebElement finderComponent;

	// ================= SEARCH =================
	@FindBy(css = "[data-testid='hospital-search-input']")
	WebElement searchInput;

	@FindBy(css = "[data-testid='hospital-near-me-btn']")
	WebElement nearMeBtn;

	// ================= FILTERS =================
	@FindBy(css = "[data-testid='hospital-filters']")
	WebElement filterContainer;

	@FindBy(css = "[data-testid='hospital-filter-hospital']")
	WebElement filterHospital;

	@FindBy(css = "[data-testid='hospital-filter-clinic']")
	WebElement filterClinic;

	@FindBy(css = "[data-testid='hospital-filter-pharmacy']")
	WebElement filterPharmacy;

	// ================= STATES =================
	@FindBy(css = "[data-testid='hospital-loading']")
	WebElement loadingIndicator;

	@FindBy(css = "[data-testid='hospital-error']")
	WebElement errorMessage;

	@FindBy(css = "[data-testid='hospital-empty-state']")
	WebElement emptyState;

	@FindBy(css = "[data-testid='hospital-permission-denied']")
	WebElement permissionDenied;

	// ================= RESULTS =================
	@FindBy(css = "[data-testid='hospital-results']")
	WebElement resultsContainer;

	@FindBy(css = "[data-testid='hospital-results-count']")
	WebElement resultsCount;

	// ================= HEADER METHODS =================
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

	public boolean isHospitalPageVisible() {
		try {
			return hospitalPage.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// ================= COMPONENT METHODS =================
	public boolean isFinderComponentVisible() {
		try {
			return finderComponent.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// ================= SEARCH METHODS =================
	public boolean isSearchInputVisible() {
		try {
			return searchInput.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void enterSearchQuery(String query) {
		searchInput.clear();
		searchInput.sendKeys(query);
	}

	public void search(String query) {
		enterSearchQuery(query);
		waitForSearchResults();
	}

	public String getSearchInputValue() {
		return searchInput.getAttribute("value");
	}

	public boolean isNearMeButtonVisible() {
		try {
			return nearMeBtn.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void clickNearMeButton() {
		nearMeBtn.click();
	}

	public boolean isNearMeButtonEnabled() {
		return nearMeBtn.isEnabled();
	}

	public boolean isNearMeButtonDisabled() {
		return !nearMeBtn.isEnabled();
	}

	// ================= FILTER METHODS =================
	public boolean isFilterVisible() {
		try {
			return filterContainer.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void selectFilter(String filterType) {
		try {
			WebElement filter = driver.findElement(By.cssSelector("[data-testid='hospital-filter-" + filterType + "']"));
			filter.click();
			waitForSearchResults();
		} catch (Exception e) {
		}
	}

	public boolean isFilterSelected(String filterType) {
		try {
			WebElement filter = driver.findElement(By.cssSelector("[data-testid='hospital-filter-" + filterType + "']"));
			return filter.getAttribute("class").contains("bg-navy-900");
		} catch (Exception e) {
			return false;
		}
	}

	public void selectHospitalFilter() {
		selectFilter("hospital");
	}

	public void selectClinicFilter() {
		selectFilter("clinic");
	}

	public void selectPharmacyFilter() {
		selectFilter("pharmacy");
	}

	public String getActiveFilter() {
		if (isFilterSelected("hospital")) return "hospital";
		if (isFilterSelected("clinic")) return "clinic";
		if (isFilterSelected("pharmacy")) return "pharmacy";
		return "";
	}

	// ================= RESULT METHODS =================
	public boolean areResultsVisible() {
		try {
			return resultsContainer.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public int getHospitalResultCount() {
		try {
			List<WebElement> cards = driver.findElements(By.cssSelector("[data-testid^='hospital-card-']"));
			return cards.size();
		} catch (Exception e) {
			return 0;
		}
	}

	public boolean hasHospitalResult(String hospitalName) {
		try {
			List<WebElement> results = driver.findElements(By.xpath("//h3[contains(text(), '" + hospitalName + "')]"));
			return !results.isEmpty();
		} catch (Exception e) {
			return false;
		}
	}

	public String getHospitalName(int index) {
		try {
			WebElement name = driver.findElement(By.cssSelector("[data-testid='hospital-name-" + index + "']"));
			return name.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public String getHospitalAddress(int index) {
		try {
			WebElement address = driver.findElement(By.cssSelector("[data-testid='hospital-address-" + index + "']"));
			return address.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public String getHospitalRating(int index) {
		try {
			WebElement rating = driver.findElement(By.cssSelector("[data-testid='hospital-rating-" + index + "']"));
			return rating.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public String getHospitalStatus(int index) {
		try {
			WebElement status = driver.findElement(By.cssSelector("[data-testid='hospital-status-" + index + "']"));
			return status.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public String getHospitalDistance(int index) {
		try {
			WebElement distance = driver.findElement(By.cssSelector("[data-testid='hospital-distance-" + index + "']"));
			return distance.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public void clickGetDirections(int index) {
		try {
			WebElement directionsBtn = driver.findElement(By.cssSelector("[data-testid='hospital-directions-" + index + "']"));
			directionsBtn.click();
		} catch (Exception e) {
		}
	}

	public String getResultsCountText() {
		try {
			return resultsCount.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public int getResultsCountNumber() {
		try {
			String text = getResultsCountText();
			String[] parts = text.split(" ");
			if (parts.length > 1) {
				return Integer.parseInt(parts[1]);
			}
			return 0;
		} catch (Exception e) {
			return 0;
		}
	}

	// ================= STATE METHODS =================
	public boolean isLoadingDisplayed() {
		try {
			return loadingIndicator.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isErrorDisplayed() {
		try {
			return errorMessage.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getErrorMessage() {
		try {
			return errorMessage.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public boolean isErrorContains(String text) {
		return getErrorMessage().toLowerCase().contains(text.toLowerCase());
	}

	public boolean isEmptyStateDisplayed() {
		try {
			return emptyState.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isPermissionDeniedDisplayed() {
		try {
			return permissionDenied.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// ================= WAIT METHODS =================
	public void waitForSearchResults() {
		try {
			Thread.sleep(1000);
			wait.until(ExpectedConditions.invisibilityOf(loadingIndicator));
			Thread.sleep(500);
		} catch (Exception e) {
		}
	}

	public void waitForResultsToLoad() {
		try {
			wait.until(ExpectedConditions.visibilityOf(resultsContainer));
		} catch (Exception e) {
		}
	}

	public void waitForLoadingToDisappear() {
		try {
			wait.until(ExpectedConditions.invisibilityOf(loadingIndicator));
		} catch (Exception e) {
		}
	}

	public void waitForPageLoad() {
		wait.until(ExpectedConditions.visibilityOf(heading));
	}

	// ================= VERIFICATION METHODS =================
	public boolean verifyResultHasText(String text) {
		try {
			return resultsContainer.getText().toLowerCase().contains(text.toLowerCase());
		} catch (Exception e) {
			return false;
		}
	}

	public boolean verifyAllFiltersPresent() {
		return filterHospital.isDisplayed() &&
			   filterClinic.isDisplayed() &&
			   filterPharmacy.isDisplayed();
	}

	public boolean verifySearchWorks(String query) {
		search(query);
		return areResultsVisible() || isEmptyStateDisplayed() || isErrorDisplayed();
	}

	// ================= COMPLETE FLOW METHODS =================
	public void completeSearchFlow(String query) {
		search(query);
		waitForSearchResults();
	}

	public void completeNearMeFlow() {
		clickNearMeButton();
		waitForSearchResults();
	}

	public void completeFilterFlow(String filterType) {
		selectFilter(filterType);
		waitForSearchResults();
	}

	public void verifyHospitalCard(int index) {
		assert getHospitalName(index) != null && !getHospitalName(index).isEmpty() : "Hospital name should not be empty";
		assert getHospitalAddress(index) != null && !getHospitalAddress(index).isEmpty() : "Hospital address should not be empty";
	}
}
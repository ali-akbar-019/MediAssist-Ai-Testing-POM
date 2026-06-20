package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;
import java.util.List;

public class MedicinePage {

	WebDriver driver;
	WebDriverWait wait;

	public MedicinePage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);
	}

	// ================= HEADER =================
	@FindBy(css = "[data-testid='medicine-heading']")
	WebElement heading;

	// ================= SEARCH =================
	@FindBy(css = "[data-testid='medicine-search-input']")
	WebElement txtSearch;

	@FindBy(css = "[data-testid='medicine-search-button']")
	WebElement btnSearch;

	// ================= ERROR =================
	@FindBy(css = "[data-testid='medicine-error']")
	WebElement errorMsg;

	// ================= RESULT =================
	@FindBy(css = "[data-testid='medicine-result']")
	WebElement resultContainer;

	@FindBy(css = "[data-testid='medicine-name']")
	WebElement medicineName;

	@FindBy(css = "[data-testid='medicine-generic-name']")
	WebElement genericName;

	@FindBy(css = "[data-testid='medicine-dosage']")
	WebElement dosageInfo;

	@FindBy(css = "[data-testid='medicine-disclaimer']")
	WebElement disclaimer;

	@FindBy(css = "[data-testid='search-another-medicine']")
	WebElement searchAnotherBtn;

	// ================= SECTIONS =================
	@FindBy(css = "[data-testid='section-uses']")
	WebElement sectionUses;

	@FindBy(css = "[data-testid='section-sideEffects']")
	WebElement sectionSideEffects;

	@FindBy(css = "[data-testid='section-warnings']")
	WebElement sectionWarnings;

	@FindBy(css = "[data-testid='section-interactions']")
	WebElement sectionInteractions;

	// ================= COMMON MEDICINES =================
	@FindBy(css = "[data-testid='common-medicines-container']")
	WebElement commonMedicinesContainer;

	// ================= LOADING =================
	@FindBy(css = "[data-testid='medicine-loading']")
	WebElement loadingIndicator;

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

	// ================= SEARCH METHODS =================
	public boolean isSearchInputVisible() {
		try {
			return txtSearch.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void enterSearch(String query) {
		txtSearch.clear();
		txtSearch.sendKeys(query);
	}

	public void clickSearch() {
		btnSearch.click();
	}

	public void search(String query) {
		enterSearch(query);
		clickSearch();
	}

	public void searchWithEnter(String query) {
		txtSearch.clear();
		txtSearch.sendKeys(query);
		txtSearch.submit();
	}

	public String getSearchInputValue() {
		return txtSearch.getAttribute("value");
	}

	public boolean isSearchButtonEnabled() {
		return btnSearch.isEnabled();
	}

	public boolean isSearchButtonDisabled() {
		return !btnSearch.isEnabled();
	}

	// ================= COMMON MEDICINES (QUICK PICKS) =================
	public boolean isCommonMedicinesVisible() {
		try {
			return commonMedicinesContainer.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public int getCommonMedicinesCount() {
		try {
			List<WebElement> medicines = driver.findElements(By.cssSelector("[data-testid*='common-medicine-']"));
			return medicines.size();
		} catch (Exception e) {
			return 0;
		}
	}

	public void clickCommonMedicine(String medicineName) {
		try {
			WebElement medicine = driver.findElement(By.cssSelector("[data-testid='common-medicine-" + medicineName.toLowerCase() + "']"));
			medicine.click();
		} catch (Exception e) {
			// Fallback: click by text
			List<WebElement> medicines = driver.findElements(By.xpath("//button[contains(text(), '" + medicineName + "')]"));
			if (!medicines.isEmpty()) {
				medicines.get(0).click();
			}
		}
	}

	public boolean isCommonMedicineVisible(String medicineName) {
		try {
			WebElement medicine = driver.findElement(By.cssSelector("[data-testid='common-medicine-" + medicineName.toLowerCase() + "']"));
			return medicine.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// ================= LOADING STATE =================
	public boolean isLoadingDisplayed() {
		try {
			return loadingIndicator.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void waitForLoadingToDisappear() {
		try {
			wait.until(ExpectedConditions.invisibilityOf(loadingIndicator));
		} catch (Exception e) {
			// Loading might already be gone
		}
	}

	// ================= ERROR METHODS =================
	public boolean isErrorDisplayed() {
		try {
			return errorMsg.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getErrorMessage() {
		try {
			return errorMsg.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public boolean isErrorContains(String text) {
		return getErrorMessage().toLowerCase().contains(text.toLowerCase());
	}

	// ================= RESULT METHODS =================
	public boolean isResultDisplayed() {
		try {
			wait.until(ExpectedConditions.visibilityOf(resultContainer));
			return resultContainer.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getMedicineName() {
		try {
			return medicineName.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public String getGenericName() {
		try {
			return genericName.getText().replace("Generic name:", "").trim();
		} catch (Exception e) {
			return "";
		}
	}

	public String getDosageInfo() {
		try {
			return dosageInfo.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public boolean isDisclaimerVisible() {
		try {
			return disclaimer.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getDisclaimerText() {
		try {
			return disclaimer.getText();
		} catch (Exception e) {
			return "";
		}
	}

	// ================= SECTION METHODS =================
	public boolean isSectionVisible(String sectionId) {
		try {
			WebElement section = driver.findElement(By.cssSelector("[data-testid='section-" + sectionId + "']"));
			return section.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void expandSection(String sectionId) {
		try {
			WebElement section = driver.findElement(By.cssSelector("[data-testid='section-" + sectionId + "']"));
			section.click();
			// Wait for items to appear
			Thread.sleep(300);
		} catch (Exception e) {
		}
	}

	public void expandAllSections() {
		expandSection("uses");
		expandSection("sideEffects");
		expandSection("warnings");
		expandSection("interactions");
	}

	public int getSectionItemCount(String sectionId) {
		try {
			List<WebElement> items = driver.findElements(By.cssSelector("[data-testid*='section-" + sectionId + "-item-']"));
			return items.size();
		} catch (Exception e) {
			return 0;
		}
	}

	public String getSectionItemText(String sectionId, int index) {
		try {
			WebElement item = driver.findElement(By.cssSelector("[data-testid='section-" + sectionId + "-item-" + index + "']"));
			return item.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public List<WebElement> getSectionItems(String sectionId) {
		return driver.findElements(By.cssSelector("[data-testid*='section-" + sectionId + "-item-']"));
	}

	public boolean isSectionExpanded(String sectionId) {
		try {
			WebElement section = driver.findElement(By.cssSelector("[data-testid='section-" + sectionId + "']"));
			// Check if section content is visible - look for items
			List<WebElement> items = driver.findElements(By.cssSelector("[data-testid*='section-" + sectionId + "-item-']"));
			return !items.isEmpty();
		} catch (Exception e) {
			return false;
		}
	}

	// ================= SEARCH ANOTHER MEDICINE =================
	public void clickSearchAnotherMedicine() {
		try {
			searchAnotherBtn.click();
		} catch (Exception e) {
		}
	}

	public boolean isSearchAnotherMedicineVisible() {
		try {
			return searchAnotherBtn.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// ================= VERIFICATION METHODS =================
	public boolean verifyResultHasText(String text) {
		try {
			return resultContainer.getText().toLowerCase().contains(text.toLowerCase());
		} catch (Exception e) {
			return false;
		}
	}

	public boolean verifyAllSectionsPresent() {
		return isSectionVisible("uses") &&
			   isSectionVisible("sideEffects") &&
			   isSectionVisible("warnings") &&
			   isSectionVisible("interactions");
	}

	public boolean verifyAllSectionsHaveItems() {
		return getSectionItemCount("uses") > 0 &&
			   getSectionItemCount("sideEffects") > 0 &&
			   getSectionItemCount("warnings") > 0 &&
			   getSectionItemCount("interactions") > 0;
	}

	// ================= WAIT METHODS =================
	public void waitForResult() {
		wait.until(ExpectedConditions.visibilityOf(resultContainer));
	}

	public void waitForError() {
		wait.until(ExpectedConditions.visibilityOf(errorMsg));
	}

	public void waitForSearchToComplete() {
		waitForLoadingToDisappear();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
	}

	// ================= COMPLETE FLOW METHODS =================
	public void completeFullSearch(String medicineName) {
		search(medicineName);
		waitForSearchToComplete();
	}

	public void completeFullSearchWithCommonPick(String medicineName) {
		clickCommonMedicine(medicineName);
		waitForSearchToComplete();
	}

	public void verifyFullMedicineInfo(String expectedName) {
		waitForResult();
		assert getMedicineName().equalsIgnoreCase(expectedName) : 
			"Medicine name mismatch. Expected: " + expectedName + ", Actual: " + getMedicineName();
		assert !getGenericName().isEmpty() : "Generic name should not be empty";
		assert !getDosageInfo().isEmpty() : "Dosage info should not be empty";
		assert isDisclaimerVisible() : "Disclaimer should be visible";
		assert verifyAllSectionsPresent() : "All sections should be present";
		assert verifyAllSectionsHaveItems() : "All sections should have items";
	}
}
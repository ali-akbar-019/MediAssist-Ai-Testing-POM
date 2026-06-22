package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class UserManagementPage {

	WebDriver driver;
	WebDriverWait wait;

	public UserManagementPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		PageFactory.initElements(driver, this);
	}

	// ================= PAGE =================
	@FindBy(css = "[data-testid='user-management']")
	WebElement userManagement;

	// ================= HEADER =================
	@FindBy(css = "[data-testid='user-heading']")
	WebElement heading;

	@FindBy(css = "[data-testid='user-total-count']")
	WebElement totalCount;

	// ================= FILTERS =================
	@FindBy(css = "[data-testid='user-filters']")
	WebElement filtersContainer;

	@FindBy(css = "[data-testid='user-search-input']")
	WebElement searchInput;

	@FindBy(css = "[data-testid='user-role-filter'] select")
	WebElement roleFilterSelect;

	// ================= TABLE =================
	@FindBy(css = "[data-testid='user-table-container']")
	WebElement tableContainer;

	@FindBy(css = "[data-testid='user-table']")
	WebElement userTable;

	@FindBy(css = "[data-testid='user-empty-state']")
	WebElement emptyState;

	// ================= LOADING =================
	@FindBy(css = "[data-testid='user-loading']")
	WebElement loadingContainer;

	@FindBy(css = "[data-testid='user-loading-spinner']")
	WebElement loadingSpinner;

	@FindBy(css = "[data-testid='user-loading-text']")
	WebElement loadingText;

	// ================= PAGINATION =================
	@FindBy(css = "[data-testid='user-pagination']")
	WebElement paginationContainer;

	@FindBy(css = "[data-testid='user-pagination-info']")
	WebElement paginationInfo;

	@FindBy(css = "[data-testid='user-prev-page']")
	WebElement prevPageBtn;

	@FindBy(css = "[data-testid='user-next-page']")
	WebElement nextPageBtn;

	// ================= DELETE MODAL =================
	@FindBy(css = "[data-testid='delete-modal']")
	WebElement deleteModal;

	@FindBy(css = "[data-testid='delete-modal-title']")
	WebElement deleteModalTitle;

	@FindBy(css = "[data-testid='delete-modal-cancel']")
	WebElement deleteModalCancel;

	@FindBy(css = "[data-testid='delete-modal-confirm']")
	WebElement deleteModalConfirm;

	// ================= HEADER METHODS =================
	public boolean isPageVisible() {
		try {
			return userManagement.isDisplayed();
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

	public int getTotalCount() {
		try {
			String text = totalCount.getText();
			String[] parts = text.split(":");
			if (parts.length > 1) {
				return Integer.parseInt(parts[1].replaceAll("[^0-9]", "").trim());
			}
			return 0;
		} catch (Exception e) {
			return 0;
		}
	}

	// ================= FILTER METHODS =================
	public boolean isFiltersVisible() {
		try {
			return filtersContainer.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void enterSearchQuery(String query) {
		searchInput.clear();
		searchInput.sendKeys(query);
	}

	public String getSearchQuery() {
		return searchInput.getAttribute("value");
	}

	public void selectRoleFilter(String role) {
		Select select = new Select(roleFilterSelect);
		select.selectByValue(role);
	}

	public String getSelectedRole() {
		Select select = new Select(roleFilterSelect);
		return select.getFirstSelectedOption().getAttribute("value");
	}

	public boolean isRoleFilterVisible() {
		try {
			return roleFilterSelect.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void clearSearch() {
		searchInput.clear();
	}

	// ================= TABLE METHODS =================
	public boolean isTableVisible() {
		try {
			return tableContainer.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isUserTableVisible() {
		try {
			return userTable.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public int getUserRowCount() {
		try {
			List<WebElement> rows = driver.findElements(By.cssSelector("[data-testid*='user-row-']"));
			return rows.size();
		} catch (Exception e) {
			return 0;
		}
	}

	public WebElement getUserRow(int index) {
		try {
			return driver.findElement(By.cssSelector("[data-testid='user-row-" + index + "']"));
		} catch (Exception e) {
			return null;
		}
	}

	public String getUserName(int index) {
		try {
			WebElement name = driver.findElement(By.cssSelector("[data-testid='user-name-" + index + "']"));
			return name.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public String getUserEmail(int index) {
		try {
			WebElement email = driver.findElement(By.cssSelector("[data-testid='user-email-" + index + "']"));
			return email.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public String getUserRole(int index) {
		try {
			WebElement role = driver.findElement(By.cssSelector("[data-testid='user-role-" + index + "']"));
			return role.getText().replaceAll("[^a-zA-Z]", "").toLowerCase();
		} catch (Exception e) {
			return "";
		}
	}

	public String getUserJoinedDate(int index) {
		try {
			WebElement date = driver.findElement(By.cssSelector("[data-testid='user-joined-" + index + "']"));
			return date.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public String getUserStatus(int index) {
		try {
			WebElement status = driver.findElement(By.cssSelector("[data-testid='user-status-" + index + "']"));
			return status.getText();
		} catch (Exception e) {
			return "";
		}
	}

	// ================= USER ACTIONS =================
	public void toggleUserRole(int index) {
		try {
			WebElement toggleBtn = driver.findElement(By.cssSelector("[data-testid='user-toggle-role-" + index + "']"));
			toggleBtn.click();
			waitForTableToLoad();
		} catch (Exception e) {
		}
	}

	public boolean isRoleToggleVisible(int index) {
		try {
			WebElement toggleBtn = driver.findElement(By.cssSelector("[data-testid='user-toggle-role-" + index + "']"));
			return toggleBtn.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void clickDeleteUser(int index) {
		try {
			WebElement deleteBtn = driver.findElement(By.cssSelector("[data-testid='user-delete-" + index + "']"));
			deleteBtn.click();
		} catch (Exception e) {
		}
	}

	// ================= DELETE MODAL METHODS =================
	public boolean isDeleteModalVisible() {
		try {
			return deleteModal.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getDeleteModalTitle() {
		try {
			return deleteModalTitle.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public void clickDeleteModalCancel() {
		try {
			deleteModalCancel.click();
		} catch (Exception e) {
		}
	}

	public void clickDeleteModalConfirm() {
		try {
			deleteModalConfirm.click();
		} catch (Exception e) {
		}
	}

	public void waitForDeleteModalToDisappear() {
		try {
			wait.until(ExpectedConditions.invisibilityOf(deleteModal));
		} catch (Exception e) {
		}
	}

	// ================= EMPTY STATE =================
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

	// ================= LOADING METHODS =================
	public boolean isLoadingVisible() {
		try {
			return loadingContainer.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void waitForLoadingToDisappear() {
		try {
			wait.until(ExpectedConditions.invisibilityOf(loadingContainer));
		} catch (Exception e) {
		}
	}

	// ================= PAGINATION METHODS =================
	public boolean isPaginationVisible() {
		try {
			return paginationContainer.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getPaginationInfo() {
		try {
			return paginationInfo.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public int getCurrentPage() {
		try {
			String text = getPaginationInfo();
			String[] parts = text.split(" ");
			if (parts.length > 1) {
				return Integer.parseInt(parts[1]);
			}
			return 1;
		} catch (Exception e) {
			return 1;
		}
	}

	public int getTotalPages() {
		try {
			String text = getPaginationInfo();
			String[] parts = text.split(" ");
			if (parts.length > 3) {
				return Integer.parseInt(parts[3]);
			}
			return 1;
		} catch (Exception e) {
			return 1;
		}
	}

	public void goToNextPage() {
		nextPageBtn.click();
		waitForTableToLoad();
	}

	public void goToPreviousPage() {
		prevPageBtn.click();
		waitForTableToLoad();
	}

	public boolean isPrevPageDisabled() {
		return !prevPageBtn.isEnabled();
	}

	public boolean isNextPageDisabled() {
		return !nextPageBtn.isEnabled();
	}

	public boolean isPrevPageVisible() {
		try {
			return prevPageBtn.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isNextPageVisible() {
		try {
			return nextPageBtn.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// ================= WAIT METHODS =================
	public void waitForPageLoad() {
		wait.until(ExpectedConditions.visibilityOf(heading));
	}

	public void waitForTableToLoad() {
		try {
			waitForLoadingToDisappear();
			wait.until(ExpectedConditions.visibilityOf(tableContainer));
		} catch (Exception e) {
		}
	}

	public void waitForSearchResults() {
		try {
			Thread.sleep(1000);
			waitForTableToLoad();
		} catch (InterruptedException e) {
		}
	}

	public void waitForRoleUpdate() {
		try {
			Thread.sleep(2000);
			waitForTableToLoad();
		} catch (InterruptedException e) {
		}
	}

	public void waitForDeleteConfirmation() {
		try {
			Thread.sleep(2000);
			waitForTableToLoad();
		} catch (InterruptedException e) {
		}
	}

	// ================= VERIFICATION METHODS =================
	public boolean verifyUserExists(String userName) {
		int count = getUserRowCount();
		for (int i = 0; i < count; i++) {
			if (getUserName(i).equalsIgnoreCase(userName)) {
				return true;
			}
		}
		return false;
	}

	public boolean verifyUserHasRole(String userName, String expectedRole) {
		int count = getUserRowCount();
		for (int i = 0; i < count; i++) {
			if (getUserName(i).equalsIgnoreCase(userName)) {
				return getUserRole(i).equalsIgnoreCase(expectedRole);
			}
		}
		return false;
	}

	public boolean verifyAllUsersHaveData() {
		int count = getUserRowCount();
		for (int i = 0; i < count; i++) {
			if (getUserName(i).isEmpty()) return false;
			if (getUserEmail(i).isEmpty()) return false;
			if (getUserRole(i).isEmpty()) return false;
			if (getUserJoinedDate(i).isEmpty()) return false;
		}
		return true;
	}

	public boolean verifySearchWorks(String query) {
		enterSearchQuery(query);
		waitForSearchResults();
		int count = getUserRowCount();
		if (count == 0) {
			return isEmptyStateVisible();
		}
		for (int i = 0; i < count; i++) {
			String name = getUserName(i);
			String email = getUserEmail(i);
			if (name.toLowerCase().contains(query.toLowerCase()) ||
			    email.toLowerCase().contains(query.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	// ================= COMPLETE FLOW METHODS =================
	public void completeFullPageLoad() {
		waitForPageLoad();
		waitForTableToLoad();
		assert isPageVisible() : "User management page should be visible";
		assert isHeadingVisible() : "Heading should be visible";
		assert isFiltersVisible() : "Filters should be visible";
		assert isTableVisible() : "Table should be visible";
	}

	public void completeSearchFlow(String query) {
		enterSearchQuery(query);
		waitForSearchResults();
	}

	public void completeRoleFilterFlow(String role) {
		selectRoleFilter(role);
		waitForTableToLoad();
	}

	public void completePaginationFlow() {
		int totalPages = getTotalPages();
		if (totalPages > 1) {
			for (int i = 1; i < totalPages; i++) {
				goToNextPage();
				assert getCurrentPage() == i + 1 : "Should be on page " + (i + 1);
				assert getUserRowCount() > 0 : "Page should have users";
			}
			for (int i = totalPages; i > 1; i--) {
				goToPreviousPage();
				assert getCurrentPage() == i - 1 : "Should be on page " + (i - 1);
			}
		}
	}

	public void completeRoleToggleFlow(int index) {
		String beforeRole = getUserRole(index);
		toggleUserRole(index);
		waitForRoleUpdate();
		String afterRole = getUserRole(index);
		assert !beforeRole.equals(afterRole) : "Role should have changed";
	}

	public void completeDeleteUserFlow(int index) {
		clickDeleteUser(index);
		assert isDeleteModalVisible() : "Delete modal should appear";
		clickDeleteModalConfirm();
		waitForDeleteConfirmation();
	}
}

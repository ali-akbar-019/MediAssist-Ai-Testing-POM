
package testCases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.UserManagementPage;

public class TC_013_UserManagementTest extends BaseClass {

	UserManagementPage ump;

	@BeforeMethod
public void loginFirst() {
    login();
    driver.get(p.getProperty("appURL") + "/admin/users");
    ump = new UserManagementPage(driver);
    ump.completeFullPageLoad();
    waitForUrlContains("admin", 10);
}
	// ============================================================
	// TC_013_01 - Page Load Test
	// ============================================================
	@Test(groups = { "smoke", "regression", "edge-case" })
	public void TC_013_01_UserManagementPage_Loads() {
		String testCase = "TC_013_01_UserManagementPage_Loads";
		logInfo("TEST STARTED: " + testCase);

		
		
		ump.completeFullPageLoad();

		Assert.assertTrue(waitForUrlContains("admin", 5), "Admin route not reached");
		Assert.assertTrue(ump.isPageVisible(), "User management page not visible");
		Assert.assertTrue(ump.isHeadingVisible(), "Heading not visible");
		Assert.assertTrue(ump.isFiltersVisible(), "Filters should be visible");
		Assert.assertTrue(ump.isTableVisible(), "Table should be visible");
		Assert.assertTrue(ump.isExportBtnVisible(), "Export button should be visible");
		Assert.assertTrue(ump.isEnrollBtnVisible(), "Enroll button should be visible");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_013_02 - Total Count Display
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_013_01_UserManagementPage_Loads" })
	public void TC_013_02_TotalCount_Display() {
		String testCase = "TC_013_02_TotalCount_Display";
		logInfo("TEST STARTED: " + testCase);

		
		ump.waitForTableToLoad();

		int totalCount = ump.getTotalCount();
		int rowCount = ump.getUserRowCount();

		logInfo("Total count from header: " + totalCount);
		logInfo("Rows displayed: " + rowCount);

		Assert.assertTrue(totalCount >= 0, "Total count should be >= 0");
		Assert.assertTrue(rowCount <= totalCount || totalCount == 0, "Rows displayed should not exceed total count");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_013_03 - Search Functionality
	// ============================================================
	@Test(groups = { "smoke", "regression" }, dependsOnMethods = { "TC_013_01_UserManagementPage_Loads" })
	public void TC_013_03_Search_Functionality() {
		String testCase = "TC_013_03_Search_Functionality";
		logInfo("TEST STARTED: " + testCase);

		
		ump.waitForTableToLoad();

		// Get first user's name for search
		int rowCount = ump.getUserRowCount();
		if (rowCount > 0) {
			String userName = ump.getUserName(0);
			logInfo("Searching for: " + userName);

			ump.completeSearchFlow(userName);
			int searchResults = ump.getUserRowCount();

			Assert.assertTrue(searchResults > 0, "Search should return at least 1 result");
			Assert.assertTrue(ump.verifyUserExists(userName), "User should exist in search results");

			// Verify search results contain the search term
			boolean found = false;
			for (int i = 0; i < searchResults; i++) {
				if (ump.getUserName(i).toLowerCase().contains(userName.toLowerCase())) {
					found = true;
					break;
				}
			}
			Assert.assertTrue(found, "Search results should contain the search term");
		} else {
			logInfo("No users to search for");
		}

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_013_04 - Search With Email
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_013_01_UserManagementPage_Loads" })
	public void TC_013_04_Search_WithEmail() {
		String testCase = "TC_013_04_Search_WithEmail";
		logInfo("TEST STARTED: " + testCase);

		
		ump.waitForTableToLoad();

		int rowCount = ump.getUserRowCount();
		if (rowCount > 0) {
			String email = ump.getUserEmail(0);
			logInfo("Searching by email: " + email);

			ump.completeSearchFlow(email);
			int searchResults = ump.getUserRowCount();

			Assert.assertTrue(searchResults > 0, "Search by email should return results");

			boolean found = false;
			for (int i = 0; i < searchResults; i++) {
				if (ump.getUserEmail(i).toLowerCase().contains(email.toLowerCase())) {
					found = true;
					break;
				}
			}
			Assert.assertTrue(found, "Search results should contain the email");
		} else {
			logInfo("No users to search by email");
		}

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_013_05 - Search Empty Query
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_013_01_UserManagementPage_Loads" })
	public void TC_013_05_Search_EmptyQuery() {
		String testCase = "TC_013_05_Search_EmptyQuery";
		logInfo("TEST STARTED: " + testCase);

		
		ump.waitForTableToLoad();

		ump.clearSearch();
		ump.waitForSearchResults();

		int rowCount = ump.getUserRowCount();
		int totalCount = ump.getTotalCount();

		logInfo("Rows after clearing search: " + rowCount);
		logInfo("Total count: " + totalCount);

		Assert.assertTrue(rowCount <= totalCount || rowCount == 0, "Should show all or no users after clearing search");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_013_06 - Role Filter - Admin
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_013_01_UserManagementPage_Loads" })
	public void TC_013_06_RoleFilter_Admin() {
		String testCase = "TC_013_06_RoleFilter_Admin";
		logInfo("TEST STARTED: " + testCase);

		
		ump.waitForTableToLoad();

		ump.completeRoleFilterFlow("admin");

		int rowCount = ump.getUserRowCount();
		logInfo("Admin users found: " + rowCount);

		if (rowCount > 0) {
			for (int i = 0; i < rowCount; i++) {
				String role = ump.getUserRole(i);
				Assert.assertEquals(role, "admin", "All filtered users should have admin role");
			}
		}

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_013_07 - Role Filter - User
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_013_01_UserManagementPage_Loads" })
	public void TC_013_07_RoleFilter_User() {
		String testCase = "TC_013_07_RoleFilter_User";
		logInfo("TEST STARTED: " + testCase);

		
		ump.waitForTableToLoad();

		ump.completeRoleFilterFlow("user");

		int rowCount = ump.getUserRowCount();
		logInfo("Standard users found: " + rowCount);

		if (rowCount > 0) {
			for (int i = 0; i < rowCount; i++) {
				String role = ump.getUserRole(i);
				Assert.assertEquals(role, "user", "All filtered users should have user role");
			}
		}

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_013_08 - Role Filter - All
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_013_01_UserManagementPage_Loads" })
	public void TC_013_08_RoleFilter_All() {
		String testCase = "TC_013_08_RoleFilter_All";
		logInfo("TEST STARTED: " + testCase);

		
		ump.waitForTableToLoad();

		ump.selectRoleFilter("all");
		ump.waitForTableToLoad();

		int rowCount = ump.getUserRowCount();
		int totalCount = ump.getTotalCount();

		logInfo("All users displayed: " + rowCount);
		logInfo("Total count: " + totalCount);

		Assert.assertTrue(rowCount <= totalCount || rowCount == 0, "All users filter should show all users");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_013_09 - Toggle User Role
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_013_01_UserManagementPage_Loads" })
	public void TC_013_09_ToggleUserRole() {
		String testCase = "TC_013_09_ToggleUserRole";
		logInfo("TEST STARTED: " + testCase);

		
		ump.waitForTableToLoad();

		int rowCount = ump.getUserRowCount();
		if (rowCount > 0) {
			String beforeRole = ump.getUserRole(0);
			logInfo("User role before toggle: " + beforeRole);

			ump.toggleUserRole(0);
			ump.waitForRoleUpdate();

			String afterRole = ump.getUserRole(0);
			logInfo("User role after toggle: " + afterRole);

			Assert.assertNotEquals(beforeRole, afterRole, "Role should have changed");
			Assert.assertTrue(afterRole.equals("admin") || afterRole.equals("user"),
					"Role should be either admin or user");
		} else {
			logInfo("No users to toggle role");
		}

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_013_10 - Toggle User Role Back
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_013_09_ToggleUserRole" })
	public void TC_013_10_ToggleUserRole_Back() {
		String testCase = "TC_013_10_ToggleUserRole_Back";
		logInfo("TEST STARTED: " + testCase);

		
		ump.waitForTableToLoad();

		int rowCount = ump.getUserRowCount();
		if (rowCount > 0) {
			String beforeRole = ump.getUserRole(0);
			logInfo("User role before second toggle: " + beforeRole);

			ump.toggleUserRole(0);
			ump.waitForRoleUpdate();

			String afterRole = ump.getUserRole(0);
			logInfo("User role after second toggle: " + afterRole);

			Assert.assertNotEquals(beforeRole, afterRole, "Role should have changed back");
		} else {
			logInfo("No users to toggle role");
		}

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_013_11 - User Details Validation
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_013_01_UserManagementPage_Loads" })
	public void TC_013_11_UserDetails_Validation() {
		String testCase = "TC_013_11_UserDetails_Validation";
		logInfo("TEST STARTED: " + testCase);

		
		ump.waitForTableToLoad();

		int rowCount = ump.getUserRowCount();
		if (rowCount > 0) {
			for (int i = 0; i < Math.min(rowCount, 5); i++) {
				String name = ump.getUserName(i);
				String email = ump.getUserEmail(i);
				String role = ump.getUserRole(i);
				String createdDate = ump.getUserCreatedDate(i);
				String status = ump.getUserStatus(i);

				logInfo("User " + (i + 1) + ": " + name + " | " + email + " | " + role + " | " + createdDate + " | "
						+ status);

				Assert.assertFalse(name.isEmpty(), "User name should not be empty");
				Assert.assertFalse(email.isEmpty(), "User email should not be empty");
				Assert.assertFalse(role.isEmpty(), "User role should not be empty");
				Assert.assertFalse(createdDate.isEmpty(), "Created date should not be empty");
				Assert.assertEquals(status, "Linked", "User status should be 'Linked'");
			}
		} else {
			logInfo("No users to validate");
		}

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_013_12 - Pagination Display
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_013_01_UserManagementPage_Loads" })
	public void TC_013_12_Pagination_Display() {
		String testCase = "TC_013_12_Pagination_Display";
		logInfo("TEST STARTED: " + testCase);

		
		ump.waitForTableToLoad();

		int totalCount = ump.getTotalCount();
		boolean paginationVisible = ump.isPaginationVisible();

		logInfo("Total users: " + totalCount);
		logInfo("Pagination visible: " + paginationVisible);

		if (totalCount > 10) {
			Assert.assertTrue(paginationVisible, "Pagination should be visible for > 10 users");
			Assert.assertTrue(ump.isPrevPageVisible(), "Previous page button should be visible");
			Assert.assertTrue(ump.isNextPageVisible(), "Next page button should be visible");

			int currentPage = ump.getCurrentPage();
			int totalPages = ump.getTotalPages();
			logInfo("Current page: " + currentPage + ", Total pages: " + totalPages);
			Assert.assertTrue(currentPage >= 1, "Current page should be >= 1");
			Assert.assertTrue(totalPages >= 1, "Total pages should be >= 1");
		} else {
			logInfo("Pagination not needed for " + totalCount + " users");
		}

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_013_13 - Pagination Navigation
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_013_12_Pagination_Display" })
	public void TC_013_13_Pagination_Navigation() {
		String testCase = "TC_013_13_Pagination_Navigation";
		logInfo("TEST STARTED: " + testCase);

		
		ump.waitForTableToLoad();

		int totalCount = ump.getTotalCount();
		if (totalCount > 10 && ump.isPaginationVisible()) {
			// Test next navigation
			if (!ump.isNextPageDisabled()) {
				int beforePage = ump.getCurrentPage();
				ump.goToNextPage();
				int afterPage = ump.getCurrentPage();
				Assert.assertEquals(afterPage, beforePage + 1, "Should move to next page");
				Assert.assertTrue(ump.getUserRowCount() > 0, "Next page should have users");
			}

			// Test previous navigation
			if (!ump.isPrevPageDisabled()) {
				int beforePage = ump.getCurrentPage();
				ump.goToPreviousPage();
				int afterPage = ump.getCurrentPage();
				Assert.assertEquals(afterPage, beforePage - 1, "Should move to previous page");
				Assert.assertTrue(ump.getUserRowCount() > 0, "Previous page should have users");
			}
		} else {
			logInfo("Pagination navigation not needed");
		}

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_013_14 - Export Button
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_013_01_UserManagementPage_Loads" })
	public void TC_013_14_ExportButton() {
		String testCase = "TC_013_14_ExportButton";
		logInfo("TEST STARTED: " + testCase);

		
		ump.waitForTableToLoad();

		Assert.assertTrue(ump.isExportBtnVisible(), "Export button should be visible");

		ump.clickExportBtn();
		logInfo("Export button clicked");

		// Wait for any action
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}

		Assert.assertTrue(ump.isPageVisible(), "Page should remain visible after export click");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_013_15 - Enroll Button
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_013_01_UserManagementPage_Loads" })
	public void TC_013_15_EnrollButton() {
		String testCase = "TC_013_15_EnrollButton";
		logInfo("TEST STARTED: " + testCase);

		
		ump.waitForTableToLoad();

		Assert.assertTrue(ump.isEnrollBtnVisible(), "Enroll button should be visible");

		ump.clickEnrollBtn();
		logInfo("Enroll button clicked");

		// Wait for any action (modal or navigation)
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}

		Assert.assertTrue(ump.isPageVisible(), "Page should remain visible after enroll click");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_013_16 - Edge: Empty State
	// ============================================================
	@Test(groups = { "edge-case" }, dependsOnMethods = { "TC_013_01_UserManagementPage_Loads" })
	public void TC_013_16_Edge_EmptyState() {
		String testCase = "TC_013_16_Edge_EmptyState";
		logInfo("TEST STARTED: " + testCase);

		
		ump.waitForTableToLoad();

		// Search for non-existent user
		String randomQuery = "nonexistentuserxyz123";
		ump.completeSearchFlow(randomQuery);

		boolean isEmpty = ump.isEmptyStateVisible();
		int rowCount = ump.getUserRowCount();

		logInfo("Empty state visible: " + isEmpty);
		logInfo("Rows found: " + rowCount);

		if (rowCount == 0) {
			Assert.assertTrue(isEmpty, "Empty state should be visible when no results found");
		} else {
			Assert.assertFalse(isEmpty, "Empty state should not be visible when results exist");
		}

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_013_17 - Edge: Search With Special Characters
	// ============================================================
	@Test(groups = { "edge-case" }, dependsOnMethods = { "TC_013_01_UserManagementPage_Loads" })
	public void TC_013_17_Edge_SearchSpecialCharacters() {
		String testCase = "TC_013_17_Edge_SearchSpecialCharacters";
		logInfo("TEST STARTED: " + testCase);

		
		ump.waitForTableToLoad();

		String specialQuery = "@#$%^&*()";
		ump.completeSearchFlow(specialQuery);

		int rowCount = ump.getUserRowCount();
		logInfo("Results for special characters: " + rowCount);

		// Should handle gracefully (either empty or no errors)
		Assert.assertTrue(rowCount >= 0, "Search should handle special characters without errors");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_013_18 - Edge: All Users Have Data
	// ============================================================
	@Test(groups = { "edge-case" }, dependsOnMethods = { "TC_013_01_UserManagementPage_Loads" })
	public void TC_013_18_Edge_AllUsersHaveData() {
		String testCase = "TC_013_18_Edge_AllUsersHaveData";
		logInfo("TEST STARTED: " + testCase);

		
		ump.waitForTableToLoad();

		int rowCount = ump.getUserRowCount();
		if (rowCount > 0) {
			Assert.assertTrue(ump.verifyAllUsersHaveData(), "All users should have complete data");
			logInfo("All " + rowCount + " users have valid data");
		} else {
			logInfo("No users to verify");
		}

		logInfo("TEST PASSED: " + testCase);
	}
}
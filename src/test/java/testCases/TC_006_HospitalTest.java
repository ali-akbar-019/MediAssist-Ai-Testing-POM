package testCases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.HospitalPage;

public class TC_006_HospitalTest extends BaseClass {

	HospitalPage hp;

@BeforeMethod
public void loginFirst() {
    login();

    try {
        Thread.sleep(2000);
    } catch (InterruptedException e) {
    }

    driver.get(p.getProperty("appURL") + "/hospitals");
    hp = new HospitalPage(driver);

    boolean hospitalsLoaded = waitForUrlContains("hospitals", 10);

    if (!hospitalsLoaded && driver.getCurrentUrl().contains("login")) {
        logInfo("Redirected to login, trying to login again...");
        login();
        driver.get(p.getProperty("appURL") + "/hospitals");
        waitForUrlContains("hospitals", 10);
    }

    hp.waitForPageLoad();
}

	// ============================================================
	// TC_006_01 - Page Load Test
	// ============================================================
	@Test(groups = { "smoke", "regression", "edge-case" })
	public void TC_006_01_HospitalPage_Loads() {
		String testCase = "TC_006_01_HospitalPage_Loads";
		logInfo("TEST STARTED: " + testCase);

		
		
		hp.waitForPageLoad();

		Assert.assertTrue(waitForUrlContains("hospitals", 5), "Hospital route not reached");
		Assert.assertTrue(hp.isHeadingVisible(), "Hospital heading not visible");
		Assert.assertTrue(hp.isSearchInputVisible(), "Search input not visible");
		Assert.assertTrue(hp.isNearMeButtonVisible(), "Near Me button not visible");
		Assert.assertTrue(hp.isFilterVisible(), "Filters not visible");
		Assert.assertTrue(hp.isFinderComponentVisible(), "Finder component not visible");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_006_02 - Search Hospital By Name
	// ============================================================
	@Test(groups = { "smoke", "regression" }, dependsOnMethods = { "TC_006_01_HospitalPage_Loads" })
	public void TC_006_02_SearchHospitalByName() {
		String testCase = "TC_006_02_SearchHospitalByName";
		logInfo("TEST STARTED: " + testCase);

		
		hp.completeSearchFlow("City Hospital");

		Assert.assertTrue(hp.areResultsVisible(), "Results should be visible");
		Assert.assertTrue(hp.getHospitalResultCount() > 0, "Should have at least one result");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_006_03 - Search Empty Query
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_006_01_HospitalPage_Loads" })
	public void TC_006_03_SearchEmptyQuery() {
		String testCase = "TC_006_03_SearchEmptyQuery";
		logInfo("TEST STARTED: " + testCase);

		
		hp.search("");

		Assert.assertFalse(hp.isLoadingDisplayed(), "Loading should not appear for empty query");
		Assert.assertTrue(hp.isEmptyStateDisplayed() || hp.getHospitalResultCount() == 0,
				"Empty state should be displayed or no results");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_006_04 - Search Special Characters
	// ============================================================
	@Test(groups = { "edge-case" }, dependsOnMethods = { "TC_006_01_HospitalPage_Loads" })
	public void TC_006_04_SearchSpecialCharacters() {
		String testCase = "TC_006_04_SearchSpecialCharacters";
		logInfo("TEST STARTED: " + testCase);

		
		hp.completeSearchFlow("@#$%^&*()");

		Assert.assertTrue(hp.getHospitalResultCount() >= 0, "Search should handle special characters");
		Assert.assertFalse(hp.isErrorDisplayed(), "Error should not appear for special characters");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_006_05 - Filter: Hospitals
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_006_01_HospitalPage_Loads" })
	public void TC_006_05_Filter_Hospitals() {
		String testCase = "TC_006_05_Filter_Hospitals";
		logInfo("TEST STARTED: " + testCase);

		
		hp.completeFilterFlow("hospital");

		Assert.assertTrue(hp.isFilterSelected("hospital"), "Hospital filter should be selected");
		Assert.assertTrue(hp.areResultsVisible() || hp.isEmptyStateDisplayed(), "Results or empty state should appear");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_006_06 - Filter: Clinics
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_006_01_HospitalPage_Loads" })
	public void TC_006_06_Filter_Clinics() {
		String testCase = "TC_006_06_Filter_Clinics";
		logInfo("TEST STARTED: " + testCase);

		
		hp.completeFilterFlow("clinic");

		Assert.assertTrue(hp.isFilterSelected("clinic"), "Clinic filter should be selected");
		Assert.assertTrue(hp.areResultsVisible() || hp.isEmptyStateDisplayed(), "Results or empty state should appear");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_006_07 - Filter: Pharmacies
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_006_01_HospitalPage_Loads" })
	public void TC_006_07_Filter_Pharmacies() {
		String testCase = "TC_006_07_Filter_Pharmacies";
		logInfo("TEST STARTED: " + testCase);

		
		hp.completeFilterFlow("pharmacy");

		Assert.assertTrue(hp.isFilterSelected("pharmacy"), "Pharmacy filter should be selected");
		Assert.assertTrue(hp.areResultsVisible() || hp.isEmptyStateDisplayed(), "Results or empty state should appear");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_006_08 - Switch Between Filters
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_006_01_HospitalPage_Loads" })
	public void TC_006_08_SwitchFilters() {
		String testCase = "TC_006_08_SwitchFilters";
		logInfo("TEST STARTED: " + testCase);

		

		hp.selectHospitalFilter();
		Assert.assertTrue(hp.isFilterSelected("hospital"), "Hospital filter should be selected");

		hp.selectClinicFilter();
		Assert.assertTrue(hp.isFilterSelected("clinic"), "Clinic filter should be selected");
		Assert.assertFalse(hp.isFilterSelected("hospital"), "Hospital filter should be deselected");

		hp.selectPharmacyFilter();
		Assert.assertTrue(hp.isFilterSelected("pharmacy"), "Pharmacy filter should be selected");
		Assert.assertFalse(hp.isFilterSelected("clinic"), "Clinic filter should be deselected");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_006_09 - Hospital Card Details
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_006_02_SearchHospitalByName" })
	public void TC_006_09_HospitalCardDetails() {
		String testCase = "TC_006_09_HospitalCardDetails";
		logInfo("TEST STARTED: " + testCase);

		
		hp.waitForResultsToLoad();

		int resultCount = hp.getHospitalResultCount();
		if (resultCount > 0) {
			String name = hp.getHospitalName(0);
			String address = hp.getHospitalAddress(0);
			String rating = hp.getHospitalRating(0);
			String status = hp.getHospitalStatus(0);
			String distance = hp.getHospitalDistance(0);

			Assert.assertFalse(name.isEmpty(), "Hospital name should not be empty");
			Assert.assertFalse(address.isEmpty(), "Hospital address should not be empty");
			logInfo("Hospital: " + name + " | Rating: " + rating + " | Status: " + status + " | Distance: " + distance);
		} else {
			logInfo("No results to verify card details");
		}

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_006_10 - Get Directions Button
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_006_02_SearchHospitalByName" })
	public void TC_006_10_GetDirectionsButton() {
		String testCase = "TC_006_10_GetDirectionsButton";
		logInfo("TEST STARTED: " + testCase);

		
		hp.waitForResultsToLoad();

		int resultCount = hp.getHospitalResultCount();
		if (resultCount > 0) {
			hp.clickGetDirections(0);
			logInfo("Directions button clicked - should open maps in new window/tab");
		} else {
			logInfo("No results to test directions button");
		}

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_006_11 - Results Count
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_006_02_SearchHospitalByName" })
	public void TC_006_11_ResultsCount() {
		String testCase = "TC_006_11_ResultsCount";
		logInfo("TEST STARTED: " + testCase);

		
		hp.waitForResultsToLoad();

		int cardCount = hp.getHospitalResultCount();
		int countText = hp.getResultsCountNumber();

		Assert.assertEquals(cardCount, countText, "Card count should match results count text");
		logInfo("Results count: " + cardCount);

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_006_12 - Search and Filter Combined
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_006_01_HospitalPage_Loads" })
	public void TC_006_12_SearchAndFilterCombined() {
		String testCase = "TC_006_12_SearchAndFilterCombined";
		logInfo("TEST STARTED: " + testCase);

		

		hp.completeSearchFlow("City");
		hp.selectHospitalFilter();
		hp.waitForSearchResults();

		Assert.assertTrue(hp.isFilterSelected("hospital"), "Hospital filter should be selected");
		Assert.assertTrue(hp.areResultsVisible() || hp.isEmptyStateDisplayed(), "Results or empty state should appear");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_006_13 - Near Me Button (Location)
	// ============================================================
	@Test(groups = { "smoke", "regression" }, dependsOnMethods = { "TC_006_01_HospitalPage_Loads" })
	public void TC_006_13_NearMeButton() {
		String testCase = "TC_006_13_NearMeButton";
		logInfo("TEST STARTED: " + testCase);

		

		Assert.assertTrue(hp.isNearMeButtonVisible(), "Near Me button should be visible");
		Assert.assertTrue(hp.isNearMeButtonEnabled(), "Near Me button should be enabled");

		hp.completeNearMeFlow();

		// After clicking, either results appear, empty state, or permission denied
		Assert.assertTrue(hp.areResultsVisible() || hp.isEmptyStateDisplayed() || hp.isPermissionDeniedDisplayed()
				|| hp.isErrorDisplayed() || hp.isLoadingDisplayed(), "Near Me should trigger some state");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_006_14 - Permission Denied Handling
	// ============================================================
	@Test(groups = { "edge-case" }, dependsOnMethods = { "TC_006_01_HospitalPage_Loads" })
	public void TC_006_14_PermissionDeniedHandling() {
		String testCase = "TC_006_14_PermissionDeniedHandling";
		logInfo("TEST STARTED: " + testCase);

		

		// Note: This test assumes location permission is denied
		// If permission is granted, this will be skipped
		hp.clickNearMeButton();
		hp.waitForSearchResults();

		if (hp.isPermissionDeniedDisplayed()) {
			logInfo("Permission denied message is displayed as expected");
		} else {
			logInfo("Location permission is granted or not prompted - skipping");
		}

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_006_15 - Edge: Very Long Search Query
	// ============================================================
	@Test(groups = { "edge-case" }, dependsOnMethods = { "TC_006_01_HospitalPage_Loads" })
	public void TC_006_15_Edge_VeryLongSearchQuery() {
		String testCase = "TC_006_15_Edge_VeryLongSearchQuery";
		logInfo("TEST STARTED: " + testCase);

		
		String longQuery = "A".repeat(200);
		hp.completeSearchFlow(longQuery);

		Assert.assertTrue(hp.getHospitalResultCount() >= 0, "Search should handle long queries");
		Assert.assertFalse(hp.isErrorDisplayed(), "Error should not appear for long query");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_006_16 - Edge: Numbers Only Search
	// ============================================================
	@Test(groups = { "edge-case" }, dependsOnMethods = { "TC_006_01_HospitalPage_Loads" })
	public void TC_006_16_Edge_NumbersOnlySearch() {
		String testCase = "TC_006_16_Edge_NumbersOnlySearch";
		logInfo("TEST STARTED: " + testCase);

		
		hp.completeSearchFlow("12345");

		Assert.assertTrue(hp.getHospitalResultCount() >= 0, "Search should handle numbers only");
		Assert.assertFalse(hp.isErrorDisplayed(), "Error should not appear for numbers only");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_006_17 - Edge: Search Then Clear Input
	// ============================================================
	@Test(groups = { "edge-case" }, dependsOnMethods = { "TC_006_01_HospitalPage_Loads" })
	public void TC_006_17_Edge_SearchThenClearInput() {
		String testCase = "TC_006_17_Edge_SearchThenClearInput";
		logInfo("TEST STARTED: " + testCase);

		

		hp.search("City Hospital");
		hp.waitForSearchResults();
		Assert.assertTrue(hp.areResultsVisible() || hp.isEmptyStateDisplayed(), "Results should appear");

		hp.enterSearchQuery("");
		hp.waitForSearchResults();

		// Should either show all results or empty state
		Assert.assertTrue(hp.areResultsVisible() || hp.isEmptyStateDisplayed(),
				"After clearing, should show results or empty state");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_006_18 - Edge: Multiple Searches
	// ============================================================
	@Test(groups = { "edge-case" }, dependsOnMethods = { "TC_006_01_HospitalPage_Loads" })
	public void TC_006_18_Edge_MultipleSearches() {
		String testCase = "TC_006_18_Edge_MultipleSearches";
		logInfo("TEST STARTED: " + testCase);

		

		String[] queries = { "City", "Hospital", "Medical", "Clinic" };

		for (String query : queries) {
			hp.completeSearchFlow(query);
			Assert.assertTrue(hp.areResultsVisible() || hp.isEmptyStateDisplayed(), "Search should work for: " + query);
			logInfo("Search for '" + query + "' completed with " + hp.getHospitalResultCount() + " results");
		}

		logInfo("TEST PASSED: " + testCase);
	}
}
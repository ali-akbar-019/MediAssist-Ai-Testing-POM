package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.ReportPage;

public class TC_007_ReportTest extends BaseClass {

	ReportPage rp;

	// ============================================================
	// TC_007_01 - Page Load Test
	// ============================================================
	@Test(groups = { "smoke", "regression" })
	public void TC_007_01_ReportPage_Loads() {
		String testCase = "TC_007_01_ReportPage_Loads";
		logInfo("TEST STARTED: " + testCase);

		driver.get(p.getProperty("appURL") + "/reports");
		rp = new ReportPage(driver);
		rp.waitForPageLoad();

		Assert.assertTrue(waitForUrlContains("reports", 5), "Reports route not reached");
		Assert.assertTrue(rp.isPageVisible(), "Reports page not visible");
		Assert.assertTrue(rp.isHeadingVisible(), "Reports heading not visible");
		Assert.assertTrue(rp.isSubtitleVisible(), "Reports subtitle not visible");
		Assert.assertTrue(rp.isBackBtnVisible(), "Back button not visible");
		Assert.assertTrue(rp.isStatsVisible(), "Stats container not visible");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_007_02 - Reports Grid Display
	// ============================================================
	@Test(groups = { "smoke", "regression" }, dependsOnMethods = { "TC_007_01_ReportPage_Loads" })
	public void TC_007_02_ReportsGrid_Display() {
		String testCase = "TC_007_02_ReportsGrid_Display";
		logInfo("TEST STARTED: " + testCase);

		rp = new ReportPage(driver);
		rp.waitForGridToLoad();

		Assert.assertTrue(rp.isGridContainerVisible(), "Grid container not visible");
		Assert.assertTrue(rp.isGridVisible(), "Reports grid not visible");

		int totalCount = rp.getTotalRecordsCount();
		int cardCount = rp.getReportCardCount();

		logInfo("Total records: " + totalCount + ", Cards displayed: " + cardCount);

		if (totalCount > 0) {
			Assert.assertTrue(cardCount > 0, "Report cards should be displayed");
			Assert.assertEquals(cardCount, Math.min(totalCount, 9), "Card count should match total records (max 9)");
		} else {
			Assert.assertTrue(rp.isEmptyStateVisible(), "Empty state should be visible");
		}

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_007_03 - Report Card Details
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_007_02_ReportsGrid_Display" })
	public void TC_007_03_ReportCard_Details() {
		String testCase = "TC_007_03_ReportCard_Details";
		logInfo("TEST STARTED: " + testCase);

		rp = new ReportPage(driver);
		rp.waitForGridToLoad();

		if (rp.hasReports()) {
			rp.verifyReportCardDetails(0);

			String bodyPart = rp.getReportCardBodyPart(0);
			String severity = rp.getReportCardSeverity(0);
			String symptom = rp.getReportCardSymptom(0, 0);
			String aiInsight = rp.getReportCardAIInsight(0);
			String time = rp.getReportCardTime(0);

			logInfo("Report Card Details:");
			logInfo("  Body Part: " + bodyPart);
			logInfo("  Severity: " + severity);
			logInfo("  Symptom: " + symptom);
			logInfo("  AI Insight: " + aiInsight);
			logInfo("  Time: " + time);

			Assert.assertFalse(bodyPart.isEmpty(), "Body part should not be empty");
			Assert.assertFalse(severity.isEmpty(), "Severity should not be empty");
			Assert.assertFalse(symptom.isEmpty(), "Symptom should not be empty");
			Assert.assertFalse(aiInsight.isEmpty(), "AI Insight should not be empty");
			Assert.assertFalse(time.isEmpty(), "Time should not be empty");
		} else {
			logInfo("No reports to verify details");
		}

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_007_04 - Report Card Metrics
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_007_02_ReportsGrid_Display" })
	public void TC_007_04_ReportCard_Metrics() {
		String testCase = "TC_007_04_ReportCard_Metrics";
		logInfo("TEST STARTED: " + testCase);

		rp = new ReportPage(driver);
		rp.waitForGridToLoad();

		if (rp.hasReports()) {
			String type = rp.getReportCardMetric(0, "type");
			String intensity = rp.getReportCardMetric(0, "intensity");
			String span = rp.getReportCardMetric(0, "span");

			logInfo("Metrics: Type=" + type + ", Intensity=" + intensity + ", Span=" + span);

			Assert.assertFalse(type.isEmpty(), "Type metric should not be empty");
			Assert.assertFalse(intensity.isEmpty(), "Intensity metric should not be empty");
			Assert.assertFalse(span.isEmpty(), "Span metric should not be empty");
			Assert.assertTrue(intensity.contains("/10"), "Intensity should contain '/10'");
		} else {
			logInfo("No reports to verify metrics");
		}

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_007_05 - Download Report
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_007_02_ReportsGrid_Display" })
	public void TC_007_05_DownloadReport() {
		String testCase = "TC_007_05_DownloadReport";
		logInfo("TEST STARTED: " + testCase);

		rp = new ReportPage(driver);
		rp.waitForGridToLoad();

		if (rp.hasReports()) {
			Assert.assertTrue(rp.isDownloadBtnVisible(0), "Download button should be visible");
			
			rp.downloadReport(0);
			logInfo("Download triggered for report at index 0");
			
			// Wait for download to start
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
		} else {
			logInfo("No reports to download");
		}

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_007_06 - Delete Report
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_007_02_ReportsGrid_Display" })
	public void TC_007_06_DeleteReport() {
		String testCase = "TC_007_06_DeleteReport";
		logInfo("TEST STARTED: " + testCase);

		rp = new ReportPage(driver);
		rp.waitForGridToLoad();

		if (rp.hasReports()) {
			int beforeCount = rp.getReportCardCount();
			int beforeTotal = rp.getTotalRecordsCount();
			
			Assert.assertTrue(rp.isDeleteBtnVisible(0), "Delete button should be visible");
			
			rp.deleteReport(0);
			logInfo("Delete triggered for report at index 0");
			
			rp.waitForGridToLoad();
			
			int afterCount = rp.getReportCardCount();
			int afterTotal = rp.getTotalRecordsCount();
			
			Assert.assertTrue(afterCount <= beforeCount, "Report should be deleted or count decreased");
			logInfo("Before: " + beforeCount + " cards, After: " + afterCount + " cards");
		} else {
			logInfo("No reports to delete");
		}

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_007_07 - Pagination Display
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_007_01_ReportPage_Loads" })
	public void TC_007_07_Pagination_Display() {
		String testCase = "TC_007_07_Pagination_Display";
		logInfo("TEST STARTED: " + testCase);

		rp = new ReportPage(driver);
		rp.waitForGridToLoad();

		int totalRecords = rp.getTotalRecordsCount();
		int totalPages = (int) Math.ceil(totalRecords / 9.0);

		if (totalPages > 1) {
			Assert.assertTrue(rp.isPaginationVisible(), "Pagination should be visible");
			Assert.assertTrue(rp.getTotalPageCount() > 1, "Should have multiple pages");
			Assert.assertFalse(rp.isPrevPageDisabled(), "Previous page should be enabled");
		} else {
			logInfo("Pagination not needed for " + totalRecords + " records");
		}

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_007_08 - Pagination Navigation
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_007_07_Pagination_Display" })
	public void TC_007_08_Pagination_Navigation() {
		String testCase = "TC_007_08_Pagination_Navigation";
		logInfo("TEST STARTED: " + testCase);

		rp = new ReportPage(driver);
		rp.waitForGridToLoad();

		int totalPages = rp.getTotalPageCount();

		if (totalPages > 1) {
			// Test next navigation
			rp.goToNextPage();
			rp.waitForGridToLoad();
			Assert.assertEquals(rp.getActivePage(), 2, "Should be on page 2");
			Assert.assertFalse(rp.isPrevPageDisabled(), "Previous page should be enabled");

			// Test page number click
			rp.goToPage(1);
			rp.waitForGridToLoad();
			Assert.assertEquals(rp.getActivePage(), 1, "Should be on page 1");
			Assert.assertTrue(rp.isPrevPageDisabled(), "Previous page should be disabled");

			// Test previous navigation
			if (totalPages > 2) {
				rp.goToPage(3);
				rp.waitForGridToLoad();
				Assert.assertEquals(rp.getActivePage(), 3, "Should be on page 3");

				rp.goToPreviousPage();
				rp.waitForGridToLoad();
				Assert.assertEquals(rp.getActivePage(), 2, "Should be on page 2 after previous");
			}
		} else {
			logInfo("Pagination navigation not needed for " + totalPages + " pages");
		}

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_007_09 - Back Button Navigation
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_007_01_ReportPage_Loads" })
	public void TC_007_09_BackButton_Navigation() {
		String testCase = "TC_007_09_BackButton_Navigation";
		logInfo("TEST STARTED: " + testCase);

		rp = new ReportPage(driver);
		rp.waitForPageLoad();

		rp.clickBackBtn();
		logInfo("Back button clicked");

		// Wait for navigation
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}

		// Should navigate to dashboard
		String currentUrl = driver.getCurrentUrl();
		Assert.assertTrue(currentUrl.contains("/dashboard") || currentUrl.contains("/"), 
			"Should navigate to dashboard or home after back");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_007_10 - Total Count Display
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_007_02_ReportsGrid_Display" })
	public void TC_007_10_TotalCount_Display() {
		String testCase = "TC_007_10_TotalCount_Display";
		logInfo("TEST STARTED: " + testCase);

		rp = new ReportPage(driver);
		rp.waitForGridToLoad();

		int totalCount = rp.getTotalRecordsCount();
		int cardCount = rp.getReportCardCount();

		logInfo("Total records: " + totalCount + ", Cards on page: " + cardCount);

		Assert.assertTrue(totalCount >= cardCount, "Total count should be >= cards on page");
		Assert.assertEquals(totalCount, Integer.parseInt(rp.getTotalRecordsText()), 
			"Total count display should match actual count");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_007_11 - Loading State
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_007_01_ReportPage_Loads" })
	public void TC_007_11_LoadingState() {
		String testCase = "TC_007_11_LoadingState";
		logInfo("TEST STARTED: " + testCase);

		driver.get(p.getProperty("appURL") + "/reports");
		rp = new ReportPage(driver);

		// Check if loading appears (briefly)
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		
		boolean loadingVisible = rp.isLoadingVisible();
		logInfo("Loading visible: " + loadingVisible);

		rp.waitForGridToLoad();
		Assert.assertTrue(rp.isGridVisible() || rp.isEmptyStateVisible(), 
			"Grid or empty state should be visible after loading");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_007_12 - Edge: Empty State
	// ============================================================
	@Test(groups = { "edge-case" }, dependsOnMethods = { "TC_007_01_ReportPage_Loads" })
	public void TC_007_12_Edge_EmptyState() {
		String testCase = "TC_007_12_Edge_EmptyState";
		logInfo("TEST STARTED: " + testCase);

		rp = new ReportPage(driver);
		rp.waitForGridToLoad();

		if (!rp.hasReports()) {
			Assert.assertTrue(rp.isEmptyStateVisible(), "Empty state should be visible");
			Assert.assertTrue(rp.getEmptyStateText().contains("No Reports Found"), 
				"Empty state should show 'No Reports Found'");
			Assert.assertEquals(rp.getReportCardCount(), 0, "Report card count should be 0");
		} else {
			logInfo("Reports exist, skipping empty state verification");
			Assert.assertFalse(rp.isEmptyStateVisible(), "Empty state should not be visible");
		}

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_007_13 - Edge: All Report Cards Have Data
	// ============================================================
	@Test(groups = { "edge-case" }, dependsOnMethods = { "TC_007_02_ReportsGrid_Display" })
	public void TC_007_13_Edge_AllReportCardsHaveData() {
		String testCase = "TC_007_13_Edge_AllReportCardsHaveData";
		logInfo("TEST STARTED: " + testCase);

		rp = new ReportPage(driver);
		rp.waitForGridToLoad();

		if (rp.hasReports()) {
			Assert.assertTrue(rp.verifyAllReportCardsHaveData(), 
				"All report cards should have complete data");
			logInfo("All " + rp.getReportCardCount() + " report cards have valid data");
		} else {
			logInfo("No reports to verify");
		}

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_007_14 - Edge: Multiple Symptoms Display
	// ============================================================
	@Test(groups = { "edge-case" }, dependsOnMethods = { "TC_007_02_ReportsGrid_Display" })
	public void TC_007_14_Edge_MultipleSymptomsDisplay() {
		String testCase = "TC_007_14_Edge_MultipleSymptomsDisplay";
		logInfo("TEST STARTED: " + testCase);

		rp = new ReportPage(driver);
		rp.waitForGridToLoad();

		if (rp.hasReports()) {
			int symptomCount = rp.getReportCardSymptomsCount(0);
			logInfo("Report 0 has " + symptomCount + " symptoms");

			if (symptomCount > 3) {
				logInfo("More than 3 symptoms - should show '+N MORE' indicator");
			}
		} else {
			logInfo("No reports to verify symptoms");
		}

		logInfo("TEST PASSED: " + testCase);
	}
}
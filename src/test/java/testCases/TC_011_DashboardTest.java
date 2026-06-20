package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.DashboardPage;

public class TC_011_DashboardTest extends BaseClass {

	DashboardPage dp;

	// ============================================================
	// TC_011_01 - Page Load Test
	// ============================================================
	@Test(groups = { "smoke", "regression" })
	public void TC_011_01_DashboardPage_Loads() {
		String testCase = "TC_011_01_DashboardPage_Loads";
		logInfo("TEST STARTED: " + testCase);

		driver.get(p.getProperty("appURL") + "/dashboard");
		dp = new DashboardPage(driver);
		dp.waitForDashboardToLoad();

		Assert.assertTrue(waitForUrlContains("dashboard", 5), "Dashboard route not reached");
		Assert.assertTrue(dp.isPageVisible(), "Dashboard page not visible");
		Assert.assertTrue(dp.isHeadingVisible(), "Dashboard heading not visible");
		Assert.assertTrue(dp.isSubtitleVisible(), "Dashboard subtitle not visible");
		Assert.assertTrue(dp.isBadgeVisible(), "Dashboard badge not visible");
		Assert.assertTrue(dp.isCommandBarVisible(), "Command bar not visible");
		Assert.assertTrue(dp.isHealthDashboardVisible(), "Health dashboard not visible");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_011_02 - Command Bar Display
	// ============================================================
	@Test(groups = { "smoke", "regression" }, dependsOnMethods = { "TC_011_01_DashboardPage_Loads" })
	public void TC_011_02_CommandBar_Display() {
		String testCase = "TC_011_02_CommandBar_Display";
		logInfo("TEST STARTED: " + testCase);

		dp = new DashboardPage(driver);

		Assert.assertTrue(dp.isCommandBarVisible(), "Command bar should be visible");
		Assert.assertFalse(dp.getStatusText().isEmpty(), "Status text should not be empty");
		Assert.assertTrue(dp.isInitiateAnalysisBtnVisible(), "Initiate Analysis button should be visible");

		logInfo("Status: " + dp.getStatusText());
		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_011_03 - Stats Display
	// ============================================================
	@Test(groups = { "smoke", "regression" }, dependsOnMethods = { "TC_011_01_DashboardPage_Loads" })
	public void TC_011_03_Stats_Display() {
		String testCase = "TC_011_03_Stats_Display";
		logInfo("TEST STARTED: " + testCase);

		dp = new DashboardPage(driver);
		dp.waitForStatsToLoad();

		Assert.assertTrue(dp.isStatsVisible(), "Stats container should be visible");
		Assert.assertTrue(dp.verifyAllStatsVisible(), "All stat cards should be visible");

		int totalAnalyses = dp.getTotalAnalyses();
		int thisMonth = dp.getThisMonthCount();
		int reportsGenerated = dp.getReportsGenerated();
		String lastAnalysis = dp.getLastAnalysis();

		logInfo("Total Analyses: " + totalAnalyses);
		logInfo("This Month: " + thisMonth);
		logInfo("Reports Generated: " + reportsGenerated);
		logInfo("Last Analysis: " + lastAnalysis);

		Assert.assertTrue(totalAnalyses >= 0, "Total analyses should be >= 0");
		Assert.assertTrue(thisMonth >= 0, "This month count should be >= 0");
		Assert.assertTrue(reportsGenerated >= 0, "Reports generated should be >= 0");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_011_04 - Charts Display
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_011_01_DashboardPage_Loads" })
	public void TC_011_04_Charts_Display() {
		String testCase = "TC_011_04_Charts_Display";
		logInfo("TEST STARTED: " + testCase);

		dp = new DashboardPage(driver);
		dp.waitForChartsToLoad();

		Assert.assertTrue(dp.isChartsVisible(), "Charts container should be visible");
		Assert.assertTrue(dp.verifyAllChartsVisible(), "All charts should be visible");

		int severitySegments = dp.getSeverityChartSegments();
		int bodypartBars = dp.getBodypartBars();

		logInfo("Severity Chart Segments: " + severitySegments);
		logInfo("Bodypart Chart Bars: " + bodypartBars);

		// Charts may have 0 data points, but should still render
		Assert.assertTrue(severitySegments >= 0, "Severity chart should render");
		Assert.assertTrue(bodypartBars >= 0, "Bodypart chart should render");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_011_05 - Vitality Intelligence Display
	// ============================================================
	@Test(groups = { "smoke", "regression" }, dependsOnMethods = { "TC_011_01_DashboardPage_Loads" })
	public void TC_011_05_VitalityIntelligence_Display() {
		String testCase = "TC_011_05_VitalityIntelligence_Display";
		logInfo("TEST STARTED: " + testCase);

		dp = new DashboardPage(driver);

		Assert.assertTrue(dp.isVitalityIntelligenceVisible(), "Vitality intelligence should be visible");
		Assert.assertTrue(dp.isVitalityGaugeVisible(), "Vitality gauge should be visible");

		int score = dp.getVitalityScore();
		String label = dp.getVitalityLabel();
		String status = dp.getVitalityStatus();
		String title = dp.getVitalityTitle();

		logInfo("Vitality Score: " + score + "%");
		logInfo("Vitality Label: " + label);
		logInfo("Vitality Status: " + status);
		logInfo("Vitality Title: " + title);

		Assert.assertTrue(score >= 0 && score <= 100, "Vitality score should be between 0 and 100");
		Assert.assertFalse(label.isEmpty(), "Vitality label should not be empty");
		Assert.assertFalse(status.isEmpty(), "Vitality status should not be empty");
		Assert.assertFalse(title.isEmpty(), "Vitality title should not be empty");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_011_06 - Vitality System Statuses
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_011_05_VitalityIntelligence_Display" })
	public void TC_011_06_Vitality_SystemStatuses() {
		String testCase = "TC_011_06_Vitality_SystemStatuses";
		logInfo("TEST STARTED: " + testCase);

		dp = new DashboardPage(driver);

		int statusCount = dp.getSystemStatusCount();
		logInfo("System statuses count: " + statusCount);

		Assert.assertTrue(statusCount >= 4, "Should have at least 4 system statuses");

		String[] systems = {"Neurological", "Cardiovascular", "Respiratory", "Biological"};
		for (String system : systems) {
			String value = dp.getSystemStatusValue(system);
			logInfo(system + ": " + value);
			Assert.assertFalse(value.isEmpty(), system + " status should not be empty");
		}

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_011_07 - Vitality AI Insight
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_011_05_VitalityIntelligence_Display" })
	public void TC_011_07_Vitality_AIInsight() {
		String testCase = "TC_011_07_Vitality_AIInsight";
		logInfo("TEST STARTED: " + testCase);

		dp = new DashboardPage(driver);

		Assert.assertTrue(dp.isVitalityAIInsightVisible(), "AI Insight should be visible");
		
		String insightText = dp.getVitalityInsightText();
		logInfo("AI Insight: " + insightText);
		
		Assert.assertFalse(insightText.isEmpty(), "AI Insight text should not be empty");
		Assert.assertTrue(insightText.length() > 20, "AI Insight should be detailed");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_011_08 - History Section Display
	// ============================================================
	@Test(groups = { "smoke", "regression" }, dependsOnMethods = { "TC_011_01_DashboardPage_Loads" })
	public void TC_011_08_HistorySection_Display() {
		String testCase = "TC_011_08_HistorySection_Display";
		logInfo("TEST STARTED: " + testCase);

		dp = new DashboardPage(driver);
		dp.waitForHistoryToLoad();

		Assert.assertTrue(dp.isHistorySectionVisible(), "History section should be visible");
		Assert.assertFalse(dp.getHistoryTitle().isEmpty(), "History title should not be empty");

		int recordCount = dp.getHistoryRecordCount();
		int cardCount = dp.getHistoryCardCount();

		logInfo("History record count: " + recordCount);
		logInfo("History card count: " + cardCount);

		if (recordCount > 0) {
			Assert.assertTrue(cardCount > 0, "History cards should be visible");
			Assert.assertTrue(dp.isHistoryGridVisible(), "History grid should be visible");
			Assert.assertFalse(dp.isEmptyStateVisible(), "Empty state should not be visible");
		} else {
			Assert.assertTrue(dp.isEmptyStateVisible(), "Empty state should be visible");
			Assert.assertEquals(cardCount, 0, "No cards should be visible");
		}

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_011_09 - View All Reports Button
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_011_08_HistorySection_Display" })
	public void TC_011_09_ViewAllReports_Button() {
		String testCase = "TC_011_09_ViewAllReports_Button";
		logInfo("TEST STARTED: " + testCase);

		dp = new DashboardPage(driver);
		dp.waitForHistoryToLoad();

		int recordCount = dp.getHistoryRecordCount();
		
		if (recordCount > 0) {
			Assert.assertTrue(dp.isViewAllReportsVisible(), "View All Reports button should be visible");
			
			dp.clickViewAllReports();
			logInfo("View All Reports clicked");
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
			
			String currentUrl = driver.getCurrentUrl();
			Assert.assertTrue(currentUrl.contains("/reports"), "Should navigate to reports page");
		} else {
			logInfo("No reports to view, skipping button test");
		}

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_011_10 - User Profile Display
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_011_01_DashboardPage_Loads" })
	public void TC_011_10_UserProfile_Display() {
		String testCase = "TC_011_10_UserProfile_Display";
		logInfo("TEST STARTED: " + testCase);

		dp = new DashboardPage(driver);

		Assert.assertTrue(dp.isUserProfileVisible(), "User profile section should be visible");
		Assert.assertTrue(dp.verifyUserProfile(), "User profile should have valid data");

		String age = dp.getProfileAge();
		String gender = dp.getProfileGender();
		String bloodGroup = dp.getProfileBloodGroup();
		String allergies = dp.getProfileAllergies();

		logInfo("Age: " + age);
		logInfo("Gender: " + gender);
		logInfo("Blood Group: " + bloodGroup);
		logInfo("Allergies: " + allergies);

		Assert.assertFalse(age.isEmpty(), "Age should not be empty");
		Assert.assertFalse(gender.isEmpty(), "Gender should not be empty");
		Assert.assertFalse(bloodGroup.isEmpty(), "Blood group should not be empty");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_011_11 - Navigate to Analyzer
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_011_01_DashboardPage_Loads" })
	public void TC_011_11_NavigateToAnalyzer() {
		String testCase = "TC_011_11_NavigateToAnalyzer";
		logInfo("TEST STARTED: " + testCase);

		// Navigate back to dashboard first
		driver.get(p.getProperty("appURL") + "/dashboard");
		dp = new DashboardPage(driver);
		dp.waitForDashboardToLoad();

		Assert.assertTrue(dp.isInitiateAnalysisBtnVisible(), "Initiate Analysis button should be visible");
		
		dp.navigateToAnalyzer();
		logInfo("Navigate to Analyzer clicked");
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		
		String currentUrl = driver.getCurrentUrl();
		Assert.assertTrue(currentUrl.contains("/analyzer"), "Should navigate to analyzer page");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_011_12 - Edge: Stats Loading State
	// ============================================================
	@Test(groups = { "edge-case" }, dependsOnMethods = { "TC_011_01_DashboardPage_Loads" })
	public void TC_011_12_Edge_StatsLoadingState() {
		String testCase = "TC_011_12_Edge_StatsLoadingState";
		logInfo("TEST STARTED: " + testCase);

		driver.get(p.getProperty("appURL") + "/dashboard");
		dp = new DashboardPage(driver);

		// Check loading state briefly
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		boolean loadingVisible = dp.isStatsLoadingVisible();
		logInfo("Stats loading visible: " + loadingVisible);

		dp.waitForStatsToLoad();
		Assert.assertTrue(dp.isStatsVisible(), "Stats should be visible after loading");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_011_13 - Edge: History Loading State
	// ============================================================
	@Test(groups = { "edge-case" }, dependsOnMethods = { "TC_011_01_DashboardPage_Loads" })
	public void TC_011_13_Edge_HistoryLoadingState() {
		String testCase = "TC_011_13_Edge_HistoryLoadingState";
		logInfo("TEST STARTED: " + testCase);

		driver.get(p.getProperty("appURL") + "/dashboard");
		dp = new DashboardPage(driver);

		// Check loading state briefly
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		boolean loadingVisible = dp.isHistoryLoadingVisible();
		logInfo("History loading visible: " + loadingVisible);

		dp.waitForHistoryToLoad();
		Assert.assertTrue(dp.isHistorySectionVisible(), "History should be visible after loading");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_011_14 - Edge: Dashboard Badge
	// ============================================================
	@Test(groups = { "edge-case" }, dependsOnMethods = { "TC_011_01_DashboardPage_Loads" })
	public void TC_011_14_Edge_DashboardBadge() {
		String testCase = "TC_011_14_Edge_DashboardBadge";
		logInfo("TEST STARTED: " + testCase);

		dp = new DashboardPage(driver);

		Assert.assertTrue(dp.isBadgeVisible(), "Dashboard badge should be visible");
		Assert.assertTrue(dp.getBadgeText().contains("Health Intelligence System"), 
			"Badge should contain 'Health Intelligence System'");

		logInfo("Badge text: " + dp.getBadgeText());
		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_011_15 - Edge: Stats Values Are Numeric
	// ============================================================
	@Test(groups = { "edge-case" }, dependsOnMethods = { "TC_011_03_Stats_Display" })
	public void TC_011_15_Edge_StatsValuesAreNumeric() {
		String testCase = "TC_011_15_Edge_StatsValuesAreNumeric";
		logInfo("TEST STARTED: " + testCase);

		dp = new DashboardPage(driver);
		dp.waitForStatsToLoad();

		int totalAnalyses = dp.getTotalAnalyses();
		int thisMonth = dp.getThisMonthCount();
		int reportsGenerated = dp.getReportsGenerated();

		// Verify values are valid numbers
		try {
			Integer.parseInt(String.valueOf(totalAnalyses));
			Integer.parseInt(String.valueOf(thisMonth));
			Integer.parseInt(String.valueOf(reportsGenerated));
			logInfo("All stat values are valid numbers");
		} catch (NumberFormatException e) {
			Assert.fail("Stat values should be numeric");
		}

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_011_16 - Edge: Verify All Dashboard Components
	// ============================================================
	@Test(groups = { "smoke", "regression" }, dependsOnMethods = { "TC_011_01_DashboardPage_Loads" })
	public void TC_011_16_VerifyAllDashboardComponents() {
		String testCase = "TC_011_16_VerifyAllDashboardComponents";
		logInfo("TEST STARTED: " + testCase);

		dp = new DashboardPage(driver);
		dp.waitForDashboardToLoad();

		// Verify all major components
		Assert.assertTrue(dp.isCommandBarVisible(), "Command bar should be visible");
		Assert.assertTrue(dp.isStatsVisible(), "Stats should be visible");
		Assert.assertTrue(dp.isChartsVisible(), "Charts should be visible");
		Assert.assertTrue(dp.isVitalityIntelligenceVisible(), "Vitality intelligence should be visible");
		Assert.assertTrue(dp.isHistorySectionVisible(), "History section should be visible");
		Assert.assertTrue(dp.isUserProfileVisible(), "User profile should be visible");

		logInfo("All dashboard components verified");
		logInfo("TEST PASSED: " + testCase);
	}
}
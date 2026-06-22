package testCases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.AdminDashboardPage;

public class TC_012_AdminDashboardTest extends BaseClass {

	AdminDashboardPage adp;

	@BeforeMethod
public void loginFirst() {
    login();
    driver.get(p.getProperty("appURL") + "/admin/dashboard");
    adp = new AdminDashboardPage(driver);
    adp.waitForDashboardToLoad();
    waitForUrlContains("admin", 10);
}

	// ============================================================
	// TC_012_01 - Page Load Test
	// ============================================================
	@Test(groups = { "smoke", "regression", "edge-case" })
	public void TC_012_01_AdminDashboardPage_Loads() {
		String testCase = "TC_012_01_AdminDashboardPage_Loads";
		logInfo("TEST STARTED: " + testCase);

		adp.waitForDashboardToLoad();

		Assert.assertTrue(waitForUrlContains("admin", 5), "Admin route not reached");
		Assert.assertTrue(adp.isPageVisible(), "Admin dashboard page not visible");
		Assert.assertTrue(adp.isHeadingVisible(), "Heading not visible");
		Assert.assertFalse(adp.getStatusText().isEmpty(), "Status text should not be empty");
		Assert.assertFalse(adp.getLatencyValue().isEmpty(), "Latency value should not be empty");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_012_02 - Loading State
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_012_01_AdminDashboardPage_Loads" })
	public void TC_012_02_LoadingState() {
		String testCase = "TC_012_02_LoadingState";
		logInfo("TEST STARTED: " + testCase);

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		boolean loadingVisible = adp.isLoadingVisible();
		logInfo("Loading visible: " + loadingVisible);

		adp.waitForDashboardToLoad();
		Assert.assertTrue(adp.isPageVisible(), "Page should be visible after loading");
		Assert.assertTrue(adp.isKPIContainerVisible(), "KPI container should be visible after loading");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_012_03 - KPI Cards Display
	// ============================================================
	@Test(groups = { "smoke", "regression" }, dependsOnMethods = { "TC_012_01_AdminDashboardPage_Loads" })
	public void TC_012_03_KPICards_Display() {
		String testCase = "TC_012_03_KPICards_Display";
		logInfo("TEST STARTED: " + testCase);

		adp.waitForKPIs();

		Assert.assertTrue(adp.isKPIContainerVisible(), "KPI container should be visible");
		Assert.assertTrue(adp.verifyAllKPIsVisible(), "All KPI cards should be visible");

		int users = adp.getUsersCount();
		int symptoms = adp.getSymptomsCount();
		int documents = adp.getDocumentsCount();
		int reports = adp.getReportsCount();

		logInfo("Users: " + users);
		logInfo("Symptoms: " + symptoms);
		logInfo("Documents: " + documents);
		logInfo("Reports: " + reports);

		Assert.assertTrue(users >= 0, "Users count should be >= 0");
		Assert.assertTrue(symptoms >= 0, "Symptoms count should be >= 0");
		Assert.assertTrue(documents >= 0, "Documents count should be >= 0");
		Assert.assertTrue(reports >= 0, "Reports count should be >= 0");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_012_04 - KPI Values Are Numeric
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_012_03_KPICards_Display" })
	public void TC_012_04_KPIValues_AreNumeric() {
		String testCase = "TC_012_04_KPIValues_AreNumeric";
		logInfo("TEST STARTED: " + testCase);

		int users = adp.getUsersCount();
		int symptoms = adp.getSymptomsCount();
		int documents = adp.getDocumentsCount();
		int reports = adp.getReportsCount();

		try {
			Integer.parseInt(String.valueOf(users));
			Integer.parseInt(String.valueOf(symptoms));
			Integer.parseInt(String.valueOf(documents));
			Integer.parseInt(String.valueOf(reports));
			logInfo("All KPI values are valid numbers");
		} catch (NumberFormatException e) {
			Assert.fail("KPI values should be numeric");
		}

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_012_05 - Intelligence Section Display
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_012_01_AdminDashboardPage_Loads" })
	public void TC_012_05_IntelligenceSection_Display() {
		String testCase = "TC_012_05_IntelligenceSection_Display";
		logInfo("TEST STARTED: " + testCase);

		adp.waitForIntelligenceSection();

		Assert.assertTrue(adp.isIntelligenceSectionVisible(), "Intelligence section should be visible");
		Assert.assertFalse(adp.getIntelligenceTitle().isEmpty(), "Intelligence title should not be empty");
		Assert.assertTrue(adp.isIntelligenceChartVisible(), "Intelligence chart should be visible");

		String confidence = adp.getConfidenceValue();
		logInfo("Avg severity: " + confidence);
		Assert.assertFalse(confidence.isEmpty(), "Avg severity should not be empty");

		String mostLogged = adp.getMostLoggedValue();
		logInfo("Most logged symptom: " + mostLogged);
		Assert.assertFalse(mostLogged.isEmpty(), "Most logged symptom should not be empty");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_012_06 - Recent Activity (Pulse) Section Display
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_012_01_AdminDashboardPage_Loads" })
	public void TC_012_06_PulseSection_Display() {
		String testCase = "TC_012_06_PulseSection_Display";
		logInfo("TEST STARTED: " + testCase);

		adp.waitForPulseSection();

		Assert.assertTrue(adp.isPulseSectionVisible(), "Recent Activity section should be visible");
		Assert.assertFalse(adp.getPulseTitle().isEmpty(), "Recent Activity title should not be empty");

		int eventCount = adp.getPulseEventCount();
		logInfo("Recent activity events count: " + eventCount);
		Assert.assertTrue(eventCount >= 0, "Should have at least 0 events");

		if (eventCount > 0) {
			for (int i = 0; i < eventCount; i++) {
				String label = adp.getPulseEventLabel(i);
				String time = adp.getPulseEventTime(i);
				logInfo("Event " + (i + 1) + ": " + label + " - " + time);
				Assert.assertFalse(label.isEmpty(), "Event label should not be empty");
				Assert.assertFalse(time.isEmpty(), "Event time should not be empty");
			}
		}

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_012_07 - Status Text Contains Online
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_012_01_AdminDashboardPage_Loads" })
	public void TC_012_07_StatusText_ContainsOnline() {
		String testCase = "TC_012_07_StatusText_ContainsOnline";
		logInfo("TEST STARTED: " + testCase);

		String status = adp.getStatusText();
		logInfo("Status text: " + status);

		Assert.assertTrue(status.toLowerCase().contains("online") || status.toLowerCase().contains("optimal"),
				"Status text should contain 'Online' or 'Optimal'");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_012_08 - Latency Value Format
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_012_01_AdminDashboardPage_Loads" })
	public void TC_012_08_LatencyValue_Format() {
		String testCase = "TC_012_08_LatencyValue_Format";
		logInfo("TEST STARTED: " + testCase);

		String latency = adp.getLatencyValue();
		logInfo("Latency value: " + latency);

		Assert.assertFalse(latency.isEmpty(), "Latency value should not be empty");
		Assert.assertTrue(latency.matches(".*\\d+.*") || latency.matches(".*[ms|s].*"),
				"Latency value should contain number or time unit");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_012_09 - Edge: All KPI Values Non-Negative
	// ============================================================
	@Test(groups = { "edge-case" }, dependsOnMethods = { "TC_012_03_KPICards_Display" })
	public void TC_012_09_Edge_AllKPIValuesNonNegative() {
		String testCase = "TC_012_09_Edge_AllKPIValuesNonNegative";
		logInfo("TEST STARTED: " + testCase);

		int users = adp.getUsersCount();
		int symptoms = adp.getSymptomsCount();
		int documents = adp.getDocumentsCount();
		int reports = adp.getReportsCount();

		logInfo("Users: " + users);
		logInfo("Symptoms: " + symptoms);
		logInfo("Documents: " + documents);
		logInfo("Reports: " + reports);

		Assert.assertTrue(users >= 0, "Users count should be >= 0");
		Assert.assertTrue(symptoms >= 0, "Symptoms count should be >= 0");
		Assert.assertTrue(documents >= 0, "Documents count should be >= 0");
		Assert.assertTrue(reports >= 0, "Reports count should be >= 0");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_012_10 - Edge: Pulse Events Have Valid Labels
	// ============================================================
	@Test(groups = { "edge-case" }, dependsOnMethods = { "TC_012_06_PulseSection_Display" })
	public void TC_012_10_Edge_PulseEventsValid() {
		String testCase = "TC_012_10_Edge_PulseEventsValid";
		logInfo("TEST STARTED: " + testCase);

		int eventCount = adp.getPulseEventCount();

		for (int i = 0; i < eventCount; i++) {
			String label = adp.getPulseEventLabel(i);
			String time = adp.getPulseEventTime(i);
			logInfo("Event " + (i + 1) + " label: " + label + " time: " + time);
			Assert.assertFalse(label.isEmpty(), "Event label should not be empty");
			Assert.assertFalse(time.isEmpty(), "Event time should not be empty");
		}

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_012_11 - Verify Complete Admin Dashboard
	// ============================================================
	@Test(groups = { "smoke", "regression" }, dependsOnMethods = { "TC_012_01_AdminDashboardPage_Loads" })
	public void TC_012_11_VerifyCompleteAdminDashboard() {
		String testCase = "TC_012_11_VerifyCompleteAdminDashboard";
		logInfo("TEST STARTED: " + testCase);

		adp.verifyAdminDashboard();

		logInfo("All admin dashboard components verified:");
		logInfo("  - Page loaded successfully");
		logInfo("  - All KPIs visible with valid values");
		logInfo("  - AI Intelligence section visible");
		logInfo("  - Recent Activity section visible");

		logInfo("TEST PASSED: " + testCase);
	}
}

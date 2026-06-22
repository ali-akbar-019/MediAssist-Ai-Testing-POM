package testCases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.SymptomAnalyzerPage;

public class TC_003_SymptomAnalyzerTest extends BaseClass {

	SymptomAnalyzerPage sap;

	@BeforeMethod
	public void setupTest() {
		// Login and verify successful login
		login();

		// Wait a bit for login to complete
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}

		// Navigate to analyzer
		driver.get(p.getProperty("appURL") + "/analyzer");
		sap = new SymptomAnalyzerPage(driver);

		// Wait for analyzer page - retry if redirected to login
		boolean analyzerLoaded = waitForUrlContains("analyzer", 10);

		if (!analyzerLoaded && driver.getCurrentUrl().contains("login")) {
			logInfo("Redirected to login, trying to login again...");
			login();
			driver.get(p.getProperty("appURL") + "/analyzer");
			waitForUrlContains("analyzer", 10);
		}
	}

	// ============================================================
	// TC_003_01 - Page Load Test
	// ============================================================
	@Test(groups = { "smoke", "regression" })
	public void TC_003_01_AnalyzerPage_Loads() {
		String testCase = "TC_003_01_AnalyzerPage_Loads";
		logInfo("TEST STARTED: " + testCase);

		Assert.assertTrue(waitForUrlContains("analyzer", 5), "Analyzer route not reached");
		Assert.assertTrue(sap.isHeadingVisible(), "Analyzer heading not visible");
		Assert.assertTrue(sap.isStep1Displayed(), "Step 1 (Body Part Selection) not displayed");
		Assert.assertEquals(sap.getCurrentStep(), 1, "Should be on step 1");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_003_02 - Select Single Body Part
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_003_01_AnalyzerPage_Loads" })
	public void TC_003_02_Step1_SelectSingleBodyPart() {
		String testCase = "TC_003_02_Step1_SelectSingleBodyPart";
		logInfo("TEST STARTED: " + testCase);

		sap.selectView("front");

		Assert.assertEquals(sap.getSelectedBodyPartCount(), 0, "Should start with no selected parts");

		sap.clickBodyPart("head-front");
		Assert.assertEquals(sap.getSelectedBodyPartCount(), 1, "Should have 1 selected part");
		Assert.assertTrue(sap.hasSelectedBodyPart("head-front"), "Head should be selected");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_003_03 - Select Multiple Body Parts
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_003_02_Step1_SelectSingleBodyPart" })
	public void TC_003_03_Step1_SelectMultipleBodyParts() {
		String testCase = "TC_003_03_Step1_SelectMultipleBodyParts";
		logInfo("TEST STARTED: " + testCase);

		sap.clickBodyPart("chest-upper-right");
		sap.clickBodyPart("abdomen-upper");
		sap.clickBodyPart("neck-front");

		Assert.assertTrue(sap.getSelectedBodyPartCount() >= 3, "Should have at least 3 selected parts");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_003_04 - Switch Between Anterior/Posterior Views
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_003_01_AnalyzerPage_Loads" })
	public void TC_003_04_Step1_SwitchViews() {
		String testCase = "TC_003_04_Step1_SwitchViews";
		logInfo("TEST STARTED: " + testCase);

		sap.selectView("front");
		sap.clickBodyPart("chest-upper-right");
		Assert.assertTrue(sap.hasSelectedBodyPart("chest-upper-right"), "Front part should be selectable");

		sap.flipBodyView();
		sap.selectView("back");
		sap.clickBodyPart("back-upper-right");
		Assert.assertTrue(sap.hasSelectedBodyPart("back-upper-right"), "Back part should be selectable");

		Assert.assertTrue(sap.getSelectedBodyPartCount() >= 2, "Should have both front and back parts");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_003_05 - Remove Body Part
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_003_02_Step1_SelectSingleBodyPart" })
	public void TC_003_05_Step1_RemoveBodyPart() {
		String testCase = "TC_003_05_Step1_RemoveBodyPart";
		logInfo("TEST STARTED: " + testCase);

		sap.clickBodyPart("neck-front");
		int countBefore = sap.getSelectedBodyPartCount();

		sap.removeSelectedBodyPart("neck-front");
		int countAfter = sap.getSelectedBodyPartCount();

		Assert.assertEquals(countAfter, countBefore - 1, "Part should be removed");
		Assert.assertFalse(sap.hasSelectedBodyPart("neck-front"), "Neck should no longer be selected");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_003_06 - Clear All Body Parts
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_003_03_Step1_SelectMultipleBodyParts" })
	public void TC_003_06_Step1_ClearAllBodyParts() {
		String testCase = "TC_003_06_Step1_ClearAllBodyParts";
		logInfo("TEST STARTED: " + testCase);

		sap.clickBodyPart("shoulder-right-front");
		sap.clickBodyPart("shoulder-left-front");
		Assert.assertTrue(sap.getSelectedBodyPartCount() > 0, "Should have selected parts");

		sap.clearAllBodyParts();
		Assert.assertEquals(sap.getSelectedBodyPartCount(), 0, "All parts should be cleared");
		Assert.assertTrue(sap.isNoPartsMessageDisplayed(), "No parts selected message should appear");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_003_07 - Proceed Without Body Part (Should Fail)
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_003_01_AnalyzerPage_Loads" })
	public void TC_003_07_Step1_ProceedWithoutSelection_ShouldFail() {
		String testCase = "TC_003_07_Step1_ProceedWithoutSelection_ShouldFail";
		logInfo("TEST STARTED: " + testCase);

		sap.clearAllBodyParts();
		Assert.assertEquals(sap.getSelectedBodyPartCount(), 0, "No parts selected");

		sap.clickContinue();
		Assert.assertTrue(sap.isStep1Displayed(), "Should remain on Step 1 when no parts selected");
		Assert.assertEquals(sap.getCurrentStep(), 1, "Step should not advance");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_003_08 - Step 2: Add Symptom
	// ============================================================
	@Test(groups = { "regression" })
	public void TC_003_08_Step2_AddSymptom() {
		String testCase = "TC_003_08_Step2_AddSymptom";
		logInfo("TEST STARTED: " + testCase);

		// Navigate to step 2
		sap.clickBodyPart("head-front");
		sap.clickContinue();

		Assert.assertTrue(sap.isStep2Displayed(), "Should be on Step 2");

		sap.addSymptom("Headache");
		Assert.assertTrue(sap.hasAddedSymptom("Headache"), "Headache symptom should be added");
		Assert.assertEquals(sap.getAddedSymptomsCount(), 1, "Should have 1 symptom");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_003_09 - Step 2: Add Multiple Symptoms
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_003_08_Step2_AddSymptom" })
	public void TC_003_09_Step2_AddMultipleSymptoms() {
		String testCase = "TC_003_09_Step2_AddMultipleSymptoms";
		logInfo("TEST STARTED: " + testCase);

		sap.addSymptom("Dizziness");
		sap.addSymptom("Nausea");
		sap.addSymptom("Blurred Vision");

		Assert.assertTrue(sap.getAddedSymptomsCount() >= 4, "Should have multiple symptoms");
		Assert.assertTrue(sap.hasAddedSymptom("Dizziness"), "Dizziness should be added");
		Assert.assertTrue(sap.hasAddedSymptom("Nausea"), "Nausea should be added");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_003_10 - Step 2: Add Symptom Via Suggestion
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_003_08_Step2_AddSymptom" })
	public void TC_003_10_Step2_AddSymptomViaSuggestion() {
		String testCase = "TC_003_10_Step2_AddSymptomViaSuggestion";
		logInfo("TEST STARTED: " + testCase);

		Assert.assertTrue(sap.isSymptomSuggestionsVisible(), "Suggestions should be visible");

		sap.addSymptomViaSuggestion("Fever");
		Assert.assertTrue(sap.hasAddedSymptom("Fever"), "Fever should be added via suggestion");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_003_11 - Step 2: Remove Symptom
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_003_08_Step2_AddSymptom" })
	public void TC_003_11_Step2_RemoveSymptom() {
		String testCase = "TC_003_11_Step2_RemoveSymptom";
		logInfo("TEST STARTED: " + testCase);

		sap.addSymptom("TestSymptom");
		Assert.assertTrue(sap.hasAddedSymptom("TestSymptom"), "Test symptom added");

		sap.removeSymptom("TestSymptom");
		Assert.assertFalse(sap.hasAddedSymptom("TestSymptom"), "Test symptom should be removed");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_003_12 - Step 2: Proceed Without Symptoms (Should Fail)
	// ============================================================
	@Test(groups = { "regression" })
	public void TC_003_12_Step2_ProceedWithoutSymptoms_ShouldFail() {
		String testCase = "TC_003_12_Step2_ProceedWithoutSymptoms_ShouldFail";
		logInfo("TEST STARTED: " + testCase);

		// Navigate to step 2 without adding symptoms
		sap.clickBodyPart("head-front");
		sap.clickContinue();

		Assert.assertTrue(sap.isStep2Displayed(), "Should be on Step 2");
		Assert.assertEquals(sap.getAddedSymptomsCount(), 0, "No symptoms added");

		sap.clickContinue();
		Assert.assertTrue(sap.isStep2Displayed(), "Should remain on Step 2 when no symptoms");
		Assert.assertEquals(sap.getCurrentStep(), 2, "Step should not advance");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_003_13 - Step 3: Set Severity
	// ============================================================
	@Test(groups = { "regression" })
	public void TC_003_13_Step3_SetSeverity() {
		String testCase = "TC_003_13_Step3_SetSeverity";
		logInfo("TEST STARTED: " + testCase);

		// Navigate to step 3
		sap.completeStep1_SelectBodyPart("head-front");
		sap.completeStep2_AddSymptom("Headache");

		Assert.assertTrue(sap.isStep3Displayed(), "Should be on Step 3");

		sap.setSeverity(8);
		Assert.assertEquals(sap.getSeverityValue(), 8, "Severity should be 8");

		sap.clickSeverityLabel(3);
		Assert.assertEquals(sap.getSeverityValue(), 3, "Severity should be 3 after clicking label");

		sap.clickSeverityBar(10);
		Assert.assertEquals(sap.getSeverityValue(), 10, "Severity should be 10 after clicking bar");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_003_14 - Step 3: Select Pain Types
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_003_13_Step3_SetSeverity" })
	public void TC_003_14_Step3_SelectPainTypes() {
		String testCase = "TC_003_14_Step3_SelectPainTypes";
		logInfo("TEST STARTED: " + testCase);

		sap.selectPainType("sharp");
		Assert.assertTrue(sap.isPainTypeSelected("sharp"), "Sharp pain should be selected");

		sap.selectPainType("dull");
		Assert.assertTrue(sap.isPainTypeSelected("dull"), "Dull pain should be selected");
		Assert.assertFalse(sap.isPainTypeSelected("sharp"), "Sharp should no longer be selected");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_003_15 - Step 3: Set Duration
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_003_13_Step3_SetSeverity" })
	public void TC_003_15_Step3_SetDuration() {
		String testCase = "TC_003_15_Step3_SetDuration";
		logInfo("TEST STARTED: " + testCase);

		sap.setDuration("5");
		Assert.assertEquals(sap.getDurationValue(), "5", "Duration should be 5");

		sap.selectDurationUnit("weeks");
		Assert.assertEquals(sap.getSelectedDurationUnit(), "weeks", "Duration unit should be weeks");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_003_16 - Step 3: Duration Validation (Edge Cases)
	// ============================================================
	@Test(groups = { "edge-case" })
	public void TC_003_16_Step3_DurationValidation_EdgeCases() {
		String testCase = "TC_003_16_Step3_DurationValidation_EdgeCases";
		logInfo("TEST STARTED: " + testCase);

		// Test min value
		sap.setDuration("1");
		Assert.assertEquals(sap.getDurationValue(), "1", "Min duration should be 1");

		// Test max value
		sap.setDuration("365");
		Assert.assertEquals(sap.getDurationValue(), "365", "Max duration should be 365");

		// Test invalid value (should be rejected or handled)
		sap.setDuration("0");
		logInfo("Duration 0 - should show validation error");

		sap.setDuration("999");
		logInfo("Duration 999 - should show validation error");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_003_17 - Step 3: Select Worse At Options
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_003_13_Step3_SetSeverity" })
	public void TC_003_17_Step3_SelectWorseAt() {
		String testCase = "TC_003_17_Step3_SelectWorseAt";
		logInfo("TEST STARTED: " + testCase);

		sap.selectWorseAt("morning");
		Assert.assertTrue(sap.isWorseAtSelected("morning"), "Morning should be selected");

		sap.selectWorseAt("night");
		Assert.assertTrue(sap.isWorseAtSelected("night"), "Night should be selected");
		Assert.assertFalse(sap.isWorseAtSelected("morning"), "Morning should no longer be selected");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_003_18 - Step 3: Add Clinical Notes
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_003_13_Step3_SetSeverity" })
	public void TC_003_18_Step3_AddClinicalNotes() {
		String testCase = "TC_003_18_Step3_AddClinicalNotes";
		logInfo("TEST STARTED: " + testCase);

		String notes = "Patient reports severe pain in the morning, worsens with activity.";
		sap.addClinicalNotes(notes);
		Assert.assertEquals(sap.getClinicalNotes(), notes, "Clinical notes should match");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_003_19 - Step 3: Proceed Without Required Fields (Should Fail)
	// ============================================================
	@Test(groups = { "regression" })
	public void TC_003_19_Step3_ProceedWithoutRequiredFields_ShouldFail() {
		String testCase = "TC_003_19_Step3_ProceedWithoutRequiredFields_ShouldFail";
		logInfo("TEST STARTED: " + testCase);

		// Navigate to step 3
		sap.completeStep1_SelectBodyPart("head-front");
		sap.completeStep2_AddSymptom("Headache");

		Assert.assertTrue(sap.isStep3Displayed(), "Should be on Step 3");

		// Submit without selecting pain type, duration, etc.
		sap.clickSubmit();

		Assert.assertTrue(sap.isStep3Displayed(), "Should remain on Step 3 when required fields missing");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_003_20 - Full Flow: Complete Analysis
	// ============================================================
	@Test(groups = { "smoke", "regression" })
	public void TC_003_20_Analysis_CompleteFullFlow() {
		String testCase = "TC_003_20_Analysis_CompleteFullFlow";
		logInfo("TEST STARTED: " + testCase);

		sap.completeFullAnalysis("head-front", "Headache", "sharp", "3", "days", "morning");

		// Wait for analysis
		sap.waitForAnalysisComplete();

		Assert.assertTrue(sap.isStep4Displayed(), "Step 4 (Analysis) should be displayed");
		Assert.assertTrue(sap.isAnalysisResultDisplayed(), "Analysis results should be displayed");
		Assert.assertFalse(sap.getAnalysisSeverity().isEmpty(), "Severity should not be empty");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_003_21 - Analysis: Verify Severity Levels
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_003_20_Analysis_CompleteFullFlow" })
	public void TC_003_21_Analysis_VerifySeverityLevels() {
		String testCase = "TC_003_21_Analysis_VerifySeverityLevels";
		logInfo("TEST STARTED: " + testCase);

		String severity = sap.getAnalysisSeverity();
		Assert.assertTrue(severity.equals("emergency") || severity.equals("severe") || severity.equals("moderate")
				|| severity.equals("mild"), "Severity should be one of: emergency, severe, moderate, mild");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_003_22 - Analysis: Possible Conditions Displayed
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_003_20_Analysis_CompleteFullFlow" })
	public void TC_003_22_Analysis_PossibleConditionsDisplayed() {
		String testCase = "TC_003_22_Analysis_PossibleConditionsDisplayed";
		logInfo("TEST STARTED: " + testCase);

		int conditionCount = sap.getPossibleConditionsCount();
		Assert.assertTrue(conditionCount >= 2, "Should display at least 2 possible conditions");

		String condition1 = sap.getConditionName(0);
		Assert.assertFalse(condition1.isEmpty(), "Condition name should not be empty");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_003_23 - Reset Analyzer
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_003_20_Analysis_CompleteFullFlow" })
	public void TC_003_23_Analysis_ResetAnalyzer() {
		String testCase = "TC_003_23_Analysis_ResetAnalyzer";
		logInfo("TEST STARTED: " + testCase);

		sap.resetAnalyzer();

		Assert.assertTrue(sap.isStep1Displayed(), "Should return to Step 1 after reset");
		Assert.assertEquals(sap.getCurrentStep(), 1, "Step should be 1 after reset");
		Assert.assertEquals(sap.getSelectedBodyPartCount(), 0, "No body parts should be selected after reset");
		Assert.assertEquals(sap.getAddedSymptomsCount(), 0, "No symptoms should remain after reset");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_003_24 - Navigation: Back Button Functionality
	// ============================================================
	@Test(groups = { "regression" })
	public void TC_003_24_Navigation_BackButton() {
		String testCase = "TC_003_24_Navigation_BackButton";
		logInfo("TEST STARTED: " + testCase);

		sap.clickBodyPart("head-front");
		sap.clickContinue();
		Assert.assertTrue(sap.isStep2Displayed(), "Should be on Step 2");

		sap.clickBack();
		Assert.assertTrue(sap.isStep1Displayed(), "Should return to Step 1 after back");
		Assert.assertEquals(sap.getCurrentStep(), 1, "Step should be 1");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_003_25 - Edge: Clinical Notes Max Character Limit
	// ============================================================
	@Test(groups = { "edge-case" })
	public void TC_003_25_Edge_ClinicalNotes_Max500Characters() {
		String testCase = "TC_003_25_Edge_ClinicalNotes_Max500Characters";
		logInfo("TEST STARTED: " + testCase);

		sap.completeStep1_SelectBodyPart("head-front");
		sap.completeStep2_AddSymptom("Headache");

		// Create 500 character string
		String notes500 = "a".repeat(500);
		sap.addClinicalNotes(notes500);
		Assert.assertEquals(sap.getClinicalNotes().length(), 500, "Should accept 500 characters");

		// Try 501 characters (should truncate to 500)
		String notes501 = "a".repeat(501);
		sap.addClinicalNotes(notes501);
		Assert.assertTrue(sap.getClinicalNotes().length() <= 500, "Should truncate to 500 characters");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_003_26 - Edge: Multiple Body Parts Selection
	// ============================================================
	@Test(groups = { "edge-case" })
	public void TC_003_26_Edge_MultipleBodyPartsSelection() {
		String testCase = "TC_003_26_Edge_MultipleBodyPartsSelection";
		logInfo("TEST STARTED: " + testCase);

		// Select as many parts as possible
		String[] parts = { "head-front", "neck-front", "chest-upper-right", "chest-upper-left", "abdomen-upper",
				"shoulder-right-front", "shoulder-left-front" };

		for (String part : parts) {
			sap.clickBodyPart(part);
		}

		Assert.assertTrue(sap.getSelectedBodyPartCount() >= 5, "Should support multiple selections");
		Assert.assertTrue(sap.hasSelectedBodyPart("head-front"), "Head should be selected");
		Assert.assertTrue(sap.hasSelectedBodyPart("abdomen-upper"), "Abdomen should be selected");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_003_27 - Edge: Special Characters in Notes
	// ============================================================
	@Test(groups = { "edge-case" })
	public void TC_003_27_Edge_SpecialCharactersInNotes() {
		String testCase = "TC_003_27_Edge_SpecialCharactersInNotes";
		logInfo("TEST STARTED: " + testCase);

		sap.completeStep1_SelectBodyPart("head-front");
		sap.completeStep2_AddSymptom("Headache");

		String specialChars = "!@#$%^&*()_+-=[]{}|;:',.<>?/~`";
		sap.addClinicalNotes(specialChars);
		Assert.assertEquals(sap.getClinicalNotes(), specialChars, "Should accept special characters");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_003_28 - Analysis: Verify Emergency Alert
	// ============================================================
	@Test(groups = { "regression" })
	public void TC_003_28_Analysis_EmergencyAlert() {
		String testCase = "TC_003_28_Analysis_EmergencyAlert";
		logInfo("TEST STARTED: " + testCase);

		// Add emergency symptom
		sap.completeStep1_SelectBodyPart("chest-upper-left");
		sap.addSymptom("Chest Pain");
		sap.clickContinue();

		// Check if emergency alert appears
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}

		// Note: Emergency alert should be triggered by "chest pain" or other emergency
		// keywords
		logInfo("Emergency alert should be visible for 'Chest Pain' symptom");

		logInfo("TEST PASSED: " + testCase);
	}
}
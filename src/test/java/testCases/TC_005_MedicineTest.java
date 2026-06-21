package testCases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.MedicinePage;

public class TC_005_MedicineTest extends BaseClass {

	MedicinePage mp;

	@BeforeMethod
public void loginFirst() {
    login();
    driver.get(p.getProperty("appURL") + "/medicine");
    mp = new MedicinePage(driver);
    waitForUrlContains("medicine", 10);
}

	// ============================================================
	// TC_005_01 - Page Load Test
	// ============================================================
	@Test(groups = { "smoke", "regression", "edge-case" })
	public void TC_005_01_MedicinePage_Loads() {
		String testCase = "TC_005_01_MedicinePage_Loads";
		logInfo("TEST STARTED: " + testCase);

		
		

		Assert.assertTrue(waitForUrlContains("medicine", 5), "Medicine route not reached");
		Assert.assertTrue(mp.isHeadingVisible(), "Medicine heading not visible");
		Assert.assertTrue(mp.isSearchInputVisible(), "Search input not visible");
		Assert.assertTrue(mp.isCommonMedicinesVisible(), "Common medicines container not visible");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_005_02 - Search Input Validation
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_005_01_MedicinePage_Loads" })
	public void TC_005_02_SearchInput_Validation() {
		String testCase = "TC_005_02_SearchInput_Validation";
		logInfo("TEST STARTED: " + testCase);

		

		// Search button should be disabled when input is empty
		mp.enterSearch("");
		Assert.assertTrue(mp.isSearchButtonDisabled(), "Search button should be disabled when input is empty");

		// Search button should be enabled when input has text
		mp.enterSearch("Paracetamol");
		Assert.assertTrue(mp.isSearchButtonEnabled(), "Search button should be enabled when input has text");

		// Verify input value
		Assert.assertEquals(mp.getSearchInputValue(), "Paracetamol", "Search input value should match");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_005_03 - Search With Enter Key
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_005_01_MedicinePage_Loads" })
	public void TC_005_03_Search_WithEnterKey() {
		String testCase = "TC_005_03_Search_WithEnterKey";
		logInfo("TEST STARTED: " + testCase);

		
		mp.searchWithEnter("Paracetamol");
		mp.waitForSearchToComplete();

		Assert.assertTrue(mp.isResultDisplayed(), "Result should be displayed after search with Enter");
		Assert.assertEquals(mp.getMedicineName(), "Paracetamol", "Medicine name should be Paracetamol");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_005_04 - Search Valid Medicine
	// ============================================================
	@Test(groups = { "smoke", "regression" }, dependsOnMethods = { "TC_005_01_MedicinePage_Loads" })
	public void TC_005_04_Search_ValidMedicine() {
		String testCase = "TC_005_04_Search_ValidMedicine";
		logInfo("TEST STARTED: " + testCase);

		
		mp.completeFullSearch("Paracetamol");

		Assert.assertTrue(mp.isResultDisplayed(), "Result should be displayed");
		Assert.assertEquals(mp.getMedicineName(), "Paracetamol", "Medicine name should be Paracetamol");
		Assert.assertFalse(mp.getGenericName().isEmpty(), "Generic name should not be empty");
		Assert.assertFalse(mp.getDosageInfo().isEmpty(), "Dosage info should not be empty");
		Assert.assertTrue(mp.isDisclaimerVisible(), "Disclaimer should be visible");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_005_05 - Search Invalid Medicine
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_005_01_MedicinePage_Loads" })
	public void TC_005_05_Search_InvalidMedicine() {
		String testCase = "TC_005_05_Search_InvalidMedicine";
		logInfo("TEST STARTED: " + testCase);

		
		mp.search("InvalidMedicineNameXYZ123");
		mp.waitForSearchToComplete();

		Assert.assertTrue(mp.isErrorDisplayed(), "Error message should be displayed");
		Assert.assertTrue(mp.isErrorContains("Failed to fetch"), "Error should mention failure");
		Assert.assertFalse(mp.isResultDisplayed(), "Result should not be displayed for invalid medicine");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_005_06 - Search Empty Query
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_005_01_MedicinePage_Loads" })
	public void TC_005_06_Search_EmptyQuery() {
		String testCase = "TC_005_06_Search_EmptyQuery";
		logInfo("TEST STARTED: " + testCase);

		
		mp.search("");

		Assert.assertFalse(mp.isResultDisplayed(), "Result should not appear for empty query");
		Assert.assertFalse(mp.isErrorDisplayed(), "Error should not appear for empty query");
		Assert.assertTrue(mp.isCommonMedicinesVisible(), "Common medicines should still be visible");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_005_07 - Search With Special Characters
	// ============================================================
	@Test(groups = { "edge-case" }, dependsOnMethods = { "TC_005_01_MedicinePage_Loads" })
	public void TC_005_07_Search_SpecialCharacters() {
		String testCase = "TC_005_07_Search_SpecialCharacters";
		logInfo("TEST STARTED: " + testCase);

		
		mp.search("@#$%^&*()");
		mp.waitForSearchToComplete();

		Assert.assertTrue(mp.isErrorDisplayed(), "Error should be displayed for special characters");
		Assert.assertTrue(mp.isErrorContains("Failed to fetch"), "Error should mention failure");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_005_08 - Search With Numbers Only
	// ============================================================
	@Test(groups = { "edge-case" }, dependsOnMethods = { "TC_005_01_MedicinePage_Loads" })
	public void TC_005_08_Search_NumbersOnly() {
		String testCase = "TC_005_08_Search_NumbersOnly";
		logInfo("TEST STARTED: " + testCase);

		
		mp.search("12345");
		mp.waitForSearchToComplete();

		Assert.assertTrue(mp.isErrorDisplayed(), "Error should be displayed for numbers only");
		Assert.assertTrue(mp.isErrorContains("Failed to fetch"), "Error should mention failure");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_005_09 - Common Medicine Quick Pick
	// ============================================================
	@Test(groups = { "smoke", "regression" }, dependsOnMethods = { "TC_005_01_MedicinePage_Loads" })
	public void TC_005_09_CommonMedicine_QuickPick() {
		String testCase = "TC_005_09_CommonMedicine_QuickPick";
		logInfo("TEST STARTED: " + testCase);

		

		Assert.assertTrue(mp.getCommonMedicinesCount() > 0, "Common medicines should be visible");
		Assert.assertTrue(mp.isCommonMedicineVisible("Paracetamol"), "Paracetamol should be in common medicines");

		mp.completeFullSearchWithCommonPick("Paracetamol");

		Assert.assertTrue(mp.isResultDisplayed(), "Result should be displayed after quick pick");
		Assert.assertEquals(mp.getMedicineName(), "Paracetamol", "Medicine name should be Paracetamol");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_005_10 - Common Medicine - Ibuprofen
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_005_01_MedicinePage_Loads" })
	public void TC_005_10_CommonMedicine_Ibuprofen() {
		String testCase = "TC_005_10_CommonMedicine_Ibuprofen";
		logInfo("TEST STARTED: " + testCase);

		
		mp.completeFullSearchWithCommonPick("Ibuprofen");

		Assert.assertTrue(mp.isResultDisplayed(), "Result should be displayed");
		Assert.assertEquals(mp.getMedicineName(), "Ibuprofen", "Medicine name should be Ibuprofen");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_005_11 - Result Sections: Uses
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_005_04_Search_ValidMedicine" })
	public void TC_005_11_ResultSection_Uses() {
		String testCase = "TC_005_11_ResultSection_Uses";
		logInfo("TEST STARTED: " + testCase);

		
		mp.completeFullSearch("Paracetamol");

		Assert.assertTrue(mp.isSectionVisible("uses"), "Uses section should be visible");

		mp.expandSection("uses");
		Assert.assertTrue(mp.isSectionExpanded("uses"), "Uses section should be expanded");
		Assert.assertTrue(mp.getSectionItemCount("uses") > 0, "Uses section should have items");

		String itemText = mp.getSectionItemText("uses", 0);
		Assert.assertFalse(itemText.isEmpty(), "First use item should not be empty");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_005_12 - Result Sections: Side Effects
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_005_04_Search_ValidMedicine" })
	public void TC_005_12_ResultSection_SideEffects() {
		String testCase = "TC_005_12_ResultSection_SideEffects";
		logInfo("TEST STARTED: " + testCase);

		
		mp.completeFullSearch("Paracetamol");

		Assert.assertTrue(mp.isSectionVisible("sideEffects"), "Side Effects section should be visible");

		mp.expandSection("sideEffects");
		Assert.assertTrue(mp.isSectionExpanded("sideEffects"), "Side Effects section should be expanded");
		Assert.assertTrue(mp.getSectionItemCount("sideEffects") > 0, "Side Effects section should have items");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_005_13 - Result Sections: Warnings
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_005_04_Search_ValidMedicine" })
	public void TC_005_13_ResultSection_Warnings() {
		String testCase = "TC_005_13_ResultSection_Warnings";
		logInfo("TEST STARTED: " + testCase);

		
		mp.completeFullSearch("Paracetamol");

		Assert.assertTrue(mp.isSectionVisible("warnings"), "Warnings section should be visible");

		mp.expandSection("warnings");
		Assert.assertTrue(mp.isSectionExpanded("warnings"), "Warnings section should be expanded");
		Assert.assertTrue(mp.getSectionItemCount("warnings") > 0, "Warnings section should have items");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_005_14 - Result Sections: Interactions
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_005_04_Search_ValidMedicine" })
	public void TC_005_14_ResultSection_Interactions() {
		String testCase = "TC_005_14_ResultSection_Interactions";
		logInfo("TEST STARTED: " + testCase);

		
		mp.completeFullSearch("Paracetamol");

		Assert.assertTrue(mp.isSectionVisible("interactions"), "Interactions section should be visible");

		mp.expandSection("interactions");
		Assert.assertTrue(mp.isSectionExpanded("interactions"), "Interactions section should be expanded");
		Assert.assertTrue(mp.getSectionItemCount("interactions") > 0, "Interactions section should have items");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_005_15 - Expand All Sections
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_005_04_Search_ValidMedicine" })
	public void TC_005_15_Result_ExpandAllSections() {
		String testCase = "TC_005_15_Result_ExpandAllSections";
		logInfo("TEST STARTED: " + testCase);

		
		mp.completeFullSearch("Paracetamol");

		mp.expandAllSections();

		Assert.assertTrue(mp.isSectionExpanded("uses"), "Uses section should be expanded");
		Assert.assertTrue(mp.isSectionExpanded("sideEffects"), "Side Effects section should be expanded");
		Assert.assertTrue(mp.isSectionExpanded("warnings"), "Warnings section should be expanded");
		Assert.assertTrue(mp.isSectionExpanded("interactions"), "Interactions section should be expanded");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_005_16 - All Sections Present
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_005_04_Search_ValidMedicine" })
	public void TC_005_16_Result_AllSectionsPresent() {
		String testCase = "TC_005_16_Result_AllSectionsPresent";
		logInfo("TEST STARTED: " + testCase);

		
		mp.completeFullSearch("Paracetamol");

		Assert.assertTrue(mp.verifyAllSectionsPresent(), "All sections should be present");
		Assert.assertTrue(mp.verifyAllSectionsHaveItems(), "All sections should have items");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_005_17 - Search Another Medicine
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_005_04_Search_ValidMedicine" })
	public void TC_005_17_SearchAnotherMedicine() {
		String testCase = "TC_005_17_SearchAnotherMedicine";
		logInfo("TEST STARTED: " + testCase);

		
		mp.completeFullSearch("Paracetamol");
		Assert.assertTrue(mp.isResultDisplayed(), "Result should be displayed");

		Assert.assertTrue(mp.isSearchAnotherMedicineVisible(), "Search another medicine button should be visible");

		mp.clickSearchAnotherMedicine();
		mp.waitForLoadingToDisappear();

		Assert.assertFalse(mp.isResultDisplayed(), "Result should be cleared after clicking search another");
		Assert.assertTrue(mp.isCommonMedicinesVisible(), "Common medicines should be visible after reset");
		Assert.assertTrue(mp.getSearchInputValue().isEmpty(), "Search input should be cleared");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_005_18 - Multiple Searches
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_005_01_MedicinePage_Loads" })
	public void TC_005_18_MultipleSearches() {
		String testCase = "TC_005_18_MultipleSearches";
		logInfo("TEST STARTED: " + testCase);

		

		String[] medicines = { "Paracetamol", "Ibuprofen", "Amoxicillin" };

		for (String med : medicines) {
			mp.search(med);
			mp.waitForSearchToComplete();
			Assert.assertTrue(mp.isResultDisplayed(), "Result should be displayed for " + med);
			Assert.assertEquals(mp.getMedicineName(), med, "Medicine name should be " + med);

			mp.clickSearchAnotherMedicine();
			mp.waitForLoadingToDisappear();
		}

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_005_19 - Loading State
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_005_01_MedicinePage_Loads" })
	public void TC_005_19_LoadingState() {
		String testCase = "TC_005_19_LoadingState";
		logInfo("TEST STARTED: " + testCase);

		

		// Start search - loading should appear
		mp.enterSearch("Paracetamol");
		mp.clickSearch();

		// Check loading is displayed (briefly)
		boolean loadingVisible = mp.isLoadingDisplayed();
		logInfo("Loading visible: " + loadingVisible);

		// Wait for loading to disappear
		mp.waitForSearchToComplete();

		Assert.assertTrue(mp.isResultDisplayed(), "Result should appear after loading");
		Assert.assertEquals(mp.getMedicineName(), "Paracetamol", "Medicine name should be Paracetamol");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_005_20 - Verify Full Medicine Info
	// ============================================================
	@Test(groups = { "smoke", "regression" }, dependsOnMethods = { "TC_005_01_MedicinePage_Loads" })
	public void TC_005_20_VerifyFullMedicineInfo() {
		String testCase = "TC_005_20_VerifyFullMedicineInfo";
		logInfo("TEST STARTED: " + testCase);

		
		mp.completeFullSearch("Paracetamol");

		mp.verifyFullMedicineInfo("Paracetamol");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_005_21 - Edge: Very Long Medicine Name
	// ============================================================
	@Test(groups = { "edge-case" }, dependsOnMethods = { "TC_005_01_MedicinePage_Loads" })
	public void TC_005_21_Edge_VeryLongMedicineName() {
		String testCase = "TC_005_21_Edge_VeryLongMedicineName";
		logInfo("TEST STARTED: " + testCase);

		
		String longName = "A".repeat(200);
		mp.search(longName);
		mp.waitForSearchToComplete();

		Assert.assertTrue(mp.isErrorDisplayed(), "Error should be displayed for very long name");
		Assert.assertTrue(mp.isErrorContains("Failed to fetch"), "Error should mention failure");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_005_22 - Edge: Whitespace Only
	// ============================================================
	@Test(groups = { "edge-case" }, dependsOnMethods = { "TC_005_01_MedicinePage_Loads" })
	public void TC_005_22_Edge_WhitespaceOnly() {
		String testCase = "TC_005_22_Edge_WhitespaceOnly";
		logInfo("TEST STARTED: " + testCase);

		
		mp.search("   ");
		mp.waitForSearchToComplete();

		Assert.assertFalse(mp.isResultDisplayed(), "Result should not appear for whitespace only");
		Assert.assertFalse(mp.isErrorDisplayed(), "Error should not appear for whitespace only");
		Assert.assertTrue(mp.isCommonMedicinesVisible(), "Common medicines should still be visible");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_005_23 - Edge: Case Sensitivity
	// ============================================================
	@Test(groups = { "edge-case" }, dependsOnMethods = { "TC_005_01_MedicinePage_Loads" })
	public void TC_005_23_Edge_CaseSensitivity() {
		String testCase = "TC_005_23_Edge_CaseSensitivity";
		logInfo("TEST STARTED: " + testCase);

		
		mp.completeFullSearch("paracetamol"); // lowercase

		Assert.assertTrue(mp.isResultDisplayed(), "Result should be displayed for lowercase query");
		Assert.assertEquals(mp.getMedicineName(), "Paracetamol", "Medicine name should be properly capitalized");

		mp.clickSearchAnotherMedicine();

		mp.completeFullSearch("PARACETAMOL"); // uppercase
		Assert.assertTrue(mp.isResultDisplayed(), "Result should be displayed for uppercase query");
		Assert.assertEquals(mp.getMedicineName(), "Paracetamol", "Medicine name should be properly capitalized");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_005_24 - Edge: Search Then Reset Then Search Again
	// ============================================================
	@Test(groups = { "edge-case" }, dependsOnMethods = { "TC_005_01_MedicinePage_Loads" })
	public void TC_005_24_Edge_SearchResetSearch() {
		String testCase = "TC_005_24_Edge_SearchResetSearch";
		logInfo("TEST STARTED: " + testCase);

		

		// First search
		mp.completeFullSearch("Paracetamol");
		Assert.assertTrue(mp.isResultDisplayed(), "First search result should be displayed");
		Assert.assertEquals(mp.getMedicineName(), "Paracetamol", "First search medicine name should match");

		// Reset
		mp.clickSearchAnotherMedicine();
		mp.waitForLoadingToDisappear();
		Assert.assertFalse(mp.isResultDisplayed(), "Result should be cleared");

		// Second search
		mp.completeFullSearch("Ibuprofen");
		Assert.assertTrue(mp.isResultDisplayed(), "Second search result should be displayed");
		Assert.assertEquals(mp.getMedicineName(), "Ibuprofen", "Second search medicine name should match");

		logInfo("TEST PASSED: " + testCase);
	}
}
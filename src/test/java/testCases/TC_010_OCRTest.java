package testCases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.OCRPage;

public class TC_010_OCRTest extends BaseClass {

	OCRPage ocrPage;

	@BeforeMethod
public void loginFirst() {
    login();
    driver.get(p.getProperty("appURL") + "/ocr");
    ocrPage = new OCRPage(driver);
    ocrPage.waitForPageLoad();
    waitForUrlContains("ocr", 10);
}

	// ============================================================
	// TC_010_01 - Page Load Test
	// ============================================================
	@Test(groups = { "smoke", "regression", "edge-case" })
	public void TC_010_01_OCRPage_Loads() {
		String testCase = "TC_010_01_OCRPage_Loads";
		logInfo("TEST STARTED: " + testCase);

		
		
		ocrPage.waitForPageLoad();

		Assert.assertTrue(waitForUrlContains("ocr", 5), "OCR route not reached");
		Assert.assertTrue(ocrPage.isPageVisible(), "OCR page not visible");
		Assert.assertTrue(ocrPage.isHeadingVisible(), "OCR heading not visible");
		Assert.assertTrue(ocrPage.isTabsVisible(), "Tabs not visible");
		Assert.assertTrue(ocrPage.isScanTabActive(), "Scan tab should be active by default");
		Assert.assertTrue(ocrPage.isScanTabContentVisible(), "Scan tab content not visible");
		Assert.assertTrue(ocrPage.isUploadDropzoneVisible(), "Upload dropzone not visible");
		Assert.assertTrue(ocrPage.isDocTypesVisible(), "Document types not visible");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_010_02 - Switch to History Tab
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_010_01_OCRPage_Loads" })
	public void TC_010_02_SwitchToHistoryTab() {
		String testCase = "TC_010_02_SwitchToHistoryTab";
		logInfo("TEST STARTED: " + testCase);

		

		ocrPage.switchToHistoryTab();
		Assert.assertTrue(ocrPage.isHistoryTabActive(), "History tab should be active");
		Assert.assertTrue(ocrPage.isHistoryTabContentVisible(), "History tab content should be visible");

		ocrPage.switchToScanTab();
		Assert.assertTrue(ocrPage.isScanTabActive(), "Scan tab should be active again");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_010_03 - Select Document Types
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_010_01_OCRPage_Loads" })
	public void TC_010_03_SelectDocumentTypes() {
		String testCase = "TC_010_03_SelectDocumentTypes";
		logInfo("TEST STARTED: " + testCase);

		

		Assert.assertTrue(ocrPage.verifyAllDocTypesPresent(), "All document types should be present");

		// Test each document type
		ocrPage.selectPrescription();
		Assert.assertTrue(ocrPage.isDocTypeSelected("prescription"), "Prescription should be selected");

		ocrPage.selectLabReport();
		Assert.assertTrue(ocrPage.isDocTypeSelected("lab_report"), "Lab Report should be selected");
		Assert.assertFalse(ocrPage.isDocTypeSelected("prescription"), "Prescription should be deselected");

		ocrPage.selectMedicalReport();
		Assert.assertTrue(ocrPage.isDocTypeSelected("medical_report"), "Medical Report should be selected");

		ocrPage.selectOther();
		Assert.assertTrue(ocrPage.isDocTypeSelected("other"), "Other should be selected");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_010_04 - Upload Prescription Document
	// ============================================================
	@Test(groups = { "smoke", "regression" }, dependsOnMethods = { "TC_010_01_OCRPage_Loads" })
	public void TC_010_04_UploadPrescriptionDocument() {
		String testCase = "TC_010_04_UploadPrescriptionDocument";
		logInfo("TEST STARTED: " + testCase);

		

		String filePath = System.getProperty("user.dir") + "/src/test/resources/test-files/prescription.jpg";
		logInfo("Uploading file: " + filePath);

		ocrPage.completeFullUploadFlowWithPrescription(filePath);
		ocrPage.waitForResultToAppear();

		Assert.assertTrue(ocrPage.isResultViewVisible(), "Result view should be visible");
		Assert.assertTrue(ocrPage.getResultTitle().contains("Prescription"),
				"Result title should contain Prescription");
		Assert.assertTrue(ocrPage.getResultFilename().contains("prescription"), "Filename should contain prescription");
		Assert.assertFalse(ocrPage.getSummary().isEmpty(), "Summary should not be empty");
		Assert.assertTrue(ocrPage.getNotesCount() > 0, "Should have at least one note");
		Assert.assertTrue(ocrPage.isDisclaimerVisible(), "Disclaimer should be visible");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_010_05 - Upload Lab Report Document
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_010_01_OCRPage_Loads" })
	public void TC_010_05_UploadLabReportDocument() {
		String testCase = "TC_010_05_UploadLabReportDocument";
		logInfo("TEST STARTED: " + testCase);

		

		String filePath = System.getProperty("user.dir") + "/src/test/resources/test-files/lab_report.pdf";
		logInfo("Uploading file: " + filePath);

		ocrPage.completeFullUploadFlowWithLabReport(filePath);
		ocrPage.waitForResultToAppear();

		Assert.assertTrue(ocrPage.isResultViewVisible(), "Result view should be visible");
		Assert.assertTrue(ocrPage.getResultTitle().contains("Lab Report"), "Result title should contain Lab Report");
		Assert.assertFalse(ocrPage.getSummary().isEmpty(), "Summary should not be empty");
		Assert.assertTrue(ocrPage.getNotesCount() > 0, "Should have at least one note");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_010_06 - Upload Medical Report Document
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_010_01_OCRPage_Loads" })
	public void TC_010_06_UploadMedicalReportDocument() {
		String testCase = "TC_010_06_UploadMedicalReportDocument";
		logInfo("TEST STARTED: " + testCase);

		

		String filePath = System.getProperty("user.dir") + "/src/test/resources/test-files/medical_report.png";
		logInfo("Uploading file: " + filePath);

		ocrPage.completeFullUploadFlowWithMedicalReport(filePath);
		ocrPage.waitForResultToAppear();

		Assert.assertTrue(ocrPage.isResultViewVisible(), "Result view should be visible");
		Assert.assertTrue(ocrPage.getResultTitle().contains("Medical Report"),
				"Result title should contain Medical Report");
		Assert.assertFalse(ocrPage.getSummary().isEmpty(), "Summary should not be empty");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_010_07 - Upload Other Document Type
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_010_01_OCRPage_Loads" })
	public void TC_010_07_UploadOtherDocument() {
		String testCase = "TC_010_07_UploadOtherDocument";
		logInfo("TEST STARTED: " + testCase);

		

		String filePath = System.getProperty("user.dir") + "/src/test/resources/test-files/other_document.pdf";
		logInfo("Uploading file: " + filePath);

		ocrPage.completeFullUploadFlowWithOther(filePath);
		ocrPage.waitForResultToAppear();

		Assert.assertTrue(ocrPage.isResultViewVisible(), "Result view should be visible");
		Assert.assertTrue(ocrPage.getResultTitle().contains("Document"), "Result title should contain Document");
		Assert.assertFalse(ocrPage.getSummary().isEmpty(), "Summary should not be empty");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_010_08 - Toggle Raw Text View
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_010_04_UploadPrescriptionDocument" })
	public void TC_010_08_ToggleRawTextView() {
		String testCase = "TC_010_08_ToggleRawTextView";
		logInfo("TEST STARTED: " + testCase);

		
		ocrPage.waitForResultToAppear();

		Assert.assertFalse(ocrPage.isRawTextVisible(), "Raw text should not be visible initially");

		ocrPage.clickToggleRawText();
		Assert.assertTrue(ocrPage.isRawTextVisible(), "Raw text should be visible after toggle");
		Assert.assertFalse(ocrPage.getRawText().isEmpty(), "Raw text should not be empty");

		ocrPage.clickToggleRawText();
		Assert.assertFalse(ocrPage.isRawTextVisible(), "Raw text should be hidden after second toggle");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_010_09 - View Notes in Result
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_010_04_UploadPrescriptionDocument" })
	public void TC_010_09_ViewNotesInResult() {
		String testCase = "TC_010_09_ViewNotesInResult";
		logInfo("TEST STARTED: " + testCase);

		
		ocrPage.waitForResultToAppear();

		int notesCount = ocrPage.getNotesCount();
		Assert.assertTrue(notesCount > 0, "Should have at least one note");

		for (int i = 0; i < notesCount && i < 3; i++) {
			String note = ocrPage.getNote(i);
			Assert.assertFalse(note.isEmpty(), "Note at index " + i + " should not be empty");
			logInfo("Note " + (i + 1) + ": " + note);
		}

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_010_10 - New Registry Button
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_010_04_UploadPrescriptionDocument" })
	public void TC_010_10_NewRegistryButton() {
		String testCase = "TC_010_10_NewRegistryButton";
		logInfo("TEST STARTED: " + testCase);

		
		ocrPage.waitForResultToAppear();

		Assert.assertTrue(ocrPage.isResultViewVisible(), "Result should be visible");

		ocrPage.clickNewRegistry();
		ocrPage.waitForPageLoad();

		Assert.assertFalse(ocrPage.isResultViewVisible(), "Result should not be visible after New Registry");
		Assert.assertTrue(ocrPage.isUploadDropzoneVisible(), "Upload dropzone should be visible");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_010_11 - Clear Uploaded File
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_010_01_OCRPage_Loads" })
	public void TC_010_11_ClearUploadedFile() {
		String testCase = "TC_010_11_ClearUploadedFile";
		logInfo("TEST STARTED: " + testCase);

		

		// Upload a file
		String filePath = System.getProperty("user.dir") + "/src/test/resources/test-files/prescription.jpg";
		ocrPage.selectPrescription();
		ocrPage.uploadFile(filePath);
		ocrPage.waitForUploadToComplete();

		Assert.assertTrue(ocrPage.isUploadComplete(), "File should be uploaded");
		Assert.assertFalse(ocrPage.getUploadedFileName().isEmpty(), "Uploaded file name should not be empty");

		ocrPage.clearUploadedFile();
		Assert.assertFalse(ocrPage.isUploadComplete(), "File should be cleared");
		Assert.assertTrue(ocrPage.isUploadDropzoneVisible(), "Upload dropzone should be visible");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_010_12 - Upload Progress Display
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_010_01_OCRPage_Loads" })
	public void TC_010_12_UploadProgressDisplay() {
		String testCase = "TC_010_12_UploadProgressDisplay";
		logInfo("TEST STARTED: " + testCase);

		

		String filePath = System.getProperty("user.dir") + "/src/test/resources/test-files/prescription.jpg";

		ocrPage.selectPrescription();
		ocrPage.uploadFile(filePath);

		// Check if progress is displayed
		Assert.assertTrue(ocrPage.isUploadingInProgress(), "Upload progress should be displayed");

		String progressText = ocrPage.getProgressText();
		Assert.assertTrue(progressText.contains("Uploading & Analyzing"),
				"Progress text should contain uploading message");

		int progress = ocrPage.getUploadProgress();
		logInfo("Upload progress: " + progress + "%");

		ocrPage.waitForUploadToComplete();
		Assert.assertFalse(ocrPage.isUploadingInProgress(), "Upload progress should disappear after completion");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_010_13 - History Tab - View History
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_010_04_UploadPrescriptionDocument" })
	public void TC_010_13_HistoryTab_ViewHistory() {
		String testCase = "TC_010_13_HistoryTab_ViewHistory";
		logInfo("TEST STARTED: " + testCase);

		

		ocrPage.switchToHistoryTab();
		ocrPage.waitForHistoryToLoad();

		int historyCount = ocrPage.getHistoryCount();
		int historyItems = ocrPage.getHistoryItemsCount();

		Assert.assertEquals(historyCount, historyItems, "History count badge should match items count");
		Assert.assertTrue(historyCount > 0, "History should have at least one item");

		// Verify first history item
		String label = ocrPage.getHistoryItemLabel(0);
		Assert.assertFalse(label.isEmpty(), "History item label should not be empty");
		logInfo("First history item: " + label);

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_010_14 - History Tab - Select History Item
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_010_13_HistoryTab_ViewHistory" })
	public void TC_010_14_HistoryTab_SelectHistoryItem() {
		String testCase = "TC_010_14_HistoryTab_SelectHistoryItem";
		logInfo("TEST STARTED: " + testCase);

		

		ocrPage.switchToHistoryTab();
		ocrPage.waitForHistoryToLoad();

		int itemsCount = ocrPage.getHistoryItemsCount();
		if (itemsCount > 0) {
			String filename = ocrPage.getHistoryItemFilename(0);
			logInfo("Selecting history item: " + filename);

			ocrPage.clickHistoryItem(0);
			ocrPage.waitForResultToAppear();

			Assert.assertTrue(ocrPage.isScanTabActive(), "Should switch to scan tab");
			Assert.assertTrue(ocrPage.isResultViewVisible(), "Result view should be visible");
			Assert.assertTrue(ocrPage.getResultFilename().contains(filename.replace("• ", "").trim()),
					"Result filename should match selected history item");
		} else {
			logInfo("No history items to select");
		}

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_010_15 - History Tab - Delete History Item
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_010_13_HistoryTab_ViewHistory" })
	public void TC_010_15_HistoryTab_DeleteHistoryItem() {
		String testCase = "TC_010_15_HistoryTab_DeleteHistoryItem";
		logInfo("TEST STARTED: " + testCase);

		

		ocrPage.switchToHistoryTab();
		ocrPage.waitForHistoryToLoad();

		int itemsBefore = ocrPage.getHistoryItemsCount();
		int countBefore = ocrPage.getHistoryCount();

		if (itemsBefore > 0) {
			ocrPage.deleteHistoryItem(0);
			ocrPage.waitForHistoryToLoad();

			int itemsAfter = ocrPage.getHistoryItemsCount();
			int countAfter = ocrPage.getHistoryCount();

			Assert.assertTrue(itemsAfter < itemsBefore, "History item should be deleted");
			Assert.assertTrue(countAfter < countBefore, "History count should decrease");
			logInfo("History item deleted. Items before: " + itemsBefore + ", after: " + itemsAfter);
		} else {
			logInfo("No history items to delete");
		}

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_010_16 - Edge: Empty History
	// ============================================================
	@Test(groups = { "edge-case" }, dependsOnMethods = { "TC_010_01_OCRPage_Loads" })
	public void TC_010_16_Edge_EmptyHistory() {
		String testCase = "TC_010_16_Edge_EmptyHistory";
		logInfo("TEST STARTED: " + testCase);

		

		ocrPage.switchToHistoryTab();
		ocrPage.waitForHistoryToLoad();

		// If there are items, delete them all first
		int itemsCount = ocrPage.getHistoryItemsCount();
		for (int i = itemsCount - 1; i >= 0; i--) {
			ocrPage.deleteHistoryItem(i);
			ocrPage.waitForHistoryToLoad();
		}

		ocrPage.switchToHistoryTab();
		ocrPage.waitForHistoryToLoad();

		Assert.assertTrue(ocrPage.isHistoryEmptyVisible(), "Empty history message should be visible");
		Assert.assertEquals(ocrPage.getHistoryItemsCount(), 0, "History items should be 0");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_010_17 - Edge: Upload Invalid File Type
	// ============================================================
	@Test(groups = { "edge-case" }, dependsOnMethods = { "TC_010_01_OCRPage_Loads" })
	public void TC_010_17_Edge_UploadInvalidFileType() {
		String testCase = "TC_010_17_Edge_UploadInvalidFileType";
		logInfo("TEST STARTED: " + testCase);

		

		String filePath = System.getProperty("user.dir") + "/src/test/resources/test-files/invalid.txt";
		logInfo("Uploading invalid file: " + filePath);

		ocrPage.selectPrescription();
		ocrPage.uploadFile(filePath);
		ocrPage.waitForErrorToAppear();

		Assert.assertTrue(ocrPage.isErrorVisible(), "Error should be displayed for invalid file type");
		Assert.assertTrue(
				ocrPage.isErrorContains("JPG") || ocrPage.isErrorContains("PNG") || ocrPage.isErrorContains("PDF"),
				"Error should mention accepted file types");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_010_18 - Edge: Upload File Larger Than 10MB
	// ============================================================
	@Test(groups = { "edge-case" }, dependsOnMethods = { "TC_010_01_OCRPage_Loads" })
	public void TC_010_18_Edge_UploadFileTooLarge() {
		String testCase = "TC_010_18_Edge_UploadFileTooLarge";
		logInfo("TEST STARTED: " + testCase);

		

		String filePath = System.getProperty("user.dir") + "/src/test/resources/test-files/large_file.pdf";
		logInfo("Uploading large file: " + filePath);

		ocrPage.selectPrescription();
		ocrPage.uploadFile(filePath);
		ocrPage.waitForErrorToAppear();

		Assert.assertTrue(ocrPage.isErrorVisible(), "Error should be displayed for file too large");
		Assert.assertTrue(ocrPage.isErrorContains("10MB") || ocrPage.isErrorContains("large"),
				"Error should mention size limit");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_010_19 - Edge: Switch Tabs During Upload
	// ============================================================
	@Test(groups = { "edge-case" }, dependsOnMethods = { "TC_010_01_OCRPage_Loads" })
	public void TC_010_19_Edge_SwitchTabsDuringUpload() {
		String testCase = "TC_010_19_Edge_SwitchTabsDuringUpload";
		logInfo("TEST STARTED: " + testCase);

		

		String filePath = System.getProperty("user.dir") + "/src/test/resources/test-files/prescription.jpg";

		ocrPage.selectPrescription();
		ocrPage.uploadFile(filePath);

		// Wait a moment for upload to start
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}

		// Switch to history tab
		ocrPage.switchToHistoryTab();
		ocrPage.waitForHistoryToLoad();

		Assert.assertTrue(ocrPage.isHistoryTabActive(), "History tab should be active");

		// Switch back to scan tab
		ocrPage.switchToScanTab();
		ocrPage.waitForPageLoad();

		Assert.assertTrue(ocrPage.isScanTabActive(), "Scan tab should be active");
		Assert.assertTrue(ocrPage.isUploadDropzoneVisible(), "Upload dropzone should be visible");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_010_20 - Edge: Multiple Quick Uploads
	// ============================================================
	@Test(groups = { "edge-case" }, dependsOnMethods = { "TC_010_01_OCRPage_Loads" })
	public void TC_010_20_Edge_MultipleQuickUploads() {
		String testCase = "TC_010_20_Edge_MultipleQuickUploads";
		logInfo("TEST STARTED: " + testCase);

		

		String[] docTypes = { "prescription", "lab_report", "medical_report" };
		String[] fileNames = { "prescription.jpg", "lab_report.pdf", "medical_report.png" };

		for (int i = 0; i < docTypes.length; i++) {
			String filePath = System.getProperty("user.dir") + "/src/test/resources/test-files/" + fileNames[i];
			logInfo("Uploading " + docTypes[i] + ": " + filePath);

			ocrPage.selectDocumentType(docTypes[i]);
			ocrPage.uploadFile(filePath);
			ocrPage.waitForUploadToComplete();
			ocrPage.waitForResultToAppear();

			Assert.assertTrue(ocrPage.isResultViewVisible(), "Result should be visible for " + docTypes[i]);
			Assert.assertTrue(
					ocrPage.getResultTitle().toLowerCase()
							.contains(docTypes[i].equals("prescription") ? "prescription"
									: docTypes[i].equals("lab_report") ? "lab report" : "medical report"),
					"Result title should match document type");

			ocrPage.clickNewRegistry();
			ocrPage.waitForPageLoad();
		}

		logInfo("TEST PASSED: " + testCase);
	}
}
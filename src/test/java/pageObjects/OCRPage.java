package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class OCRPage {

	WebDriver driver;
	WebDriverWait wait;

	public OCRPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		PageFactory.initElements(driver, this);
	}

	// ================= HEADER =================
	@FindBy(css = "[data-testid='ocr-page']")
	WebElement ocrPage;

	@FindBy(css = "[data-testid='ocr-heading']")
	WebElement heading;

	// ================= TABS =================
	@FindBy(css = "[data-testid='ocr-tabs']")
	WebElement tabsContainer;

	@FindBy(css = "[data-testid='ocr-tab-scan']")
	WebElement tabScan;

	@FindBy(css = "[data-testid='ocr-tab-history']")
	WebElement tabHistory;

	@FindBy(css = "[data-testid='ocr-history-count']")
	WebElement historyCount;

	// ================= SCAN TAB =================
	@FindBy(css = "[data-testid='ocr-scan-tab']")
	WebElement scanTabContent;

	@FindBy(css = "[data-testid='ocr-upload-container']")
	WebElement uploadContainer;

	@FindBy(css = "[data-testid='ocr-info-banner']")
	WebElement infoBanner;

	// ================= DOCUMENT UPLOADER =================
	@FindBy(css = "[data-testid='document-uploader']")
	WebElement documentUploader;

	@FindBy(css = "[data-testid='doc-type-label']")
	WebElement docTypeLabel;

	@FindBy(css = "[data-testid='doc-types-container']")
	WebElement docTypesContainer;

	@FindBy(css = "[data-testid='upload-dropzone']")
	WebElement uploadDropzone;

	@FindBy(css = "[data-testid='upload-file-input']")
	WebElement fileInput;

	@FindBy(css = "[data-testid='upload-title']")
	WebElement uploadTitle;

	@FindBy(css = "[data-testid='upload-subtitle']")
	WebElement uploadSubtitle;

	@FindBy(css = "[data-testid='upload-formats']")
	WebElement uploadFormats;

	@FindBy(css = "[data-testid='upload-max-size']")
	WebElement uploadMaxSize;

	// ================= DOCUMENT TYPES =================
	@FindBy(css = "[data-testid='doc-type-prescription']")
	WebElement docTypePrescription;

	@FindBy(css = "[data-testid='doc-type-lab_report']")
	WebElement docTypeLabReport;

	@FindBy(css = "[data-testid='doc-type-medical_report']")
	WebElement docTypeMedicalReport;

	@FindBy(css = "[data-testid='doc-type-other']")
	WebElement docTypeOther;

	// ================= UPLOAD PROGRESS =================
	@FindBy(css = "[data-testid='upload-progress-overlay']")
	WebElement progressOverlay;

	@FindBy(css = "[data-testid='upload-progress-text']")
	WebElement progressText;

	@FindBy(css = "[data-testid='upload-progress-bar']")
	WebElement progressBar;

	@FindBy(css = "[data-testid='upload-progress-fill']")
	WebElement progressFill;

	@FindBy(css = "[data-testid='upload-progress-label']")
	WebElement progressLabel;

	// ================= UPLOADED FILE =================
	@FindBy(css = "[data-testid='uploaded-file-container']")
	WebElement uploadedFileContainer;

	@FindBy(css = "[data-testid='uploaded-file-name']")
	WebElement uploadedFileName;

	@FindBy(css = "[data-testid='uploaded-file-size']")
	WebElement uploadedFileSize;

	@FindBy(css = "[data-testid='uploaded-check']")
	WebElement uploadedCheck;

	@FindBy(css = "[data-testid='uploaded-clear-btn']")
	WebElement uploadedClearBtn;

	// ================= ERROR =================
	@FindBy(css = "[data-testid='upload-error']")
	WebElement uploadError;

	@FindBy(css = "[data-testid='upload-error-text']")
	WebElement uploadErrorText;

	// ================= RESULT VIEW =================
	@FindBy(css = "[data-testid='ocr-result-view']")
	WebElement resultView;

	@FindBy(css = "[data-testid='ocr-result-header']")
	WebElement resultHeader;

	@FindBy(css = "[data-testid='ocr-result-title']")
	WebElement resultTitle;

	@FindBy(css = "[data-testid='ocr-result-time']")
	WebElement resultTime;

	@FindBy(css = "[data-testid='ocr-result-filename']")
	WebElement resultFilename;

	@FindBy(css = "[data-testid='ocr-toggle-raw-text']")
	WebElement toggleRawTextBtn;

	@FindBy(css = "[data-testid='ocr-new-registry-btn']")
	WebElement newRegistryBtn;

	@FindBy(css = "[data-testid='ocr-delete-result-btn']")
	WebElement deleteResultBtn;

	@FindBy(css = "[data-testid='ocr-raw-text-container']")
	WebElement rawTextContainer;

	@FindBy(css = "[data-testid='ocr-raw-text-content']")
	WebElement rawTextContent;

	@FindBy(css = "[data-testid='ocr-summary-container']")
	WebElement summaryContainer;

	@FindBy(css = "[data-testid='ocr-summary-content']")
	WebElement summaryContent;

	@FindBy(css = "[data-testid='ocr-notes-container']")
	WebElement notesContainer;

	@FindBy(css = "[data-testid='ocr-notes-list']")
	WebElement notesList;

	@FindBy(css = "[data-testid='ocr-disclaimer']")
	WebElement disclaimer;

	// ================= HISTORY TAB =================
	@FindBy(css = "[data-testid='ocr-history-tab']")
	WebElement historyTabContent;

	@FindBy(css = "[data-testid='ocr-history-list']")
	WebElement historyList;

	@FindBy(css = "[data-testid='ocr-history-empty']")
	WebElement historyEmpty;

	@FindBy(css = "[data-testid='ocr-history-loading']")
	WebElement historyLoading;

	// ================= HEADER METHODS =================
	public boolean isPageVisible() {
		try {
			return ocrPage.isDisplayed();
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

	// ================= TAB METHODS =================
	public boolean isTabsVisible() {
		try {
			return tabsContainer.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void switchToScanTab() {
		tabScan.click();
		wait.until(ExpectedConditions.visibilityOf(scanTabContent));
	}

	public void switchToHistoryTab() {
		tabHistory.click();
		wait.until(ExpectedConditions.visibilityOf(historyTabContent));
	}

	public boolean isScanTabActive() {
		try {
			return tabScan.getAttribute("style").contains("var(--color-navy-900)");
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isHistoryTabActive() {
		try {
			return tabHistory.getAttribute("style").contains("var(--color-navy-900)");
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isScanTabContentVisible() {
		try {
			return scanTabContent.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isHistoryTabContentVisible() {
		try {
			return historyTabContent.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public int getHistoryCount() {
		try {
			String countText = historyCount.getText();
			return Integer.parseInt(countText);
		} catch (Exception e) {
			return 0;
		}
	}

	// ================= DOCUMENT TYPE METHODS =================
	public boolean isDocTypesVisible() {
		try {
			return docTypesContainer.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void selectDocumentType(String type) {
		try {
			WebElement typeBtn = driver.findElement(By.cssSelector("[data-testid='doc-type-" + type + "']"));
			typeBtn.click();
			wait.until(ExpectedConditions.attributeContains(typeBtn, "class", "bg-navy-900"));
		} catch (Exception e) {
		}
	}

	public void selectPrescription() {
		selectDocumentType("prescription");
	}

	public void selectLabReport() {
		selectDocumentType("lab_report");
	}

	public void selectMedicalReport() {
		selectDocumentType("medical_report");
	}

	public void selectOther() {
		selectDocumentType("other");
	}

	public boolean isDocTypeSelected(String type) {
		try {
			WebElement typeBtn = driver.findElement(By.cssSelector("[data-testid='doc-type-" + type + "']"));
			return typeBtn.getAttribute("class").contains("bg-navy-900");
		} catch (Exception e) {
			return false;
		}
	}

	public String getSelectedDocType() {
		if (isDocTypeSelected("prescription")) return "prescription";
		if (isDocTypeSelected("lab_report")) return "lab_report";
		if (isDocTypeSelected("medical_report")) return "medical_report";
		if (isDocTypeSelected("other")) return "other";
		return "";
	}

	// ================= UPLOAD METHODS =================
	public boolean isUploadDropzoneVisible() {
		try {
			return uploadDropzone.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void uploadFile(String filePath) {
		fileInput.sendKeys(filePath);
	}

	public void clickUploadDropzone() {
		uploadDropzone.click();
	}

	public String getUploadTitle() {
		try {
			return uploadTitle.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public boolean isUploadingInProgress() {
		try {
			return progressOverlay.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getProgressText() {
		try {
			return progressText.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public int getUploadProgress() {
		try {
			String text = getProgressText();
			if (text.contains("%")) {
				String[] parts = text.split("%");
				return Integer.parseInt(parts[0].replaceAll("[^0-9]", ""));
			}
			return 0;
		} catch (Exception e) {
			return 0;
		}
	}

	public boolean isUploadComplete() {
		try {
			return uploadedFileContainer.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getUploadedFileName() {
		try {
			return uploadedFileName.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public String getUploadedFileSize() {
		try {
			return uploadedFileSize.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public void clearUploadedFile() {
		try {
			uploadedClearBtn.click();
			wait.until(ExpectedConditions.invisibilityOf(uploadedFileContainer));
		} catch (Exception e) {
		}
	}

	public boolean isErrorVisible() {
		try {
			return uploadError.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getErrorMessage() {
		try {
			return uploadErrorText.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public boolean isErrorContains(String text) {
		return getErrorMessage().toLowerCase().contains(text.toLowerCase());
	}

	// ================= RESULT VIEW METHODS =================
	public boolean isResultViewVisible() {
		try {
			return resultView.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getResultTitle() {
		try {
			return resultTitle.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public String getResultFilename() {
		try {
			return resultFilename.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public void clickToggleRawText() {
		toggleRawTextBtn.click();
	}

	public boolean isRawTextVisible() {
		try {
			return rawTextContainer.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getRawText() {
		try {
			return rawTextContent.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public String getSummary() {
		try {
			return summaryContent.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public int getNotesCount() {
		try {
			List<WebElement> notes = driver.findElements(By.cssSelector("[data-testid*='ocr-note-']"));
			return notes.size();
		} catch (Exception e) {
			return 0;
		}
	}

	public String getNote(int index) {
		try {
			WebElement note = driver.findElement(By.cssSelector("[data-testid='ocr-note-" + index + "']"));
			return note.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public void clickNewRegistry() {
		newRegistryBtn.click();
	}

	public void clickDeleteResult() {
		deleteResultBtn.click();
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

	// ================= HISTORY METHODS =================
	public boolean isHistoryListVisible() {
		try {
			return historyList.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isHistoryEmptyVisible() {
		try {
			return historyEmpty.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public int getHistoryItemsCount() {
		try {
			List<WebElement> items = driver.findElements(By.cssSelector("[data-testid*='ocr-history-item-']"));
			return items.size();
		} catch (Exception e) {
			return 0;
		}
	}

	public void clickHistoryItem(int index) {
		try {
			WebElement item = driver.findElement(By.cssSelector("[data-testid='ocr-history-item-" + index + "']"));
			item.click();
		} catch (Exception e) {
		}
	}

	public String getHistoryItemLabel(int index) {
		try {
			WebElement label = driver.findElement(By.cssSelector("[data-testid='ocr-history-label-" + index + "']"));
			return label.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public String getHistoryItemFilename(int index) {
		try {
			WebElement filename = driver.findElement(By.cssSelector("[data-testid='ocr-history-filename-" + index + "']"));
			return filename.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public void deleteHistoryItem(int index) {
		try {
			WebElement deleteBtn = driver.findElement(By.cssSelector("[data-testid='ocr-history-delete-" + index + "']"));
			deleteBtn.click();
		} catch (Exception e) {
		}
	}

	public boolean isHistoryLoadingVisible() {
		try {
			return historyLoading.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// ================= WAIT METHODS =================
	public void waitForPageLoad() {
		wait.until(ExpectedConditions.visibilityOf(heading));
	}

	public void waitForUploadToComplete() {
		try {
			wait.until(ExpectedConditions.invisibilityOf(progressOverlay));
		} catch (Exception e) {
		}
	}

	public void waitForResultToAppear() {
		wait.until(ExpectedConditions.visibilityOf(resultView));
	}

	public void waitForHistoryToLoad() {
		try {
			wait.until(ExpectedConditions.invisibilityOf(historyLoading));
		} catch (Exception e) {
		}
	}

	public void waitForErrorToAppear() {
		wait.until(ExpectedConditions.visibilityOf(uploadError));
	}

	public void waitForUploadProgressToFinish() {
		try {
			int previousProgress = -1;
			for (int i = 0; i < 30; i++) {
				if (!isUploadingInProgress()) break;
				int currentProgress = getUploadProgress();
				if (currentProgress == previousProgress) break;
				previousProgress = currentProgress;
				Thread.sleep(1000);
			}
			Thread.sleep(2000);
		} catch (Exception e) {
		}
	}

	// ================= VERIFICATION METHODS =================
	public boolean verifyAllDocTypesPresent() {
		return docTypePrescription.isDisplayed() &&
			   docTypeLabReport.isDisplayed() &&
			   docTypeMedicalReport.isDisplayed() &&
			   docTypeOther.isDisplayed();
	}

	public boolean verifyUploadFlow() {
		return isUploadDropzoneVisible() &&
			   isDocTypesVisible() &&
			   fileInput.isDisplayed();
	}

	public boolean verifyResultContent() {
		return isResultViewVisible() &&
			   !getSummary().isEmpty() &&
			   getNotesCount() > 0 &&
			   isDisclaimerVisible();
	}

	// ================= COMPLETE FLOW METHODS =================
	public void completeFullUploadFlow(String filePath, String docType) {
		selectDocumentType(docType);
		uploadFile(filePath);
		waitForUploadToComplete();
		waitForResultToAppear();
	}

	public void completeFullUploadFlowWithPrescription(String filePath) {
		completeFullUploadFlow(filePath, "prescription");
	}

	public void completeFullUploadFlowWithLabReport(String filePath) {
		completeFullUploadFlow(filePath, "lab_report");
	}

	public void completeFullUploadFlowWithMedicalReport(String filePath) {
		completeFullUploadFlow(filePath, "medical_report");
	}

	public void completeFullUploadFlowWithOther(String filePath) {
		completeFullUploadFlow(filePath, "other");
	}

	public void verifyUploadResult(String expectedFileName) {
		waitForResultToAppear();
		assert isResultViewVisible() : "Result view should be visible";
		assert getResultFilename().contains(expectedFileName) : 
			"Expected filename: " + expectedFileName + " but got: " + getResultFilename();
		assert !getSummary().isEmpty() : "Summary should not be empty";
		assert getNotesCount() > 0 : "Should have at least one note";
		assert isDisclaimerVisible() : "Disclaimer should be visible";
	}

	public void verifyHistoryItemExists(int index) {
		assert getHistoryItemsCount() > index : "History item at index " + index + " should exist";
		assert !getHistoryItemLabel(index).isEmpty() : "History item label should not be empty";
	}
}
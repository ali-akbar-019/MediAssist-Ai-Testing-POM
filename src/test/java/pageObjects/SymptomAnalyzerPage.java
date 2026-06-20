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

public class SymptomAnalyzerPage {

	WebDriver driver;
	WebDriverWait wait;

	public SymptomAnalyzerPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);
	}

	// ================= HEADER =================
	@FindBy(css = "[data-testid='analyzer-heading']")
	WebElement heading;

	@FindBy(css = "[data-testid='symptom-form']")
	WebElement symptomForm;

	// ================= STEP INDICATORS =================
	@FindBy(css = "[data-testid='step-indicator']")
	WebElement stepIndicator;

	@FindBy(css = "[data-testid='step-1']")
	WebElement step1Indicator;

	@FindBy(css = "[data-testid='step-2']")
	WebElement step2Indicator;

	@FindBy(css = "[data-testid='step-3']")
	WebElement step3Indicator;

	@FindBy(css = "[data-testid='step-4']")
	WebElement step4Indicator;

	// ================= STEP 1: BODY PART =================
	@FindBy(css = "[data-testid='step1-content']")
	WebElement step1Content;

	@FindBy(css = "[data-testid='body-map-container']")
	WebElement bodyMapContainer;

	@FindBy(css = "[data-testid='body-view-front']")
	WebElement viewFrontBtn;

	@FindBy(css = "[data-testid='body-view-back']")
	WebElement viewBackBtn;

	@FindBy(css = "[data-testid='body-flip']")
	WebElement flipBtn;

	@FindBy(css = "[data-testid='selected-parts-container']")
	WebElement selectedPartsContainer;

	@FindBy(css = "[data-testid='selected-parts-list']")
	WebElement selectedPartsList;

	@FindBy(css = "[data-testid='no-parts-selected']")
	WebElement noPartsSelectedMsg;

	@FindBy(css = "[data-testid='clear-all-parts']")
	WebElement clearAllBtn;

	// ================= STEP 2: SYMPTOMS =================
	@FindBy(css = "[data-testid='step2-content']")
	WebElement step2Content;

	@FindBy(css = "[data-testid='symptom-input']")
	WebElement symptomInput;

	@FindBy(css = "[data-testid='symptom-add-btn']")
	WebElement symptomAddBtn;

	@FindBy(css = "[data-testid='symptom-list']")
	WebElement symptomList;

	@FindBy(css = "[data-testid='symptom-suggestions-container']")
	WebElement suggestionsContainer;

	// ================= STEP 3: PAIN DETAILS =================
	@FindBy(css = "[data-testid='step3-content']")
	WebElement step3Content;

	@FindBy(css = "[data-testid='severity-slider']")
	WebElement severitySlider;

	@FindBy(css = "[data-testid='severity-range']")
	WebElement severityRange;

	@FindBy(css = "[data-testid='duration-input']")
	WebElement durationInput;

	@FindBy(css = "[data-testid='duration-unit']")
	WebElement durationUnitDropdown;

	@FindBy(css = "[data-testid='clinical-notes']")
	WebElement clinicalNotes;

	// ================= STEP 4: ANALYSIS =================
	@FindBy(css = "[data-testid='step4-content']")
	WebElement step4Content;

	@FindBy(css = "[data-testid='analyzing-state']")
	WebElement analyzingState;

	@FindBy(css = "[data-testid='analysis-result']")
	WebElement analysisResult;

	@FindBy(css = "[data-testid='analysis-severity']")
	WebElement analysisSeverity;

	@FindBy(css = "[data-testid='analyzer-reset']")
	WebElement resetBtn;

	// ================= NAVIGATION =================
	@FindBy(css = "[data-testid='navigation-controls']")
	WebElement navigationControls;

	@FindBy(css = "[data-testid='analyzer-back']")
	WebElement backBtn;

	@FindBy(css = "[data-testid='analyzer-continue']")
	WebElement continueBtn;

	@FindBy(css = "[data-testid='analyzer-submit']")
	WebElement submitBtn;

	// ================= HEADER METHODS =================
	public boolean isHeadingVisible() {
		try {
			return heading.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getHeadingText() {
		return heading.getText();
	}

	// ================= STEP INDICATOR METHODS =================
	public int getCurrentStep() {
		try {
			if (step1Indicator.getAttribute("class").contains("bg-navy-900")) return 1;
			if (step2Indicator.getAttribute("class").contains("bg-navy-900")) return 2;
			if (step3Indicator.getAttribute("class").contains("bg-navy-900")) return 3;
			if (step4Indicator.getAttribute("class").contains("bg-navy-900")) return 4;
		} catch (Exception e) {}
		return 0;
	}

	public boolean isStepActive(int step) {
		try {
			switch (step) {
				case 1: return step1Indicator.getAttribute("class").contains("bg-navy-900");
				case 2: return step2Indicator.getAttribute("class").contains("bg-navy-900");
				case 3: return step3Indicator.getAttribute("class").contains("bg-navy-900");
				case 4: return step4Indicator.getAttribute("class").contains("bg-navy-900");
				default: return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isStepCompleted(int step) {
		try {
			switch (step) {
				case 1: return step1Indicator.getAttribute("class").contains("bg-emerald-500");
				case 2: return step2Indicator.getAttribute("class").contains("bg-emerald-500");
				case 3: return step3Indicator.getAttribute("class").contains("bg-emerald-500");
				case 4: return step4Indicator.getAttribute("class").contains("bg-emerald-500");
				default: return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	// ================= STEP 1: BODY PART METHODS =================
	public boolean isStep1Displayed() {
		try {
			return step1Content.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void clickBodyPart(String partName) {
		try {
			WebElement part = driver.findElement(By.cssSelector("[data-testid='body-part-" + partName + "']"));
			part.click();
		} catch (Exception e) {
			// Try partial match
			List<WebElement> parts = driver.findElements(By.cssSelector("[data-testid*='body-part-']"));
			for (WebElement p : parts) {
				if (p.getAttribute("data-testid").contains(partName)) {
					p.click();
					return;
				}
			}
		}
	}

	public boolean isBodyPartSelected(String partName) {
		try {
			WebElement part = driver.findElement(By.cssSelector("[data-testid='body-part-" + partName + "']"));
			return part.getAttribute("fill").equals("#10b981");
		} catch (Exception e) {
			return false;
		}
	}

	public void selectView(String view) {
		if (view.equalsIgnoreCase("front") || view.equalsIgnoreCase("anterior")) {
			viewFrontBtn.click();
		} else {
			viewBackBtn.click();
		}
	}

	public void flipBodyView() {
		flipBtn.click();
	}

	public int getSelectedBodyPartCount() {
		try {
			List<WebElement> selected = driver.findElements(By.cssSelector("[data-testid*='selected-part-']"));
			return selected.size();
		} catch (Exception e) {
			return 0;
		}
	}

	public boolean hasSelectedBodyPart(String partName) {
		try {
			WebElement part = driver.findElement(By.cssSelector("[data-testid='selected-part-" + partName + "']"));
			return part.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void removeSelectedBodyPart(String partId) {
		try {
			WebElement removeBtn = driver.findElement(By.cssSelector("[data-testid='remove-part-" + partId + "']"));
			removeBtn.click();
		} catch (Exception e) {}
	}

	public void clearAllBodyParts() {
		clearAllBtn.click();
	}

	public boolean isNoPartsMessageDisplayed() {
		try {
			return noPartsSelectedMsg.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// ================= STEP 2: SYMPTOM METHODS =================
	public boolean isStep2Displayed() {
		try {
			return step2Content.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void enterSymptom(String symptom) {
		symptomInput.clear();
		symptomInput.sendKeys(symptom);
	}

	public void addSymptom(String symptom) {
		enterSymptom(symptom);
		symptomAddBtn.click();
	}

	public void addSymptomWithEnter(String symptom) {
		symptomInput.clear();
		symptomInput.sendKeys(symptom);
		symptomInput.submit();
	}

	public void addSymptomViaSuggestion(String symptomName) {
		try {
			WebElement suggestion = driver.findElement(By.cssSelector("[data-testid='symptom-suggestion-" + symptomName + "']"));
			suggestion.click();
		} catch (Exception e) {}
	}

	public boolean hasAddedSymptom(String symptom) {
		try {
			WebElement tag = driver.findElement(By.cssSelector("[data-testid='symptom-tag-" + symptom + "']"));
			return tag.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public int getAddedSymptomsCount() {
		try {
			List<WebElement> tags = driver.findElements(By.cssSelector("[data-testid*='symptom-tag-']"));
			return tags.size();
		} catch (Exception e) {
			return 0;
		}
	}

	public void removeSymptom(String symptom) {
		try {
			WebElement removeBtn = driver.findElement(By.cssSelector("[data-testid='symptom-tag-remove-" + symptom + "']"));
			removeBtn.click();
		} catch (Exception e) {}
	}

	public boolean isSymptomSuggestionsVisible() {
		try {
			return suggestionsContainer.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// ================= STEP 3: PAIN DETAILS METHODS =================
	public boolean isStep3Displayed() {
		try {
			return step3Content.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void setSeverity(int value) {
		try {
			((org.openqa.selenium.JavascriptExecutor) driver)
				.executeScript("arguments[0].value = '" + value + "';", severityRange);
			// Trigger change event
			((org.openqa.selenium.JavascriptExecutor) driver)
				.executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", severityRange);
		} catch (Exception e) {}
	}

	public int getSeverityValue() {
		try {
			return Integer.parseInt(severityRange.getAttribute("value"));
		} catch (Exception e) {
			return 0;
		}
	}

	public void clickSeverityLabel(int score) {
		try {
			WebElement label = driver.findElement(By.cssSelector("[data-testid='severity-label-" + score + "']"));
			label.click();
		} catch (Exception e) {}
	}

	public void clickSeverityBar(int score) {
		try {
			WebElement bar = driver.findElement(By.cssSelector("[data-testid='severity-bar-" + score + "']"));
			bar.click();
		} catch (Exception e) {}
	}

	public void selectPainType(String painType) {
		try {
			WebElement painBtn = driver.findElement(By.cssSelector("[data-testid='pain-type-" + painType + "']"));
			painBtn.click();
		} catch (Exception e) {}
	}

	public boolean isPainTypeSelected(String painType) {
		try {
			WebElement painBtn = driver.findElement(By.cssSelector("[data-testid='pain-type-" + painType + "']"));
			return painBtn.getAttribute("class").contains("bg-navy-900");
		} catch (Exception e) {
			return false;
		}
	}

	public void setDuration(String value) {
		durationInput.clear();
		durationInput.sendKeys(value);
	}

	public String getDurationValue() {
		return durationInput.getAttribute("value");
	}

	public void selectDurationUnit(String unit) {
		durationUnitDropdown.click();
		WebElement option = driver.findElement(By.cssSelector("[data-testid='duration-option-" + unit + "']"));
		option.click();
	}

	public String getSelectedDurationUnit() {
		return durationUnitDropdown.getText();
	}

	public void selectWorseAt(String timing) {
		try {
			WebElement option = driver.findElement(By.cssSelector("[data-testid='worse-at-" + timing + "']"));
			option.click();
		} catch (Exception e) {}
	}

	public boolean isWorseAtSelected(String timing) {
		try {
			WebElement option = driver.findElement(By.cssSelector("[data-testid='worse-at-" + timing + "']"));
			return option.getAttribute("class").contains("bg-navy-900");
		} catch (Exception e) {
			return false;
		}
	}

	public void addClinicalNotes(String notes) {
		clinicalNotes.clear();
		clinicalNotes.sendKeys(notes);
	}

	public String getClinicalNotes() {
		return clinicalNotes.getText();
	}

	// ================= STEP 4: ANALYSIS METHODS =================
	public boolean isStep4Displayed() {
		try {
			return step4Content.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isAnalyzing() {
		try {
			return analyzingState.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isAnalysisResultDisplayed() {
		try {
			return analysisResult.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getAnalysisSeverity() {
		try {
			return analysisSeverity.getText().toLowerCase().trim();
		} catch (Exception e) {
			return "";
		}
	}

	public String getAnalysisRecommendation() {
		try {
			WebElement rec = analysisSeverity.findElement(By.tagName("p"));
			return rec.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public int getPossibleConditionsCount() {
		try {
			List<WebElement> conditions = driver.findElements(By.cssSelector("[data-testid*='condition-']"));
			return conditions.size();
		} catch (Exception e) {
			return 0;
		}
	}

	public String getConditionName(int index) {
		try {
			WebElement condition = driver.findElement(By.cssSelector("[data-testid='condition-" + index + "']"));
			return condition.findElement(By.className("text-sm")).getText();
		} catch (Exception e) {
			return "";
		}
	}

	public void resetAnalyzer() {
		resetBtn.click();
	}

	// ================= NAVIGATION METHODS =================
	public void clickBack() {
		backBtn.click();
	}

	public void clickContinue() {
		continueBtn.click();
	}

	public void clickSubmit() {
		submitBtn.click();
	}

	public boolean isContinueEnabled() {
		return continueBtn.isEnabled();
	}

	public boolean isSubmitEnabled() {
		return submitBtn.isEnabled();
	}

	public boolean isBackVisible() {
		try {
			return backBtn.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// ================= COMPLETE FLOW METHODS =================
	public void completeStep1_SelectBodyPart(String partName) {
		selectView("front");
		clickBodyPart(partName);
		clickContinue();
	}

	public void completeStep2_AddSymptom(String symptom) {
		addSymptom(symptom);
		clickContinue();
	}

	public void completeStep3_PainDetails(String painType, String duration, String unit, String worseAt) {
		selectPainType(painType);
		setDuration(duration);
		selectDurationUnit(unit);
		selectWorseAt(worseAt);
		clickSubmit();
	}

	public void completeFullAnalysis(String bodyPart, String symptom, String painType, String duration, String unit, String worseAt) {
		completeStep1_SelectBodyPart(bodyPart);
		
		wait.until(ExpectedConditions.visibilityOf(step2Content));
		completeStep2_AddSymptom(symptom);
		
		wait.until(ExpectedConditions.visibilityOf(step3Content));
		completeStep3_PainDetails(painType, duration, unit, worseAt);
	}

	public void waitForAnalysisComplete() {
		wait.until(ExpectedConditions.visibilityOf(analysisResult));
	}
}
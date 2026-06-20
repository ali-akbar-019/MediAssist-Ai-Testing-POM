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

public class ChatPage {

	WebDriver driver;
	WebDriverWait wait;

	public ChatPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);
	}

	// ================= HEADER =================
	@FindBy(css = "[data-testid='chat-heading']")
	WebElement heading;

	@FindBy(css = "[data-testid='chat-page']")
	WebElement chatPage;

	@FindBy(css = "[data-testid='chat-container']")
	WebElement chatContainer;

	@FindBy(css = "[data-testid='chat-disclaimer']")
	WebElement disclaimer;

	// ================= CHAT WINDOW =================
	@FindBy(css = "[data-testid='chat-window']")
	WebElement chatWindow;

	@FindBy(css = "[data-testid='chat-sidebar']")
	WebElement chatSidebar;

	@FindBy(css = "[data-testid='chat-main-area']")
	WebElement chatMainArea;

	// ================= SESSIONS =================
	@FindBy(css = "[data-testid='chat-new-session-btn']")
	WebElement newSessionBtn;

	@FindBy(css = "[data-testid='chat-session-list']")
	WebElement sessionList;

	@FindBy(css = "[data-testid='chat-no-sessions']")
	WebElement noSessionsMsg;

	@FindBy(css = "[data-testid='chat-sessions-loading']")
	WebElement sessionsLoading;

	// ================= CHAT HEADER =================
	@FindBy(css = "[data-testid='chat-header']")
	WebElement chatHeader;

	@FindBy(css = "[data-testid='chat-header-title']")
	WebElement chatHeaderTitle;

	@FindBy(css = "[data-testid='chat-mobile-new-chat']")
	WebElement mobileNewChatBtn;

	// ================= MESSAGES =================
	@FindBy(css = "[data-testid='chat-messages-area']")
	WebElement messagesArea;

	@FindBy(css = "[data-testid='chat-welcome']")
	WebElement welcomeMessage;

	@FindBy(css = "[data-testid='chat-suggestions']")
	WebElement suggestionsContainer;

	@FindBy(css = "[data-testid='chat-sending-indicator']")
	WebElement sendingIndicator;

	@FindBy(css = "[data-testid='chat-error']")
	WebElement errorMessage;

	// ================= INPUT =================
	@FindBy(css = "[data-testid='chat-input-area']")
	WebElement inputArea;

	@FindBy(css = "[data-testid='chat-textarea']")
	WebElement textarea;

	@FindBy(css = "[data-testid='chat-send-btn']")
	WebElement sendBtn;

	@FindBy(css = "[data-testid='chat-voice-btn']")
	WebElement voiceBtn;

	@FindBy(css = "[data-testid='chat-quick-suggestions']")
	WebElement quickSuggestions;

	@FindBy(css = "[data-testid='chat-protocol-info']")
	WebElement protocolInfo;

	// ================= HEADER METHODS =================
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

	public boolean isDisclaimerVisible() {
		try {
			return disclaimer.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// ================= CHAT WINDOW METHODS =================
	public boolean isChatWindowVisible() {
		try {
			return chatWindow.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isChatSidebarVisible() {
		try {
			return chatSidebar.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isChatMainAreaVisible() {
		try {
			return chatMainArea.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isChatHeaderVisible() {
		try {
			return chatHeader.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getChatHeaderTitle() {
		try {
			return chatHeaderTitle.getText();
		} catch (Exception e) {
			return "";
		}
	}

	// ================= SESSION METHODS =================
	public void clickNewSession() {
		newSessionBtn.click();
	}

	public void clickMobileNewChat() {
		try {
			mobileNewChatBtn.click();
		} catch (Exception e) {
		}
	}

	public int getSessionCount() {
		try {
			List<WebElement> sessions = driver.findElements(By.cssSelector("[data-testid*='chat-session-']"));
			return sessions.size();
		} catch (Exception e) {
			return 0;
		}
	}

	public void selectSession(int index) {
		try {
			List<WebElement> sessions = driver.findElements(By.cssSelector("[data-testid*='chat-session-']"));
			if (index >= 0 && index < sessions.size()) {
				sessions.get(index).click();
			}
		} catch (Exception e) {
		}
	}

	public void selectSessionById(String sessionId) {
		try {
			WebElement session = driver.findElement(By.cssSelector("[data-testid='chat-session-" + sessionId + "']"));
			session.click();
		} catch (Exception e) {
		}
	}

	public void deleteSession(int index) {
		try {
			List<WebElement> deleteBtns = driver.findElements(By.cssSelector("[data-testid*='chat-delete-session-']"));
			if (index >= 0 && index < deleteBtns.size()) {
				deleteBtns.get(index).click();
			}
		} catch (Exception e) {
		}
	}

	public void deleteSessionById(String sessionId) {
		try {
			WebElement deleteBtn = driver.findElement(By.cssSelector("[data-testid='chat-delete-session-" + sessionId + "']"));
			deleteBtn.click();
		} catch (Exception e) {
		}
	}

	public boolean isNoSessionsMessageVisible() {
		try {
			return noSessionsMsg.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isSessionsLoadingVisible() {
		try {
			return sessionsLoading.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isSessionSelected(int index) {
		try {
			List<WebElement> sessions = driver.findElements(By.cssSelector("[data-testid*='chat-session-']"));
			if (index >= 0 && index < sessions.size()) {
				return sessions.get(index).getAttribute("class").contains("bg-navy-900");
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	// ================= MESSAGE METHODS =================
	public void enterMessage(String message) {
		textarea.clear();
		textarea.sendKeys(message);
	}

	public void sendMessage(String message) {
		enterMessage(message);
		sendBtn.click();
	}

	public void sendMessageViaEnter(String message) {
		textarea.clear();
		textarea.sendKeys(message);
		textarea.submit();
	}

	public void clickQuickSuggestion(String suggestion) {
		try {
			WebElement suggestionBtn = driver.findElement(
				By.cssSelector("[data-testid='chat-suggestion-" + suggestion.replace(/\s+/g, '-').toLowerCase() + "']")
			);
			suggestionBtn.click();
		} catch (Exception e) {
			// Fallback: find by text
			List<WebElement> suggestions = driver.findElements(By.xpath("//button[contains(text(), '" + suggestion + "')]"));
			if (!suggestions.isEmpty()) {
				suggestions.get(0).click();
			}
		}
	}

	public void clickSuggestionByIndex(int index) {
		try {
			List<WebElement> suggestions = driver.findElements(By.cssSelector("[data-testid*='chat-suggestion-']"));
			if (index >= 0 && index < suggestions.size()) {
				suggestions.get(index).click();
			}
		} catch (Exception e) {
		}
	}

	public int getMessageCount() {
		try {
			List<WebElement> messages = driver.findElements(By.cssSelector("[data-testid*='chat-message-']"));
			return messages.size();
		} catch (Exception e) {
			return 0;
		}
	}

	public boolean hasMessage(String text) {
		try {
			List<WebElement> messages = driver.findElements(By.xpath("//*[contains(text(), '" + text + "')]"));
			return !messages.isEmpty();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean hasUserMessage(String text) {
		try {
			List<WebElement> messages = driver.findElements(By.cssSelector("[data-testid*='chat-bubble-user-']"));
			for (WebElement msg : messages) {
				if (msg.getText().contains(text)) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean hasAIMessage(String text) {
		try {
			List<WebElement> messages = driver.findElements(By.cssSelector("[data-testid*='chat-bubble-ai-']"));
			for (WebElement msg : messages) {
				if (msg.getText().contains(text)) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean hasAIResponse() {
		try {
			List<WebElement> aiMessages = driver.findElements(By.cssSelector("[data-testid*='chat-bubble-ai-']"));
			return !aiMessages.isEmpty();
		} catch (Exception e) {
			return false;
		}
	}

	public String getLastUserMessage() {
		try {
			List<WebElement> userMessages = driver.findElements(By.cssSelector("[data-testid*='chat-bubble-user-']"));
			if (!userMessages.isEmpty()) {
				return userMessages.get(userMessages.size() - 1).getText();
			}
			return "";
		} catch (Exception e) {
			return "";
		}
	}

	public String getLastAIMessage() {
		try {
			List<WebElement> aiMessages = driver.findElements(By.cssSelector("[data-testid*='chat-bubble-ai-']"));
			if (!aiMessages.isEmpty()) {
				return aiMessages.get(aiMessages.size() - 1).getText();
			}
			return "";
		} catch (Exception e) {
			return "";
		}
	}

	public String getMessageContent(int index) {
		try {
			WebElement message = driver.findElement(By.cssSelector("[data-testid='chat-message-" + index + "']"));
			return message.getText();
		} catch (Exception e) {
			return "";
		}
	}

	// ================= INPUT METHODS =================
	public boolean isTextareaVisible() {
		try {
			return textarea.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void clearTextarea() {
		textarea.clear();
	}

	public String getTextareaValue() {
		return textarea.getAttribute("value");
	}

	public boolean isSendButtonVisible() {
		try {
			return sendBtn.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isSendButtonEnabled() {
		return sendBtn.isEnabled();
	}

	public boolean isSendButtonDisabled() {
		return !sendBtn.isEnabled();
	}

	public boolean isVoiceButtonVisible() {
		try {
			return voiceBtn.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void clickVoiceButton() {
		voiceBtn.click();
	}

	public boolean isQuickSuggestionsVisible() {
		try {
			return quickSuggestions.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public int getQuickSuggestionsCount() {
		try {
			List<WebElement> suggestions = driver.findElements(By.cssSelector("[data-testid*='chat-suggestion-']"));
			return suggestions.size();
		} catch (Exception e) {
			return 0;
		}
	}

	// ================= WAIT METHODS =================
	public void waitForMessagesToLoad() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
	}

	public void waitForAIResponse() {
		try {
			wait.until(ExpectedConditions.visibilityOf(sendingIndicator));
			Thread.sleep(1000);
			wait.until(ExpectedConditions.invisibilityOf(sendingIndicator));
		} catch (Exception e) {
		}
	}

	public void waitForSessionsToLoad() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
	}

	public void waitForChatWindow() {
		wait.until(ExpectedConditions.visibilityOf(chatWindow));
	}

	public void waitForWelcomeMessage() {
		try {
			wait.until(ExpectedConditions.visibilityOf(welcomeMessage));
		} catch (Exception e) {
		}
	}

	// ================= VERIFICATION METHODS =================
	public boolean isWelcomeMessageVisible() {
		try {
			return welcomeMessage.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isSendingIndicatorVisible() {
		try {
			return sendingIndicator.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isErrorVisible() {
		try {
			return errorMessage.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getErrorMessage() {
		try {
			return errorMessage.getText();
		} catch (Exception e) {
			return "";
		}
	}

	public boolean isProtocolInfoVisible() {
		try {
			return protocolInfo.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// ================= COMPLETE FLOW METHODS =================
	public void completeFullChatFlow(String message) {
		sendMessage(message);
		waitForAIResponse();
	}

	public void completeFullChatFlowWithSuggestion(String suggestion) {
		clickQuickSuggestion(suggestion);
		waitForAIResponse();
	}

	public void verifyChatResponse(String expectedMessage) {
		waitForMessagesToLoad();
		assert hasUserMessage(expectedMessage) : "User message not found: " + expectedMessage;
		assert hasAIResponse() : "AI response not received";
	}

	public void createAndVerifyNewSession() {
		int beforeCount = getSessionCount();
		clickNewSession();
		waitForSessionsToLoad();
		int afterCount = getSessionCount();
		assert afterCount > beforeCount : "New session was not created";
	}

	public void deleteAndVerifySession(int index) {
		int beforeCount = getSessionCount();
		deleteSession(index);
		waitForSessionsToLoad();
		int afterCount = getSessionCount();
		assert afterCount < beforeCount : "Session was not deleted";
	}
}
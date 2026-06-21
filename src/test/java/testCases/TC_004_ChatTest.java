package testCases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.ChatPage;

public class TC_004_ChatTest extends BaseClass {

	ChatPage cp;

	@BeforeMethod
public void loginFirst() {
    login();
    driver.get(p.getProperty("appURL") + "/chat");
    cp = new ChatPage(driver);
    waitForUrlContains("chat", 10);
}
	// ============================================================
	// TC_004_01 - Page Load Test
	// ============================================================
	@Test(groups = { "smoke", "regression", "edge-case" })
	public void TC_004_01_ChatPage_Loads() {
		String testCase = "TC_004_01_ChatPage_Loads";
		logInfo("TEST STARTED: " + testCase);

		
		

		Assert.assertTrue(waitForUrlContains("chat", 5), "Chat route not reached");
		Assert.assertTrue(cp.isHeadingVisible(), "Chat heading not visible");
		Assert.assertTrue(cp.isChatWindowVisible(), "Chat window not visible");
		Assert.assertTrue(cp.isTextareaVisible(), "Textarea not visible");
		Assert.assertTrue(cp.isSendButtonVisible(), "Send button not visible");
		Assert.assertTrue(cp.isDisclaimerVisible(), "Disclaimer not visible");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_004_02 - Send Message
	// ============================================================
	@Test(groups = { "smoke", "regression" }, dependsOnMethods = { "TC_004_01_ChatPage_Loads" })
	public void TC_004_02_SendMessage() {
		String testCase = "TC_004_02_SendMessage";
		logInfo("TEST STARTED: " + testCase);

		
		cp.waitForChatWindow();

		int beforeCount = cp.getMessageCount();
		logInfo("Messages before: " + beforeCount);

		cp.sendMessage("I have a headache");
		cp.waitForAIResponse();

		int afterCount = cp.getMessageCount();
		logInfo("Messages after: " + afterCount);

		Assert.assertTrue(afterCount > beforeCount, "Message count should increase");
		Assert.assertTrue(cp.hasUserMessage("headache"), "User message should be visible");
		Assert.assertTrue(cp.hasAIResponse(), "AI response should be received");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_004_03 - Send Message With Enter Key
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_004_01_ChatPage_Loads" })
	public void TC_004_03_SendMessage_WithEnterKey() {
		String testCase = "TC_004_03_SendMessage_WithEnterKey";
		logInfo("TEST STARTED: " + testCase);

		
		cp.waitForChatWindow();

		int beforeCount = cp.getMessageCount();
		cp.sendMessageViaEnter("My stomach hurts");
		cp.waitForAIResponse();

		int afterCount = cp.getMessageCount();
		Assert.assertTrue(afterCount > beforeCount, "Message count should increase");
		Assert.assertTrue(cp.hasUserMessage("stomach"), "User message should be visible");
		Assert.assertTrue(cp.hasAIResponse(), "AI response should be received");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_004_04 - Send Empty Message (Should Fail)
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_004_01_ChatPage_Loads" })
	public void TC_004_04_SendEmptyMessage_ShouldFail() {
		String testCase = "TC_004_04_SendEmptyMessage_ShouldFail";
		logInfo("TEST STARTED: " + testCase);

		
		cp.waitForChatWindow();

		cp.clearTextarea();
		Assert.assertTrue(cp.isSendButtonDisabled(), "Send button should be disabled for empty message");

		int beforeCount = cp.getMessageCount();
		cp.clickSendButton();
		cp.waitForMessagesToLoad();

		int afterCount = cp.getMessageCount();
		Assert.assertEquals(afterCount, beforeCount, "Message count should not change for empty message");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_004_05 - Quick Suggestion Click
	// ============================================================
	@Test(groups = { "smoke", "regression" }, dependsOnMethods = { "TC_004_01_ChatPage_Loads" })
	public void TC_004_05_QuickSuggestion() {
		String testCase = "TC_004_05_QuickSuggestion";
		logInfo("TEST STARTED: " + testCase);

		
		cp.waitForChatWindow();

		Assert.assertTrue(cp.isQuickSuggestionsVisible(), "Quick suggestions should be visible");

		String suggestion = "I have a headache";
		cp.clickQuickSuggestion(suggestion);

		Assert.assertEquals(cp.getTextareaValue(), suggestion, "Textarea should be populated with suggestion");

		cp.sendMessageViaEnter(suggestion);
		cp.waitForAIResponse();

		Assert.assertTrue(cp.hasUserMessage("headache"), "User message should be visible");
		Assert.assertTrue(cp.hasAIResponse(), "AI response should be received");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_004_06 - Multiple Messages
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_004_02_SendMessage" })
	public void TC_004_06_MultipleMessages() {
		String testCase = "TC_004_06_MultipleMessages";
		logInfo("TEST STARTED: " + testCase);

		
		cp.waitForChatWindow();

		String[] messages = { "I have a headache", "My stomach hurts", "I feel feverish" };

		for (String msg : messages) {
			cp.sendMessage(msg);
			cp.waitForAIResponse();
			Assert.assertTrue(cp.hasUserMessage(msg), "Message should be visible: " + msg);
		}

		Assert.assertTrue(cp.getMessageCount() >= 6, "Should have at least 6 messages (3 user + 3 AI)");
		Assert.assertTrue(cp.hasAIResponse(), "AI responses should be present");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_004_07 - Create New Session
	// ============================================================
	@Test(groups = { "smoke", "regression" }, dependsOnMethods = { "TC_004_01_ChatPage_Loads" })
	public void TC_004_07_CreateNewSession() {
		String testCase = "TC_004_07_CreateNewSession";
		logInfo("TEST STARTED: " + testCase);

		
		cp.waitForChatWindow();

		int beforeCount = cp.getSessionCount();
		logInfo("Sessions before: " + beforeCount);

		cp.clickNewSession();
		cp.waitForSessionsToLoad();

		int afterCount = cp.getSessionCount();
		logInfo("Sessions after: " + afterCount);

		Assert.assertTrue(afterCount > beforeCount, "New session should be created");
		Assert.assertTrue(cp.isWelcomeMessageVisible(), "Welcome message should be visible for new session");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_004_08 - Select Session
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_004_07_CreateNewSession" })
	public void TC_004_08_SelectSession() {
		String testCase = "TC_004_08_SelectSession";
		logInfo("TEST STARTED: " + testCase);

		
		cp.waitForChatWindow();

		int sessionCount = cp.getSessionCount();
		if (sessionCount < 2) {
			cp.clickNewSession();
			cp.waitForSessionsToLoad();
		}

		Assert.assertTrue(cp.getSessionCount() >= 2, "Should have at least 2 sessions");

		// Select first session
		cp.selectSession(0);
		cp.waitForMessagesToLoad();
		Assert.assertTrue(cp.isSessionSelected(0), "First session should be selected");

		// Select second session
		cp.selectSession(1);
		cp.waitForMessagesToLoad();
		Assert.assertTrue(cp.isSessionSelected(1), "Second session should be selected");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_004_09 - Delete Session
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_004_07_CreateNewSession" })
	public void TC_004_09_DeleteSession() {
		String testCase = "TC_004_09_DeleteSession";
		logInfo("TEST STARTED: " + testCase);

		
		cp.waitForChatWindow();

		// Create a new session to delete
		cp.clickNewSession();
		cp.waitForSessionsToLoad();

		int beforeCount = cp.getSessionCount();
		logInfo("Sessions before: " + beforeCount);

		cp.deleteSession(0);
		cp.waitForSessionsToLoad();

		int afterCount = cp.getSessionCount();
		logInfo("Sessions after: " + afterCount);

		Assert.assertTrue(afterCount < beforeCount, "Session should be deleted");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_004_10 - Voice Button
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_004_01_ChatPage_Loads" })
	public void TC_004_10_VoiceButton() {
		String testCase = "TC_004_10_VoiceButton";
		logInfo("TEST STARTED: " + testCase);

		
		cp.waitForChatWindow();

		Assert.assertTrue(cp.isVoiceButtonVisible(), "Voice button should be visible");

		cp.clickVoiceButton();
		logInfo("Voice button clicked - should trigger voice input or show feedback");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_004_11 - Chat Header Verification
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_004_01_ChatPage_Loads" })
	public void TC_004_11_ChatHeader() {
		String testCase = "TC_004_11_ChatHeader";
		logInfo("TEST STARTED: " + testCase);

		
		cp.waitForChatWindow();

		Assert.assertTrue(cp.isChatHeaderVisible(), "Chat header should be visible");
		Assert.assertEquals(cp.getChatHeaderTitle(), "Clinical Intelligence Unit", "Chat header title should match");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_004_12 - Welcome Message
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_004_01_ChatPage_Loads" })
	public void TC_004_12_WelcomeMessage() {
		String testCase = "TC_004_12_WelcomeMessage";
		logInfo("TEST STARTED: " + testCase);

		
		cp.waitForChatWindow();

		Assert.assertTrue(cp.isWelcomeMessageVisible(), "Welcome message should be visible for new chat");
		Assert.assertTrue(cp.isQuickSuggestionsVisible(), "Quick suggestions should be visible");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_004_13 - Protocol Info
	// ============================================================
	@Test(groups = { "regression" }, dependsOnMethods = { "TC_004_01_ChatPage_Loads" })
	public void TC_004_13_ProtocolInfo() {
		String testCase = "TC_004_13_ProtocolInfo";
		logInfo("TEST STARTED: " + testCase);

		
		cp.waitForChatWindow();

		Assert.assertTrue(cp.isProtocolInfoVisible(), "Protocol info should be visible");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_004_14 - Send Message With Special Characters
	// ============================================================
	@Test(groups = { "edge-case" }, dependsOnMethods = { "TC_004_01_ChatPage_Loads" })
	public void TC_004_14_Edge_SpecialCharacters() {
		String testCase = "TC_004_14_Edge_SpecialCharacters";
		logInfo("TEST STARTED: " + testCase);

		
		cp.waitForChatWindow();

		String specialMsg = "I have pain! @#$%^&*()_+";
		cp.sendMessage(specialMsg);
		cp.waitForAIResponse();

		Assert.assertTrue(cp.hasUserMessage(specialMsg), "Message with special characters should be sent");
		Assert.assertTrue(cp.hasAIResponse(), "AI response should be received");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_004_15 - Edge: Very Long Message
	// ============================================================
	@Test(groups = { "edge-case" }, dependsOnMethods = { "TC_004_01_ChatPage_Loads" })
	public void TC_004_15_Edge_VeryLongMessage() {
		String testCase = "TC_004_15_Edge_VeryLongMessage";
		logInfo("TEST STARTED: " + testCase);

		
		cp.waitForChatWindow();

		String longMessage = "I have severe pain. ".repeat(50);
		cp.sendMessage(longMessage);
		cp.waitForAIResponse();

		Assert.assertTrue(cp.hasUserMessage("severe pain"), "Long message should be sent");
		Assert.assertTrue(cp.hasAIResponse(), "AI response should be received");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_004_16 - Edge: Emoji in Message
	// ============================================================
	@Test(groups = { "edge-case" })
	public void TC_004_16_Edge_EmojiInMessage() {
		String testCase = "TC_004_16_Edge_EmojiInMessage";
		logInfo("TEST STARTED: " + testCase);

		
		cp.waitForChatWindow();

		String emojiMsg = "I have a headache 😫 and fever 🌡️";
		cp.sendMessage(emojiMsg);
		cp.waitForAIResponse();

		Assert.assertTrue(cp.hasUserMessage("headache"), "Message with emojis should be sent");
		Assert.assertTrue(cp.hasAIResponse(), "AI response should be received");

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_004_17 - Edge: Multiple Quick Suggestions
	// ============================================================
	@Test(groups = { "edge-case" }, dependsOnMethods = { "TC_004_01_ChatPage_Loads" })
	public void TC_004_17_Edge_MultipleQuickSuggestions() {
		String testCase = "TC_004_17_Edge_MultipleQuickSuggestions";
		logInfo("TEST STARTED: " + testCase);

		
		cp.waitForChatWindow();

		int suggestionCount = cp.getQuickSuggestionsCount();
		Assert.assertTrue(suggestionCount > 0, "Quick suggestions should exist");

		String[] suggestions = { "I have a headache", "My stomach hurts", "I feel dizzy" };

		for (String suggestion : suggestions) {
			cp.clickQuickSuggestion(suggestion);
			Assert.assertEquals(cp.getTextareaValue(), suggestion, "Textarea should be populated with: " + suggestion);
			cp.clearTextarea();
		}

		logInfo("TEST PASSED: " + testCase);
	}

	// ============================================================
	// TC_004_18 - Edge: Mobile New Chat Button
	// ============================================================
	@Test(groups = { "edge-case" }, dependsOnMethods = { "TC_004_01_ChatPage_Loads" })
	public void TC_004_18_Edge_MobileNewChat() {
		String testCase = "TC_004_18_Edge_MobileNewChat";
		logInfo("TEST STARTED: " + testCase);

		
		cp.waitForChatWindow();

		int beforeCount = cp.getSessionCount();
		cp.clickMobileNewChat();
		cp.waitForSessionsToLoad();

		int afterCount = cp.getSessionCount();
		Assert.assertTrue(afterCount >= beforeCount, "Mobile new chat should work");

		logInfo("TEST PASSED: " + testCase);
	}
}
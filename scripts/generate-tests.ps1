$ts = (Get-Date).ToString('yyyyMMddHHmmss')

function Write-TestFile($path, $content){
    if(Test-Path $path){ Copy-Item -Path $path -Destination "${path}.bak.${ts}" -Force -ErrorAction SilentlyContinue }
    $content | Set-Content -Path $path -Encoding UTF8
    Write-Host "Wrote $path (backup created if existed)"
}

$TC003 = @'
package testCases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_003_SymptomAnalyzerTest extends BaseClass {

    @Test(groups = { "smoke", "regression" })
    public void TC_003_SymptomAnalyzer_Smoke() {
        String testCase = "TC_003_SymptomAnalyzer_Smoke";
        logInfo("TEST STARTED: " + testCase);

        driver.get(p.getProperty("appURL") + "/analyzer");

        Assert.assertTrue(waitForUrlContains("analyzer", 5), "Analyzer route not reached");

        By heading = By.xpath("//h1[contains(.,'Clinical') and contains(.,'Analyzer')]");
        Assert.assertNotNull(waitForElementVisible(heading, 10), "Analyzer heading not visible");

        logInfo("TEST PASSED: " + testCase);
    }

}
'@

$TC004 = @'
package testCases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_004_ChatTest extends BaseClass {

    @Test(groups = { "smoke", "regression" })
    public void TC_004_Chat_Smoke() {
        String testCase = "TC_004_Chat_Smoke";
        logInfo("TEST STARTED: " + testCase);

        driver.get(p.getProperty("appURL") + "/chat");

        Assert.assertTrue(waitForUrlContains("chat", 5), "Chat route not reached");

        By heading = By.xpath("//h1[contains(.,'Signal') or contains(.,'Chat')]");
        Assert.assertNotNull(waitForElementVisible(heading, 10), "Chat heading not visible");

        logInfo("TEST PASSED: " + testCase);
    }

}
'@

$TC005 = @'
package testCases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_005_MedicineTest extends BaseClass {

    @Test(groups = { "smoke", "regression" })
    public void TC_005_Medicine_Smoke() {
        String testCase = "TC_005_Medicine_Smoke";
        logInfo("TEST STARTED: " + testCase);

        driver.get(p.getProperty("appURL") + "/medicine");

        Assert.assertTrue(waitForUrlContains("medicine", 5), "Medicine route not reached");

        By heading = By.xpath("//h1[contains(.,'Medicine') and contains(.,'Compass') or contains(.,'Medicine')]");
        Assert.assertNotNull(waitForElementVisible(heading, 10), "Medicine heading not visible");

        logInfo("TEST PASSED: " + testCase);
    }

}
'@

$TC006 = @'
package testCases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_006_HospitalTest extends BaseClass {

    @Test(groups = { "smoke", "regression" })
    public void TC_006_Hospital_Smoke() {
        String testCase = "TC_006_Hospital_Smoke";
        logInfo("TEST STARTED: " + testCase);

        driver.get(p.getProperty("appURL") + "/hospitals");

        Assert.assertTrue(waitForUrlContains("hospitals", 5), "HospitalFinder route not reached");

        By heading = By.xpath("//h1[contains(.,'Hospital') and contains(.,'Finder') or contains(.,'Find')]");
        Assert.assertNotNull(waitForElementVisible(heading, 10), "Hospital heading not visible");

        logInfo("TEST PASSED: " + testCase);
    }

}
'@

$TC007 = @'
package testCases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_007_ReportTest extends BaseClass {

    @Test(groups = { "smoke", "regression" })
    public void TC_007_Report_Smoke() {
        String testCase = "TC_007_Report_Smoke";
        logInfo("TEST STARTED: " + testCase);

        driver.get(p.getProperty("appURL") + "/reports");

        Assert.assertTrue(waitForUrlContains("reports", 5), "Reports route not reached");

        By heading = By.xpath("//h1[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'report')]");
        Assert.assertNotNull(waitForElementVisible(heading, 10), "Reports heading not visible");

        logInfo("TEST PASSED: " + testCase);
    }

}
'@

$TC008 = @'
package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.LoginPage;

public class TC_008_LoginSecurityTest extends BaseClass {

    LoginPage lp;

    @Test(groups = { "regression" })
    public void TC_008_Login_SQLi_Email() {
        String testCase = "TC_008_Login_SQLi_Email";
        logInfo("TEST STARTED: " + testCase);

        driver.get(p.getProperty("appURL") + "/login");
        lp = new LoginPage(driver);

        // SQLi-like email payload
        lp.login("' OR '1'='1@example.com", "somepassword");
        Assert.assertFalse(waitForUrlContains("dashboard", 5), "SQLi in email should not bypass login");

        String err = lp.getErrorMessage();
        Assert.assertTrue(err.length() > 0, "Error message expected for SQLi attempt");

        logInfo("TEST PASSED: " + testCase);
    }

    @Test(groups = { "regression" })
    public void TC_009_Login_SQLi_Password() {
        String testCase = "TC_009_Login_SQLi_Password";
        logInfo("TEST STARTED: " + testCase);

        driver.get(p.getProperty("appURL") + "/login");
        lp = new LoginPage(driver);

        lp.login("nonexistent" + System.currentTimeMillis() + "@example.com", "' OR '1'='1");
        Assert.assertFalse(waitForUrlContains("dashboard", 5), "SQLi in password should not bypass login");

        String err = lp.getErrorMessage();
        Assert.assertTrue(err.length() > 0, "Error message expected for SQLi attempt");

        logInfo("TEST PASSED: " + testCase);
    }

}
'@

Write-TestFile "D:\eclipse-workspace\MediAssistAi\src\test\java\testCases\TC_003_SymptomAnalyzerTest.java" $TC003
Write-TestFile "D:\eclipse-workspace\MediAssistAi\src\test\java\testCases\TC_004_ChatTest.java" $TC004
Write-TestFile "D:\eclipse-workspace\MediAssistAi\src\test\java\testCases\TC_005_MedicineTest.java" $TC005
Write-TestFile "D:\eclipse-workspace\MediAssistAi\src\test\java\testCases\TC_006_HospitalTest.java" $TC006
Write-TestFile "D:\eclipse-workspace\MediAssistAi\src\test\java\testCases\TC_007_ReportTest.java" $TC007
Write-TestFile "D:\eclipse-workspace\MediAssistAi\src\test\java\testCases\TC_008_LoginSecurityTest.java" $TC008

Set-Location "D:\eclipse-workspace\MediAssistAi"
Write-Host "Compiling tests (mvn -q test-compile)..."
mvn -q test-compile
Write-Host "Done."

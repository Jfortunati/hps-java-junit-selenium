package com.coffeemachine;

import junit.framework.TestCase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;


public class SupportInternationalisationTest extends TestCase {
    // Tags: sprint:3
    public Actionwords actionwords;
    public WebDriver driver;
    private CBTHelper cbt;
    public String score = "fail";
    public String featureName = "Support internationalisation";

    protected void setUp() throws Exception {
        super.setUp();
        actionwords = new Actionwords();
    }

    protected void scenarioSetup(String testName)  throws Exception {
        driver = new SeleniumDriverGetter().getDriver(featureName, testName);
        if (System.getenv("USE_CBT") != null) {
            cbt = new CBTHelper();
            cbt.setSessionId(((RemoteWebDriver)driver).getSessionId().toString());
        }
        actionwords.setDriver(driver);

    }

    protected void tearDown() throws Exception {
        if (System.getenv("USE_CBT") != null) {
            cbt.setScore(score);
        }
        driver.quit();
    }

    public void messagesAreBasedOnLanguage(String language, String readyMessage) throws Exception {
        // Tags: priority:medium
        scenarioSetup("Messages are based on language");
        // When I start the coffee machine using language "<language>"
        actionwords.iStartTheCoffeeMachineUsingLanguageLang(language);
        // Then message "<ready_message>" should be displayed
        actionwords.messageMessageShouldBeDisplayed(readyMessage);
        score = "pass";
    }

    public void testMessagesAreBasedOnLanguageEnglish() throws Exception {
        messagesAreBasedOnLanguage("en", "Ready");
    }

    public void testMessagesAreBasedOnLanguageFrench() throws Exception {
        messagesAreBasedOnLanguage("fr", "Pret");
    }


    // 
    // Tags: priority:medium
    public void testNoMessagesAreDisplayedWhenMachineIsShutDown() throws Exception {
        scenarioSetup("No messages are displayed when machine is shut down");

        // Given the coffee machine is started
        actionwords.theCoffeeMachineIsStarted();
        // When I shutdown the coffee machine
        actionwords.iShutdownTheCoffeeMachine();
        // Then message "" should be displayed
        actionwords.messageMessageShouldBeDisplayed("");
        score = "pass";
    }
}
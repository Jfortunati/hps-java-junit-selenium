package com.coffeemachine;

import junit.framework.TestCase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.coffeemachine.Actionwords;
import com.coffeemachine.SeleniumDriverGetter;

public class GroundsTest extends TestCase {

    public Actionwords actionwords;
    public WebDriver driver;
    private CBTHelper cbt;
    public String score = "fail";
    public String featureName = "Grounds";

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

        // Given the coffee machine is started
        actionwords.theCoffeeMachineIsStarted();
        // And I handle everything except the grounds
        actionwords.iHandleEverythingExceptTheGrounds();
    }

    protected void tearDown() throws Exception {
        if (System.getenv("USE_CBT") != null) {
            cbt.setScore(score);
        }
        driver.quit();
    }

    // 
    // Tags: priority:high
    public void testMessageEmptyGroundsIsDisplayedAfter30CoffeesAreTaken() throws Exception {
        scenarioSetup("Message \"Empty grounds\" is displayed after 30 coffees are taken");

        // When I take "30" coffees
        actionwords.iTakeCoffeeNumberCoffees(30);
        // Then message "Empty grounds" should be displayed
        actionwords.messageMessageShouldBeDisplayed("Empty grounds");
        score = "pass";
    }
    // 
    // Tags: priority:medium
    public void testWhenTheGroundsAreEmptiedMessageIsRemoved() throws Exception {
        scenarioSetup("When the grounds are emptied, message is removed");

        // When I take "30" coffees
        actionwords.iTakeCoffeeNumberCoffees(30);
        // And I empty the coffee grounds
        actionwords.iEmptyTheCoffeeGrounds();
        // Then message "Ready" should be displayed
        actionwords.messageMessageShouldBeDisplayed("Ready");
        score = "pass";
    }
}
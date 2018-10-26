package com.coffeemachine;

import junit.framework.TestCase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.coffeemachine.Actionwords;
import com.coffeemachine.SeleniumDriverGetter;

public class WaterTest extends TestCase {

    public Actionwords actionwords;
    public WebDriver driver;
    private CBTHelper cbt;
    public String score = "fail";
    public String featureName = "Water";

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
        // And I handle everything except the water tank
        actionwords.iHandleEverythingExceptTheWaterTank();
    }

    protected void tearDown() throws Exception {
        if (System.getenv("USE_CBT") != null) {
            cbt.setScore(score);
        }
        driver.quit();
    }

    // 
    // Tags: priority:high
    public void testMessageFillWaterTankIsDisplayedAfter50CoffeesAreTaken() throws Exception {
        scenarioSetup("Message \"Fill water tank\" is displayed after 50 coffees are taken");

        // When I take "50" coffees
        actionwords.iTakeCoffeeNumberCoffees(50);
        // Then message "Fill tank" should be displayed
        actionwords.messageMessageShouldBeDisplayed("Fill tank");
        score = "pass";
    }
    // 
    // Tags: priority:low
    public void testItIsPossibleToTake10CoffeesAfterTheMessageFillWaterTankIsDisplayed() throws Exception {
        scenarioSetup("It is possible to take 10 coffees after the message \"Fill water tank\" is displayed");

        // When I take "60" coffees
        actionwords.iTakeCoffeeNumberCoffees(60);
        // Then coffee should be served
        actionwords.coffeeShouldBeServed();
        // When I take a coffee
        actionwords.iTakeACoffee();
        // Then coffee should not be served
        actionwords.coffeeShouldNotBeServed();
        score = "pass";
    }
    // 
    // Tags: priority:high
    public void testWhenTheWaterTankIsFilledTheMessageDisappears() throws Exception {
        scenarioSetup("When the water tank is filled, the message disappears");

        // When I take "55" coffees
        actionwords.iTakeCoffeeNumberCoffees(55);
        // And I fill the water tank
        actionwords.iFillTheWaterTank();
        // Then message "Ready" should be displayed
        actionwords.messageMessageShouldBeDisplayed("Ready");
        score = "pass";
    }
}
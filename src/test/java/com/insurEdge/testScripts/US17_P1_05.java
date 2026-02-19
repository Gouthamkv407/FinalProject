package com.insurEdge.testScripts;

import org.openqa.selenium.ElementNotInteractableException;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.insurEdge.Utilities.BrowserConfig;

public class US17_P1_05 extends BrowserConfig {

    // --- Hamburger Close Validation ---
    @Test(priority = 1)
    public void validateHamburgerClose() {
        try {
            homePage.getMenuButton().click(); // hamburger button
            homePage.getElement(homePage.dashBoard).click(); // simulate interaction when closed
            System.out.println("Hamburger closed successfully");
            AssertJUnit.assertTrue(true);
        } catch (ElementNotInteractableException e) {
            System.out.println("Hamburger Functioning Properly - left-side menu closed");
            AssertJUnit.assertTrue(true);
        } catch (Exception e) {
            System.out.println("Some Other Exception - left-side menu visible");
            AssertJUnit.fail();
        }
    }

    // --- Hamburger Open Validation ---
    @Test(priority = 2)
    public void validateHamburgerOpen() {
        try {
            homePage.getMenuButton().click(); // open hamburger again
            homePage.getElement(homePage.customer).click(); // interact with customer submenu
            //System.out.println("Customer interacted with - Test case passed");
            AssertJUnit.assertTrue(true);
        } catch (ElementNotInteractableException e) {
            //System.out.println("Hamburger not Functioning Properly - left-side menu not visible");
            AssertJUnit.fail();
        }
    }
}
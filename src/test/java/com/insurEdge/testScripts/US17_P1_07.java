package com.insurEdge.testScripts;

import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.insurEdge.Utilities.BrowserConfig;

public class US17_P1_07 extends BrowserConfig {

    // --- Footer Credits Validation ---
    @Test(priority = 1)
    public void validateFooterCredits() {
        try {
            String message = homePage.performActionsOnFooterSectionCredits();
            if (message.equals("Footer Present")) {
                Assert.assertTrue(true, "Footer Credits Present");
                System.out.println("Footer Credits Present");
            } else {
                System.out.println("Footer Credits Absent");
                Assert.fail("Footer Credits Absent");
            }
        } catch (NoSuchElementException e) {
            System.out.println("Footer Credits element not found: " + e.getMessage());
            Assert.fail("Footer Credits validation threw exception");
        }
    }

    // --- Footer Hyperlink Validation ---
    @Test(priority = 2)
    public void validateFooterHyperLink() {
        try {
            String message = homePage.performActionsOnFooterSectionHyperLink();
            if (message.equals("Hyperlink present")) {
                Assert.assertTrue(true, "Footer Hyperlink Present");
                System.out.println("Footer Hyperlink Present");
            } else {
                System.out.println("Footer Hyperlink Absent or Broken");
                Assert.fail("Footer Hyperlink Absent or Broken");
                // recover by navigating back so further tests can continue
                driver.navigate().back();
            }
        } catch (Exception e) {
            System.out.println("Footer Hyperlink check failed: " + e.getMessage());
            Assert.fail("Footer Hyperlink validation threw exception");
            driver.navigate().back();
        }
    }
}

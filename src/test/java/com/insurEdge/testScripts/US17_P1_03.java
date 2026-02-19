package com.insurEdge.testScripts;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.insurEdge.Utilities.BrowserConfig;

public class US17_P1_03 extends BrowserConfig {

    private WebElement waitForVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    // --- Task1: Validate footer is displayed on all pages ---
    @Test(priority = 1)
    public void validateFooterPresenceOnPages() {
        String[] pagesURL = {
            "https://qeaskillhub.cognizant.com/AdminDashboard",
            "https://qeaskillhub.cognizant.com/CreateCustomer",
            "https://qeaskillhub.cognizant.com/AdminViewCustomer",
            "https://qeaskillhub.cognizant.com/AdminAuthorizeCustomer",
            "https://qeaskillhub.cognizant.com/AdminCreateMainCategory",
            "https://qeaskillhub.cognizant.com/AdminCreateSubCategory",
            "https://qeaskillhub.cognizant.com/AdminCreatePolicy",
            "https://qeaskillhub.cognizant.com/AdminAuthorizePolicy",
            "https://qeaskillhub.cognizant.com/AdminViewPolicy",
            "https://qeaskillhub.cognizant.com/AdminModifyPolicy",
            "https://qeaskillhub.cognizant.com/AdminAppliedPolicyHolders",
            "https://qeaskillhub.cognizant.com/AdminApprovedPolicyHolder",
            "https://qeaskillhub.cognizant.com/AdminPendingPolicyHolder",
            "https://qeaskillhub.cognizant.com/AdminRejectedPolicyHolder"
        };

        for (String url : pagesURL) {
            driver.navigate().to(url);
            WebElement footer = wait.until(ExpectedConditions.visibilityOf(homePage.getElement(homePage.footerLine1)));
            Assert.assertTrue(footer.isDisplayed(), "Footer should be displayed on page: " + url);
        }
    }

    // --- Task3: Footer first line ---
    @Test(priority = 2)
    public void validateFooterLine1() {
        WebElement footerLine1 = waitForVisible(homePage.getElement(homePage.footerLine1));
        String expectedText = "© Copyright InsurEdge. All Rights Reserved";
        Assert.assertEquals(footerLine1.getText(), expectedText, "Footer first line text should match expected");

        String color = footerLine1.getCssValue("color");
        Assert.assertTrue(color.equals("rgba(1, 41, 112, 1)") || color.equals("#012970"),
                "Footer line1 should be in Deep Navy Blue / Oxford Blue");

        WebElement boldText = waitForVisible(homePage.getElement(homePage.footerLine1Bold));
        String fontWeight = boldText.getCssValue("font-weight");
        Assert.assertTrue(fontWeight.equals("bold") || Integer.parseInt(fontWeight) >= 700,
                "'InsurEdge' should appear in bold in footer line1");
    }

    // --- Task4: Footer second line ---
    @Test(priority = 3)
    public void validateFooterLine2() {
        WebElement footerLine2 = waitForVisible(homePage.getElement(homePage.footerLine2));
        String expectedText = "Designed by QEA Skill Enable Function";
        Assert.assertEquals(footerLine2.getText(), expectedText, "Footer second line text should match expected");

        String color = footerLine2.getCssValue("color");
        Assert.assertTrue(color.equals("rgba(1, 41, 112, 1)") || color.equals("#012970"),
                "'Designed by' should be in Deep Navy Blue / Oxford Blue");
    }

    // --- Task5: QEA Skill Enable Function link ---
    @Test(priority = 4)
    public void validateQeaSkillEnableFunctionLink() {
        WebElement qeaLink = waitForVisible(homePage.getElement(homePage.footerQeaLink));
        String color = qeaLink.getCssValue("color");
        Assert.assertTrue(color.equals("rgba(65, 84, 241, 1)") || color.equals("#4154F1"),
                "'QEA Skill Enable Function' should be in Royal Blue color");

        Assert.assertTrue(qeaLink.isEnabled(), "'QEA Skill Enable Function' link should be clickable");
    }
}

package com.insurEdge.testScripts;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.insurEdge.Utilities.BrowserConfig;
import com.insurEdge.AuthorizePage.AuthorizePage;

public class US17_P1_15 extends BrowserConfig {

    private AuthorizePage authorizePage;

    @BeforeClass
    public void initAuthorizePage() {
        authorizePage = new AuthorizePage(driver);
        authorizePage.navigateToAuthorizeCustomer();
    }

    // --- Test Case 1: Breadcrumb "Customer" is visible and styled consistently ---
    @Test(priority = 1)
    public void validateCustomerBreadcrumbVisibleAndStyled() {
        Assert.assertTrue(authorizePage.isCustomerBreadcrumbVisible(),
                "Customer breadcrumb link is not visible");
        Assert.assertTrue(authorizePage.isCustomerBreadcrumbStyledConsistently(),
                "Customer breadcrumb link is not styled consistently (underline + color)");
    }

    // --- Test Case 2: Clicking "Customer" redirects to Dashboard ---
    @Test(priority = 2)
    public void validateCustomerBreadcrumbRedirectsToDashboard() {
        Assert.assertTrue(authorizePage.clickCustomerBreadcrumbRedirectsToDashboard(),
                "Clicking Customer breadcrumb did not redirect to Dashboard");
    }

    // --- Test Case 3: Breadcrumb link is functional across common resolutions ---
    @DataProvider(name = "viewports")
    public Object[][] viewports() {
        return new Object[][]{
    
            {1366, 768},  // Laptop
            {1920, 1080}  // Desktop FHD
        };
    }

    @Test(dataProvider = "viewports", priority = 3)
    public void customerBreadcrumbClickableAcrossResolutions(int width, int height) {
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(width, height));
        authorizePage.navigateToAuthorizeCustomer();

        Assert.assertTrue(authorizePage.isCustomerBreadcrumbVisible(),
                "Customer breadcrumb should be visible at " + width + "x" + height);

        Assert.assertTrue(authorizePage.clickCustomerBreadcrumbRedirectsToDashboard(),
                "Customer breadcrumb should redirect to Dashboard at " + width + "x" + height);
    }
}

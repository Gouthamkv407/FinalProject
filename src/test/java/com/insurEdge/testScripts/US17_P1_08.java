package com.insurEdge.testScripts;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.insurEdge.Utilities.BrowserConfig;
import com.insurEdge.AuthorizePage.AuthorizePage;

public class US17_P1_08 extends BrowserConfig {

    private AuthorizePage authorizePage;

    @BeforeClass
    public void initAuthorizePage() {
        authorizePage = new AuthorizePage(driver);
        authorizePage.navigateToAuthorizeCustomer();
    }

    @Test(priority = 1)
    public void validateAuthorizeCustomerTitleColor() {
        Assert.assertTrue(authorizePage.validateAuthorizeCustomerTitleColor(),
                "Authorize Customer page title font color is incorrect");
    }

    @Test(priority = 2)
    public void validateBreadcrumbTrailPresence() {
        String trailText = authorizePage.getBreadcrumbTrailText();
        Assert.assertTrue(trailText != null && !trailText.isEmpty(),
                "Breadcrumb trail is missing or empty");
        System.out.println("Breadcrumb trail: " + trailText);
    }
}

package com.insurEdge.testScripts;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.insurEdge.Utilities.BrowserConfig;
import com.insurEdge.AuthorizePage.AuthorizePage;

public class US17_P1_09 extends BrowserConfig {

    private AuthorizePage authorizePage;

    @BeforeClass
    public void initAuthorizePage() {
        authorizePage = new AuthorizePage(driver);
        authorizePage.navigateToAuthorizeCustomer();
    }

    @Test(priority = 1)
    public void validateApproveButtonUI() {
        Assert.assertTrue(authorizePage.validateApproveButtonColor(),
                "Approve button is either not clickable or has incorrect color");
    }

    @Test(priority = 2)
    public void validateRejectButtonUI() {
        Assert.assertTrue(authorizePage.validateRejectButtonColor(),
                "Reject button is either not clickable or has incorrect color");
    }

    @Test(priority = 3)
    public void validateDeleteButtonUI() {
        Assert.assertTrue(authorizePage.validateDeleteButtonColor(),
                "Delete button is either not clickable or has incorrect color");
    }
}

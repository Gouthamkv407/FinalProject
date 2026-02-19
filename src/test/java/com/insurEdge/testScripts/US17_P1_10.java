package com.insurEdge.testScripts;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.insurEdge.Utilities.BrowserConfig;
import com.insurEdge.AuthorizePage.AuthorizePage;

public class US17_P1_10 extends BrowserConfig {

    private AuthorizePage authorizePage;

    @BeforeClass
    public void initAuthorizePage() {
        authorizePage = new AuthorizePage(driver);
        authorizePage.navigateToAuthorizeCustomer();
    }

    @Test(priority = 1)
    public void validateSearchBoxAppearance() {
        Assert.assertTrue(authorizePage.validateSearchBoxAppearance(),
                "Search Customer input box or label is not displayed correctly");
    }

    @Test(priority = 2)
    public void validatePlaceholderText() {
        Assert.assertTrue(authorizePage.validatePlaceholderText(),
                "Search Customer placeholder text mismatch");
    }

    @Test(priority = 3)
    public void validateSearchBoxPosition() {
        Assert.assertTrue(authorizePage.validateSearchBoxPosition(),
                "Search Customer input box is not positioned correctly relative to Records dropdown");
    }
}

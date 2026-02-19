package com.insurEdge.testScripts;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.insurEdge.Utilities.BrowserConfig;
import com.insurEdge.AuthorizePage.AuthorizePage;

public class US17_P1_14 extends BrowserConfig {

    private AuthorizePage authorizePage;

    @BeforeClass
    public void initAuthorizePage() {
        authorizePage = new AuthorizePage(driver);
        authorizePage.navigateToAuthorizeCustomer();
    }

    @Test(priority = 1)
    public void validatePaginationControlsClickable() {
        Assert.assertTrue(authorizePage.validatePaginationControlsClickable(),
                "Pagination controls are not clickable");
    }

    @Test(priority = 2)
    public void validateCurrentPageHighlighted() {
        Assert.assertTrue(authorizePage.validateCurrentPageHighlighted(),
                "Current page is not highlighted in pagination controls");
    }
}

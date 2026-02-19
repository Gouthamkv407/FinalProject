package com.insurEdge.testScripts;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.insurEdge.Utilities.BrowserConfig;
import com.insurEdge.AuthorizePage.AuthorizePage;

public class US17_P1_13 extends BrowserConfig {

    private AuthorizePage authorizePage;

    @BeforeClass
    public void initAuthorizePage() {
        authorizePage = new AuthorizePage(driver);
        authorizePage.navigateToAuthorizeCustomer();
    }

    // Task 1: Checkboxes visible at start of each row
    @Test(priority = 1)
    public void validateCheckboxesVisible() {
        Assert.assertTrue(authorizePage.validateCheckboxesVisible(),
                "Checkboxes are not visible at the start of each row in User Details table");
    }

    // Task 3: Checkbox selection/deselection toggles correctly
    @Test(priority = 2)
    public void validateCheckboxSelectionToggle() {
        Assert.assertTrue(authorizePage.validateCheckboxSelectionToggle(),
                "Checkbox selection/deselection did not toggle correctly");
    }
}

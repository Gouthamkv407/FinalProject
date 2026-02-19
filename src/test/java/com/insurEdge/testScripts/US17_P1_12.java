package com.insurEdge.testScripts;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.insurEdge.Utilities.BrowserConfig;
import com.insurEdge.AuthorizePage.AuthorizePage;

public class US17_P1_12 extends BrowserConfig {

    private AuthorizePage authorizePage;

    @BeforeClass
    public void initAuthorizePage() {
        authorizePage = new AuthorizePage(driver);
        authorizePage.navigateToAuthorizeCustomer();
    }

    @Test(priority = 1)
    public void validateCustomerTableAppearance() {
        Assert.assertTrue(authorizePage.validateCustomerTableAppearance(),
                "Customer Details table is not displayed on the Authorize Customer page");
    }

    @Test(priority = 2)
    public void validateCustomerTableColumnCount() {
        Assert.assertTrue(authorizePage.validateCustomerTableColumnCount(),
                "Customer Details table does not have the expected number of columns");
    }

    @Test(priority = 3)
    public void validateCustomerTableColumnText() {
        Assert.assertTrue(authorizePage.validateCustomerTableColumnText(),
                "Customer Details table column order is incorrect");
    }
}

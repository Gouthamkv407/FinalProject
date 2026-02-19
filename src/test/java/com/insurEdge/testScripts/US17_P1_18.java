package com.insurEdge.testScripts;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.insurEdge.Utilities.BrowserConfig;
import com.insurEdge.AuthorizePage.AuthorizePage;

public class US17_P1_18 extends BrowserConfig {

    private AuthorizePage authorizePage;

    @BeforeClass
    public void initAuthorizePage() {
        authorizePage = new AuthorizePage(driver);
        // Assuming login and initial navigation is handled by BrowserConfig or similar
        authorizePage.navigateToAuthorizeCustomer();
    }

    @Test(priority = 1)
    public void validateApproveButtonPopup() {
        authorizePage.selectFirstCustomer();
        authorizePage.clickApprove();
        Assert.assertTrue(authorizePage.isConfirmPopupDisplayed(), 
                "Confirm Approval popup is NOT displayed.");
    }

    @Test(priority = 2, dependsOnMethods = "validateApproveButtonPopup")
    public void validateConfirmationPopup() {
        authorizePage.clickOkOnPopup();
        Assert.assertTrue(authorizePage.isSuccessMessageDisplayed(), 
                "Confirmation popup (success message) is NOT displayed.");
    }

    @Test(priority = 3, dependsOnMethods = "validateConfirmationPopup")
    public void validateStatusChange() {
        authorizePage.clickOkOnPopup(); // Click OK on the success popup
        String actualStatus = authorizePage.getFirstCustomerStatus();
        Assert.assertEquals(actualStatus, "Approved", 
                "Customer status did not change to Approved.");
    }
}
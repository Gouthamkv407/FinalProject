package com.insurEdge.testScripts;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.insurEdge.Utilities.BrowserConfig;
import com.insurEdge.AuthorizePage.AuthorizePage;

public class US17_P1_19 extends BrowserConfig {

    private AuthorizePage authorizePage;
    private String testCustomerId;

    @BeforeClass
    public void setup() {
        authorizePage = new AuthorizePage(driver);
        authorizePage.navigateToAuthorizeCustomer();
    }

    @Test(priority = 1, description = "Test 1: Verify Confirm Rejection popup shows on selection")
    public void validateConfirmRejectPopup() {
        testCustomerId = authorizePage.selectFirstNonRejectedCustomer();
        Assert.assertNotNull(testCustomerId, "FAIL: No customers available to reject.");
        
        authorizePage.clickReject();
        Assert.assertTrue(authorizePage.isConfirmRejectPopupVisible(), "FAIL: Confirm Rejection popup not seen.");
    }

    @Test(priority = 2, dependsOnMethods = "validateConfirmRejectPopup", description = "Test 2: Verify Success popup shows after OK")
    public void validateRejectionSuccess() {
        authorizePage.clickOverlayOk(); // Confirm Rejection
        Assert.assertTrue(authorizePage.isRejectSuccessPopupVisible(), "FAIL: Success popup did not appear.");
        authorizePage.clickOverlayOk(); // Close Success popup
    }

    @Test(priority = 3, dependsOnMethods = "validateRejectionSuccess", description = "Test 3: Verify Grid Status updated to Rejected")
    public void validateStatusUpdateInGrid() {
        // This is where we verify the data actually changed in the DB/Grid
        String finalStatus = authorizePage.getStatusOfCustomerById(testCustomerId);
        
        System.out.println("Customer ID: " + testCustomerId + " | Final Status: " + finalStatus);
        Assert.assertTrue(finalStatus.equalsIgnoreCase("Rejected"), 
            "FAIL: Expected 'Rejected' status for ID " + testCustomerId + " but found '" + finalStatus + "'");
    }

    @Test(priority = 4, description = "Test 4: Verify Confirm popup shows even with NO selection")
    public void validateRejectWithNoSelection() {
        authorizePage.clearCheckboxes();
        authorizePage.clickReject();
        Assert.assertTrue(authorizePage.isConfirmRejectPopupVisible(), "FAIL: Confirm popup should show even if empty.");
    }

    @Test(priority = 5, dependsOnMethods = "validateRejectWithNoSelection", description = "Test 5: Verify No Selection warning shows")
    public void validateNoSelectionWarning() {
        authorizePage.clickOverlayOk(); // Click OK on the 'Confirm Rejection'
        
        Assert.assertTrue(authorizePage.isNoSelectionWarningVisible(), "FAIL: '⚠️ No Selection' warning not displayed.");
        String msg = authorizePage.getWarningMessage();
        Assert.assertTrue(msg.contains("Please select at least one customer"), "FAIL: Warning message text mismatch.");
        
        authorizePage.clickOverlayOk(); // Final cleanup
    }
}
package com.insurEdge.testScripts;
 
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.insurEdge.Utilities.BrowserConfig;
import com.insurEdge.AuthorizePage.AuthorizePage;
 
public class US17_P1_20 extends BrowserConfig {
 
    private AuthorizePage authorizePage;
    private String testCustomerId;
 
    @BeforeClass
    public void setup() {
        authorizePage = new AuthorizePage(driver);
        authorizePage.navigateToAuthorizeCustomer();
    }
 
    @Test(priority = 1, description = "Test 1: Validate that when a customer checkbox is selected and the Delete button is clicked, a Delete confirmation popup should be displayed.")
    public void validateConfirmDeletePopup() {
        authorizePage.selectFirstCustomerForDelete();
        authorizePage.clickDelete();
        Assert.assertTrue(authorizePage.isConfirmDeletePopupVisible(), "FAIL: Confirm Delete popup not seen.");
    }
 
    @Test(priority = 2, dependsOnMethods = "validateConfirmDeletePopup", description = "Test 2: Verify Success popup shows after OK")
    public void validateDeletionSuccess() {
        authorizePage.clickOverlayOk(); // Confirm Deletion
        Assert.assertTrue(authorizePage.isDeleteSuccessPopupVisible(), "FAIL: Success popup did not appear.");
        authorizePage.clickOverlayOk(); // Close Success popup
    }
 
    @Test(priority = 3, dependsOnMethods = "validateDeletionSuccess", description = "Test 3: Verify Grid Status updated to Deleted")
    public void validateStatusUpdateInGrid() {
        // For simplicity, re‑use the first selected customer ID
        testCustomerId = "someCustomerId"; // Replace with actual captured ID if needed
        String finalStatus = authorizePage.getStatusOfCustomerById(testCustomerId);
 
        System.out.println("Customer ID: " + testCustomerId + " | Final Status: " + finalStatus);
        Assert.assertTrue(finalStatus.equalsIgnoreCase("Deleted"),
            "FAIL: Expected 'Deleted' status for ID " + testCustomerId + " but found '" + finalStatus + "'");
    }
 
    @Test(priority = 4, description = "Test 4: Verify Confirm popup shows even with NO selection")
    public void validateDeleteWithNoSelection() {
        authorizePage.clearCheckboxes();
        authorizePage.clickDelete();
        Assert.assertTrue(authorizePage.isConfirmDeletePopupVisible(), "FAIL: Confirm popup should show even if empty.");
    }
 
    @Test(priority = 5, dependsOnMethods = "validateDeleteWithNoSelection", description = "Test 5: Verify No Selection warning shows")
    public void validateNoSelectionWarning() {
        authorizePage.clickOverlayOk(); // Click OK on the 'Confirm Deletion'
 
        Assert.assertTrue(authorizePage.isNoSelectionWarningVisible(), "FAIL: '⚠️ No Selection' warning not displayed.");
        String msg = authorizePage.getWarningMessage();
        Assert.assertTrue(msg.contains("Please select at least one customer"), "FAIL: Warning message text mismatch.");
 
        authorizePage.clickOverlayOk(); // Final cleanup
    }
}
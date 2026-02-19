package com.insurEdge.testScripts;

import com.insurEdge.AuthorizePage.AuthorizePage;
import com.insurEdge.Utilities.BrowserConfig;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class US17_P1_17 extends BrowserConfig {

    private AuthorizePage authorizePage;

    @BeforeClass
    public void initAuthorizePage() {
        authorizePage = new AuthorizePage(driver);
        authorizePage.navigateToAuthorizeCustomer();
    }

    // --- Test Case 1: Dropdown should have 5,10,20 options ---
    @Test(priority = 1)
    public void verifyRecordsDropdownOptions() {
        List<String> expected = Arrays.asList("5", "10", "20");
        List<String> actual = authorizePage.getRecordsDropdownOptionValues();
        Assert.assertEquals(actual, expected,
                "Dropdown options incorrect. Expected " + expected + " but got " + actual);
    }

    // --- Test Case 2: Default value should be 5 ---
    @Test(priority = 2)
    public void verifyDefaultRecordsDropdownValue() {
        Assert.assertEquals(authorizePage.getRecordsDropdownDefaultValue(), "5",
                "Default dropdown value is not 5!");
    }

    // --- Test Case 3: Selecting 10 should show <= 10 rows ---
    @Test(priority = 3)
    public void verifySelect10Records() {
        authorizePage.selectRecordsPerPage("10");
        int rows = authorizePage.getRenderedRowCount();
        Assert.assertTrue(rows <= 10, "More than 10 rows displayed: " + rows);
    }

    // --- Test Case 4: Selecting 20 should show <= 20 rows ---
    @Test(priority = 4)
    public void verifySelect20Records() {
        authorizePage.selectRecordsPerPage("20");
        int rows = authorizePage.getRenderedRowCount();
        Assert.assertTrue(rows <= 20, "More than 20 rows displayed: " + rows);
    }

    // --- Test Case 5: Selecting 5 should show <= 5 rows ---
    @Test(priority = 5)
    public void verifySelect5Records() {
        authorizePage.selectRecordsPerPage("5");
        int rows = authorizePage.getRenderedRowCount();
        Assert.assertTrue(rows <= 5, "More than 5 rows displayed: " + rows);
    }
}

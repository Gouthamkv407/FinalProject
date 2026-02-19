package com.insurEdge.testScripts;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.insurEdge.Utilities.BrowserConfig;
import com.insurEdge.Utilities.ExcelUtils;
import com.insurEdge.AuthorizePage.AuthorizePage;

public class US17_P1_16 extends BrowserConfig {

    private AuthorizePage authorizePage;

    @BeforeClass
    public void initAuthorizePage() {
        authorizePage = new AuthorizePage(driver);
        authorizePage.navigateToAuthorizeCustomer();
    }

    // --- Test Case 1: Navigation + Search Box Visibility (Automation16_01) ---
    @Test(priority = 1)
    public void validateNavigationAndSearchBoxVisible() {
        Assert.assertTrue(authorizePage.isCustomerMenuVisibleAndClickable(),
                "Customer menu is not visible or clickable");
        Assert.assertTrue(authorizePage.isAuthorizeCustomerLinkVisibleAndClickable(),
                "Authorize Customer link is not visible or clickable");
        Assert.assertTrue(authorizePage.isSearchBoxVisible(),
                "Search Customer input box is not visible");
    }

    // --- Test Case 2: Excel-driven Positive Search Validation (Automation_16_02) ---
    @Test(priority = 2)
    public void validateCustomerSearchFromExcelPositive() {
        List<List<String>> data = ExcelUtils.readSheet(0);

        for (int i = 1; i <= 5 && i < data.size(); i++) {
            List<String> row = data.get(i);
            String[] valuesToTest = {row.get(1), row.get(2), row.get(7), row.get(6)};
            String[] fieldLabels = {"First Name", "Last Name", "Username", "Email"};

            for (int j = 0; j < valuesToTest.length; j++) {
                String testValue = valuesToTest[j];
                String label = fieldLabels[j];
                System.out.println("Positive Test - " + label + ": [" + testValue + "]");
                Assert.assertTrue(authorizePage.searchCustomerAndValidate(testValue),
                        "Record not found for " + label + ": " + testValue);
            }
        }
    }

    // --- Test Case 3: Excel-driven Negative Search Validation (Automation_16_03) ---
    @Test(priority = 3)
    public void validateCustomerSearchFromExcelNegative() {
        List<List<String>> data = ExcelUtils.readSheet(1);

        for (int i = 1; i <= 3 && i < data.size(); i++) {
            List<String> row = data.get(i);
            String[] valuesToTest = {row.get(1), row.get(2), row.get(7), row.get(6)};
            String[] fieldLabels = {"First Name", "Last Name", "Username", "Email"};

            for (int j = 0; j < valuesToTest.length; j++) {
                String testValue = valuesToTest[j];
                String label = fieldLabels[j];
                System.out.println("Negative Test - " + label + ": [" + testValue + "]");
                Assert.assertTrue(authorizePage.searchCustomerAndExpectNoResult(testValue),
                        "Unexpected record found for " + label + ": " + testValue);
            }
        }
    }
}

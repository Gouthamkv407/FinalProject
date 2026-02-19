package com.insurEdge.testScripts;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.insurEdge.Utilities.BrowserConfig;

public class US17_P1_02 extends BrowserConfig {

    private void displayedNEnabled(WebElement element, String name) {
        wait.until(ExpectedConditions.visibilityOf(element));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        Assert.assertTrue(element.isDisplayed(), name + " should be visible");
        Assert.assertTrue(element.isEnabled(), name + " should be clickable");
    }

    // --- Dashboard ---
    @Test(priority = 1)
    public void validateDashboard() {
        WebElement dash = homePage.getElement(homePage.dashBoard);
//        dash.click();
        displayedNEnabled(dash, "Dashboard");
    }

    // --- Customer ---
    @Test(priority = 2)
    public void validateCustomerMenu() {
        WebElement cust = homePage.getElement(homePage.customer);
        cust.click();
        displayedNEnabled(cust, "Customer");
    }

    @Test(priority = 3)
    public void validateCreateCustomer() {
        WebElement createCust = homePage.getElement(homePage.create_customer);
        displayedNEnabled(createCust, "Create Customer");
    }

    @Test(priority = 4)
    public void validateViewCustomer() {
        WebElement viewCust = homePage.getElement(homePage.view_customer);
        displayedNEnabled(viewCust, "View Customer");
    }

    @Test(priority = 5)
    public void validateAuthorizeCustomer() {
        WebElement authCust = homePage.getElement(homePage.authorize_customer);
        displayedNEnabled(authCust, "Authorize Customer");
    }

    // --- Category ---
    @Test(priority = 6)
    public void validateCategoryMenu() {
        WebElement cat = homePage.getElement(homePage.category);
        cat.click();
        displayedNEnabled(cat, "Category");
    }

    @Test(priority = 7)
    public void validateCreateMainCategory() {
        WebElement mainCat = homePage.getElement(homePage.create_main_category);
        displayedNEnabled(mainCat, "Create Main Category");
    }

    @Test(priority = 8)
    public void validateCreateSubCategory() {
        WebElement subCat = homePage.getElement(homePage.create_sub_category);
        displayedNEnabled(subCat, "Create Sub Category");
    }

    // --- Policy ---
    @Test(priority = 9)
    public void validatePolicyMenu() {
        WebElement pol = homePage.getElement(homePage.policy);
        pol.click();
        displayedNEnabled(pol, "Policy");
    }

    @Test(priority = 10)
    public void validateCreatePolicy() {
        WebElement createPol = homePage.getElement(homePage.create_policy);
        displayedNEnabled(createPol, "Create Policy");
    }

    @Test(priority = 11)
    public void validateAuthorizePolicy() {
        WebElement authPol = homePage.getElement(homePage.authorize_policy);
        displayedNEnabled(authPol, "Authorize Policy");
    }

    @Test(priority = 12)
    public void validateViewPolicy() {
        WebElement viewPol = homePage.getElement(homePage.view_policy);
        displayedNEnabled(viewPol, "View Policy");
    }

    @Test(priority = 13)
    public void validateModifyPolicy() {
        WebElement modPol = homePage.getElement(homePage.modify_policy);
        displayedNEnabled(modPol, "Modify Policy");
    }

    // --- Policy Holder ---
    @Test(priority = 14)
    public void validatePolicyHolderMenu() {
        WebElement ph = homePage.getElement(homePage.policy_holder);
        ph.click();
        displayedNEnabled(ph, "Policy Holder");
    }

    @Test(priority = 15)
    public void validateAppliedPolicyHolder() {
        WebElement appliedPH = homePage.getElement(homePage.applied_policy_holder);
        displayedNEnabled(appliedPH, "Applied Policy Holder");
    }

    @Test(priority = 16)
    public void validateApprovedPolicyHolder() {
        WebElement approvedPH = homePage.getElement(homePage.approved_policy_holder);
        displayedNEnabled(approvedPH, "Approved Policy Holder");
    }

    @Test(priority = 17)
    public void validatePendingPolicyHolder() {
        WebElement pendingPH = homePage.getElement(homePage.pending_policy_holder);
        displayedNEnabled(pendingPH, "Pending Policy Holder");
    }

    @Test(priority = 18)
    public void validateRejectedPolicyHolder() {
        WebElement rejectedPH = homePage.getElement(homePage.rejected_policy_holder);
        displayedNEnabled(rejectedPH, "Rejected Policy Holder");
    }

    // --- Questions ---
    @Test(priority = 19)
    public void validateQuestionsMenuAndLinks() {
        WebElement q = homePage.getElement(homePage.questions);
        q.click();
        displayedNEnabled(q, "Questions");

        WebElement link1 = homePage.getElement(homePage.link1_questions);
        displayedNEnabled(link1, "Link1 Questions");

        WebElement link2 = homePage.getElement(homePage.link2_questions);
        displayedNEnabled(link2, "Link2 Questions");
    }
}

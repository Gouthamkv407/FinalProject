package com.insurEdge.testScripts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.insurEdge.Utilities.BrowserConfig;

public class US17_P1_06 extends BrowserConfig {

    private void expandSection(By header, By child) {
        WebElement hdr = wait.until(ExpectedConditions.elementToBeClickable(header));
        hdr.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(child));
    }

    private void clickAndValidate(By header, By child, String expectedPath, String message) {
        expandSection(header, child);
        WebElement childEl = wait.until(ExpectedConditions.elementToBeClickable(child));
        childEl.click();

        try {
            wait.until(ExpectedConditions.urlContains(expectedPath));

            if (!driver.getCurrentUrl().contains(expectedPath)) {
                // Navigation failed → recover
                recoverToDashboard();
                Assert.fail(message + " | Navigation failed. Current URL: " + driver.getCurrentUrl());
            } else {
                // Success → stay on the page
                Assert.assertTrue(driver.getCurrentUrl().contains(expectedPath),
                        message + " | Current URL: " + driver.getCurrentUrl());
            }
        } catch (Exception e) {
            // Exception during navigation → recover
            recoverToDashboard();
            throw e;
        }
    }

    private void clickAndValidateWith404(By header, By child, String expectedPath, String message) {
        expandSection(header, child);
        WebElement childEl = wait.until(ExpectedConditions.elementToBeClickable(child));
        childEl.click();

        try {
            wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains(expectedPath),
                ExpectedConditions.titleContains("404")
            ));

            if (homePage.pageShows404()) {
                System.out.println("Broken link detected: " + expectedPath);
                recoverToDashboard(); // recover only on error
                Assert.fail(message + " - Link returned 404");
            } else if (!driver.getCurrentUrl().contains(expectedPath)) {
                recoverToDashboard(); // recover only if wrong URL
                Assert.fail(message + " - Wrong navigation. Current URL: " + driver.getCurrentUrl());
            } else {
                // Success → stay on the page
                Assert.assertTrue(driver.getCurrentUrl().contains(expectedPath),
                        message + " | Current URL: " + driver.getCurrentUrl());
            }
        } catch (Exception e) {
            recoverToDashboard(); // recover only if exception
            throw e;
        }
    }

    // -------------------- Dashboard --------------------
    @Test(priority = 1)
    public void dashboard() {
        WebElement dash = wait.until(ExpectedConditions.elementToBeClickable(homePage.dashBoard));
        dash.click();
        wait.until(ExpectedConditions.urlContains("AdminDashboard"));
        Assert.assertTrue(driver.getCurrentUrl().contains("AdminDashboard"),
                "Dashboard did NOT navigate to home page");
        // stays on dashboard naturally
    }

    // -------------------- Customer --------------------
    @Test(priority = 2)
    public void expandCustomer() {
        expandSection(homePage.customer, homePage.create_customer);
        Assert.assertTrue(homePage.getElement(homePage.create_customer).isDisplayed(),
                "Customer menu NOT expanded.");
    }

    @Test(priority = 3)
    public void customerCreate() {
        clickAndValidate(homePage.customer, homePage.create_customer,
                "CreateCustomer", "Create Customer NOT reached");
    }

    @Test(priority = 4)
    public void customerView() {
        clickAndValidate(homePage.customer, homePage.view_customer,
                "AdminViewCustomer", "View Customer NOT reached");
    }

    @Test(priority = 5)
    public void customerAuthorize() {
        clickAndValidate(homePage.customer, homePage.authorize_customer,
                "AdminAuthorizeCustomer", "Authorize Customer NOT reached");
    }

    // -------------------- Category --------------------
    @Test(priority = 6)
    public void expandCategory() {
        expandSection(homePage.category, homePage.create_main_category);
        Assert.assertTrue(homePage.getElement(homePage.create_main_category).isDisplayed(),
                "Category menu NOT expanded.");
    }

    @Test(priority = 7)
    public void createMainCategory() {
        clickAndValidate(homePage.category, homePage.create_main_category,
                "AdminCreateMainCategory", "Create Main Category NOT reached");
    }

    @Test(priority = 8)
    public void createSubCategory() {
        clickAndValidate(homePage.category, homePage.create_sub_category,
                "AdminCreateSubCategory", "Create Sub Category NOT reached");
    }

    // -------------------- Policy --------------------
    @Test(priority = 9)
    public void expandPolicy() {
        expandSection(homePage.policy, homePage.create_policy);
        Assert.assertTrue(homePage.getElement(homePage.create_policy).isDisplayed(),
                "Policy menu NOT expanded.");
    }

    @Test(priority = 10)
    public void createPolicy() {
        clickAndValidate(homePage.policy, homePage.create_policy,
                "AdminCreatePolicy", "Create Policy NOT reached");
    }

    @Test(priority = 11)
    public void authorizePolicy() {
        clickAndValidate(homePage.policy, homePage.authorize_policy,
                "AdminAuthorizePolicy", "Authorize Policy NOT reached");
    }

    @Test(priority = 12)
    public void viewPolicy() {
        clickAndValidate(homePage.policy, homePage.view_policy,
                "AdminViewPolicy", "View Policy NOT reached");
    }

    @Test(priority = 13)
    public void modifyPolicy() {
        clickAndValidate(homePage.policy, homePage.modify_policy,
                "AdminModifyPolicy", "Modify Policy NOT reached");
    }

    // -------------------- Policy Holders --------------------
    @Test(priority = 14)
    public void expandPolicyHolders() {
        expandSection(homePage.policy_holder, homePage.applied_policy_holder);
        Assert.assertTrue(homePage.getElement(homePage.applied_policy_holder).isDisplayed(),
                "Policy Holders menu NOT expanded.");
    }

    @Test(priority = 15)
    public void appliedPolicyHolder() {
        clickAndValidate(homePage.policy_holder, homePage.applied_policy_holder,
                "AdminAppliedPolicyHolders", "Applied Policy Holder NOT reached");
    }

    @Test(priority = 16)
    public void approvedPolicyHolder() {
        clickAndValidate(homePage.policy_holder, homePage.approved_policy_holder,
                "AdminApprovedPolicyHolder", "Approved Policy Holder NOT reached");
    }

    @Test(priority = 17)
    public void pendingPolicyHolder() {
        clickAndValidate(homePage.policy_holder, homePage.pending_policy_holder,
                "AdminPendingPolicyHolder", "Pending Policy Holder NOT reached");
    }

    @Test(priority = 18)
    public void rejectedPolicyHolder() {
        clickAndValidate(homePage.policy_holder, homePage.rejected_policy_holder,
                "AdminRejectedPolicyHolder", "Rejected Policy Holder NOT reached");
    }

    // -------------------- Questions --------------------
    @Test(priority = 19)
    public void expandQuestions() {
        expandSection(homePage.questions, homePage.link1_questions);
        Assert.assertTrue(homePage.getElement(homePage.link1_questions).isDisplayed(),
                "Questions menu NOT expanded.");
    }

    @Test(priority = 20)
    public void link1Questions() {
        clickAndValidateWith404(homePage.questions, homePage.link1_questions,
                "charts-chartjs", "Link1 Questions NOT reached");
    }

    @Test(priority = 21)
    public void link2Questions() {
        clickAndValidateWith404(homePage.questions, homePage.link2_questions,
                "charts-apexcharts", "Link2 Questions NOT reached");
    }
}

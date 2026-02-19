package com.insurEdge.HomePage;

import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {
    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // --- Header Section Locators ---
    private By user = By.id("txtUsername");
    private By pass = By.id("txtPassword");
    private By loginBtn = By.id("BtnLogin");
    private By menuBtn = By.cssSelector(".bi.bi-list.toggle-sidebar-btn");
    private By logo = By.cssSelector("img[src='assets/img/logo.png']");
    private By headerUsername = By.id("loggedInUserName");
    private By profileImgCircle = By.cssSelector(".rounded-circle");

    // --- Left Panel Locators (aligned with LeftMenu) ---
    public By dashBoard             = By.cssSelector(".nav-link[href='AdminDashboard.aspx']");

    // Customer
    public By customer              = By.cssSelector("a[data-bs-target='#components-nav']");
    public By create_customer       = By.cssSelector("#components-nav a[href*='CreateCustomer']");
    public By view_customer         = By.cssSelector("#components-nav a[href*='AdminViewCustomer']");
    public By authorize_customer    = By.cssSelector("#components-nav a[href*='AdminAuthorizeCustomer']");

    // Category
    public By category              = By.cssSelector("a[data-bs-target='#forms-nav']");
    public By create_main_category  = By.cssSelector("#forms-nav a[href*='AdminCreateMainCategory']");
    public By create_sub_category   = By.cssSelector("#forms-nav a[href*='AdminCreateSubCategory']");

    // Policy
    public By policy                = By.cssSelector("a[data-bs-target='#tables-nav']");
    public By create_policy         = By.cssSelector("#tables-nav a[href*='AdminCreatePolicy']");
    public By authorize_policy      = By.cssSelector("#tables-nav a[href*='AdminAuthorizePolicy']");
    public By view_policy           = By.cssSelector("#tables-nav a[href*='AdminViewPolicy']");
    public By modify_policy         = By.cssSelector("#tables-nav a[href*='AdminModifyPolicy']");

    // Policy Holders
    public By policy_holder         = By.cssSelector("a[data-bs-target='#policyHolder-nav']");
    public By applied_policy_holder = By.cssSelector("#policyHolder-nav a[href*='AdminAppliedPolicyHolders']");
    public By approved_policy_holder= By.cssSelector("#policyHolder-nav a[href*='AdminApprovedPolicyHolder']");
    public By pending_policy_holder = By.cssSelector("#policyHolder-nav a[href*='AdminPendingPolicyHolder']");
    public By rejected_policy_holder= By.cssSelector("#policyHolder-nav a[href*='AdminRejectedPolicyHolder']");

    // Questions
    public By questions             = By.cssSelector("a[data-bs-target='#charts-nav']");
    public By link1_questions       = By.cssSelector("#charts-nav a[href$='charts-chartjs.html']");
    public By link2_questions       = By.cssSelector("#charts-nav a[href$='charts-apexcharts.html']");

    // --- Footer Locators ---
    public By footerLine1 = By.xpath("//*[@id='footer']/div[1]");
    public By footerLine1Bold = By.xpath("//*[@id='footer']/div/strong/span");
    public By footerLine2 = By.xpath("//*[@id='footer']/div[2]");
    public By footerQeaLink = By.xpath("//*[@id='footer']/div[2]/a");
    public By footerCredits = By.cssSelector("div[class='credits']");

    // --- Profile Section Locators ---
    public By profilePhoto = By.xpath("//li/a/img");
    public By profileUserNameBox = By.id("profileUserName");
    public By arrow = By.id("loggedInUserName");

    // --- Actions ---
    public void login(String username, String password) {
        driver.findElement(user).sendKeys(username);
        driver.findElement(pass).sendKeys(password);
        driver.findElement(loginBtn).click();
    }

    // --- Header Getters ---
    public WebElement getMenuButton() { return driver.findElement(menuBtn); }
    public WebElement getLogo() { return driver.findElement(logo); }
    public WebElement getHeaderUsername() { return driver.findElement(headerUsername); }
    public WebElement getProfileImage() { return driver.findElement(profileImgCircle); }

    // --- Profile Section Getters ---
    public WebElement getProfilePhoto() { return driver.findElement(profilePhoto); }
    public WebElement getProfileUserNameBox() { return driver.findElement(profileUserNameBox); }
    public WebElement getArrow() { return driver.findElement(arrow); }

    // --- Generic Getter ---
    public WebElement getElement(By locator) { return driver.findElement(locator); }

    // --- Footer Actions ---
    public String performActionsOnFooterSectionCredits() {
        WebElement footerSection = driver.findElement(footerCredits);
        if (footerSection.getText().equals("Designed by QEA Skill Enable Function")) {
            return "Footer Present";
        } else {
            return "Footer Absent";
        }
    }

    public String performActionsOnFooterSectionHyperLink() {
        WebElement footerHyperLink = driver.findElement(footerQeaLink);
        footerHyperLink.click();

        Set<String> winHandles = driver.getWindowHandles();
        for (String s : winHandles) {
            driver.switchTo().window(s);
            String title = driver.getTitle();
            if (title.contains("BootstrapMade")) {
                return "Hyperlink present";
            }
        }
        return "Hyperlink absent";
    }

    // --- Sidebar / Recovery Helpers ---
    public boolean hasSidebar() {
        try {
            return driver.findElement(By.id("sidebar-nav")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void waitForSidebar() {
        for (int i = 0; i < 10; i++) {
            if (hasSidebar()) return;
            try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        }
    }

    public boolean pageShows404() {
        String src = driver.getPageSource().toLowerCase();
        return src.contains("404 - file or directory not found") || src.contains("server error");
    }
}

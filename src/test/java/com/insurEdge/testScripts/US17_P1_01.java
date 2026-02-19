package com.insurEdge.testScripts;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.insurEdge.Utilities.BrowserConfig;

public class US17_P1_01 extends BrowserConfig {

    private static final String EXPECTED_USERNAME = "admin_user";
    private static final String EXPECTED_COLOR_RGB = "1, 41, 112";

    @Test(priority = 1)
    public void validateMenuButton() {
        WebElement btn = homePage.getMenuButton();
        Assert.assertTrue(btn.isDisplayed());
        Assert.assertTrue(btn.isEnabled());
    }

    @Test(priority = 2)
    public void validateLogo() {
        WebElement logo = homePage.getLogo();
        Assert.assertTrue(logo.isDisplayed());
        Assert.assertTrue(logo.isEnabled());
        logo.click();
    }

    @Test(priority = 3)
    public void validateUsernameDisplayedAndMatch() {
        WebElement userEl = homePage.getHeaderUsername();
        Assert.assertTrue(userEl.isDisplayed());
        Assert.assertEquals(userEl.getText().trim().toLowerCase(), EXPECTED_USERNAME.toLowerCase());
    }

    @Test(priority = 4)
    public void validateProfileImageCircular() {
        WebElement img = homePage.getProfileImage();
        String borderRadius = img.getCssValue("border-radius");
        Assert.assertTrue(borderRadius.contains("50"), "Profile image should be circular");
    }

    @Test(priority = 5)
    public void validateUsernameColorAndPosition() {
        WebElement userEl = homePage.getHeaderUsername();
        WebElement img = homePage.getProfileImage();

        String color = userEl.getCssValue("color");
        Assert.assertTrue(color.contains(EXPECTED_COLOR_RGB), "Username should be dark blue");

        int logoX = img.getLocation().getX();
        int userX = userEl.getLocation().getX();
        Assert.assertTrue(userX > logoX, "Username should be next to profile image");
    }

    @Test(priority = 6)
    public void validateUserProfileDropdown() {
        WebElement userEl = homePage.getHeaderUsername();
        Assert.assertTrue(userEl.isDisplayed());
        Assert.assertTrue(userEl.isEnabled());
        userEl.click(); // dropdown interaction
    }
}

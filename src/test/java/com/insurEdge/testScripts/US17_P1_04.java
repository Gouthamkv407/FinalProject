package com.insurEdge.testScripts;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.insurEdge.Utilities.BrowserConfig;

public class US17_P1_04 extends BrowserConfig {

    private void displayedNEnabled(WebElement element, String name) {
        wait.until(ExpectedConditions.visibilityOf(element));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        Assert.assertTrue(element.isDisplayed(), name + " should be visible");
        Assert.assertTrue(element.isEnabled(), name + " should be clickable");
    }

    // --- Profile Photo Click and Username Box ---
    @Test(priority = 1)
    public void validateProfilePhotoAndUserNameBox() {
        WebElement profilePhoto = homePage.getProfilePhoto();
        wait.until(ExpectedConditions.elementToBeClickable(profilePhoto)).click();
        displayedNEnabled(profilePhoto, "Profile Photo");

        try {
            WebElement userNameBox = wait.until(
                ExpectedConditions.visibilityOf(homePage.getProfileUserNameBox())
            );
            Assert.assertTrue(userNameBox.isDisplayed(), "Username Box should be visible after profile photo click");
        } catch (Exception e) {
            Assert.fail("Username Box did not appear after profile photo click");
        }
    }

    // --- Second Profile Photo Click ---
    @Test(priority = 2)
    public void validateSecondProfilePhotoClick() {
        WebElement profilePhoto = homePage.getProfilePhoto();
        wait.until(ExpectedConditions.elementToBeClickable(profilePhoto)).click();
        displayedNEnabled(profilePhoto, "Profile Photo (Second Click)");
    }

    // --- Arrow Click and Username Box ---
    @Test(priority = 3)
    public void validateArrowAndUserNameBox() {
        WebElement arrow = homePage.getArrow();
        wait.until(ExpectedConditions.elementToBeClickable(arrow)).click();
        displayedNEnabled(arrow, "Arrow");

        try {
            WebElement userNameBox = wait.until(
                ExpectedConditions.visibilityOf(homePage.getProfileUserNameBox())
            );
            Assert.assertTrue(userNameBox.isDisplayed(), "Username Box should be visible after arrow click");
        } catch (Exception e) {
            Assert.fail("Username Box did not appear after arrow click");
        }
    }
}
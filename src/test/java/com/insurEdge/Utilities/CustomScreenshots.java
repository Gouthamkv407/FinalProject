package com.insurEdge.Utilities;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CustomScreenshots {

    public static void captureElementScreenshot(WebDriver driver, WebElement element, String testName) throws InterruptedException {
        try {
        	((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
        	new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOf(element));
        		Thread.sleep(500); // tiny buffer for repaint
        		File src = element.getScreenshotAs(OutputType.FILE);
            String baseDir = System.getProperty("user.dir") + "//test-output//screenshots//";
            File dest = new File(baseDir + testName + ".png");
            dest.getParentFile().mkdirs(); 
            FileUtils.copyFile(src, dest);
            System.out.println("Screenshot saved: " + dest.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

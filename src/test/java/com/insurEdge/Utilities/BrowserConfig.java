package com.insurEdge.Utilities;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import com.insurEdge.HomePage.HomePage;

public class BrowserConfig {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected HomePage homePage;

    @Parameters({"browser", "url"})
    @BeforeClass(alwaysRun = true)
    public void setUp(@Optional("chrome")String browser,@Optional("https://qeaskillhub.cognizant.com/LoginPage") String url) {
        switch (browser.toLowerCase()) {
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            default:
                throw new IllegalArgumentException("Unknown Browser: " + browser);
        }
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        driver.get(url);

        homePage = new HomePage(driver);
        homePage.login("admin_user", "testadmin");
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // --- Recovery Helper ---
    public void recoverToDashboard() {
        driver.navigate().back();
        homePage.waitForSidebar();
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePage.dashBoard));
    }
}

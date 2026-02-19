package com.insurEdge.AuthorizePage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.insurEdge.Utilities.CustomScreenshots;

public class AuthorizePage {
    private WebDriver driver;
    private WebDriverWait wait;

    // --- Locators ---
    private By customerMenu = By.cssSelector("a[data-bs-target='#components-nav']");
    private By authorizeCustomerLink = By.cssSelector("#components-nav a[href*='AdminAuthorizeCustomer']");
    private By pageTitle = By.xpath("//*[text()='Authorize Customer']");
    private By pageTrail = By.xpath("//ol[@class='breadcrumb']");
    private By customerTable = By.tagName("table");
    private By columnHeaders = By.xpath("//th");

    // Action buttons
    private By approveBtn = By.id("ContentPlaceHolder_Admin_btnApprove");
    private By rejectBtn  = By.id("ContentPlaceHolder_Admin_btnReject");
    private By deleteBtn  = By.id("ContentPlaceHolder_Admin_btnDelete");

    // Search Customer locators
    private By searchBoxLabel = By.xpath("//*[text() = 'Search Customer:']");
    private By searchBox = By.id("ContentPlaceHolder_Admin_txtSearch");
    private By searchBoxPlaceholder = By.xpath("//input[@placeholder='Search...']");
    private By recordsDropdown = By.id("ContentPlaceHolder_Admin_ddlPageSize");
    private By resultRows = By.xpath("//table[@id='ContentPlaceHolder_Admin_GridViewCustomers']//tr[td]");

    // --- Checkbox locators ---
    private By rowCheckboxes = By.cssSelector("input[id^='ContentPlaceHolder_Admin_GridViewCustomers_chkSelect_']");

    // --- Pagination locators ---
    private By paginationControls = By.xpath("//table//table/tbody/tr");
    
    // --- Records Per Page Dropdown --- 
    private By recordsLabel = By.cssSelector("label[for='ddlPageSize']"); 
    private By gridDataRows = By.xpath("//table[@id='ContentPlaceHolder_Admin_GridViewCustomers']//tr[.//input[starts-with(@id,'ContentPlaceHolder_Admin_GridViewCustomers_chkSelect_')]]");
    
    // --- US17_P1_18 Approval Flow Locators ---
    private By firstRowCheckbox = By.id("ContentPlaceHolder_Admin_GridViewCustomers_chkSelect_0");
    private By confirmApprovalPopup = By.xpath("//*[text() = '✅ Confirm Approval']");
    private By successPopup = By.xpath("//*[text() = 'Selected customers have been approved successfully.']");
    private By popupOkBtn = By.xpath("//button[text()='OK']");
    private By firstRowStatusCell = By.xpath("//table[@id='ContentPlaceHolder_Admin_GridViewCustomers']/tbody/tr[2]/td[16]");

    // --- NEW: US17_P1_19 Rejection Flow & Overlay Locators ---
    private By confirmRejectTitle = By.xpath("//*[@id='popupOverlay']//h3[normalize-space()='❌ Confirm Rejection' or normalize-space()='Confirm Rejection']");
    private By successRejectTitle = By.xpath("//*[@id='popupOverlay']//h3[normalize-space()='❌ Rejection Successful!' or normalize-space()='✅ Reject Success']");
    private By noSelectionTitle = By.xpath("//*[@id='popupOverlay']//h3[normalize-space()='⚠️ No Selection']");
    private By overlayBodyText = By.xpath("//*[@id='popupOverlay']//p");
    private By overlayOkBtn = By.xpath("//*[@id='popupOverlay']//button[normalize-space()='OK']");
    
    // --- NEW: US17_P1_20 Deletion Flow Locators ---
    private By confirmDeleteTitle = By.xpath("//*[@id='popupOverlay']//h3[normalize-space()='🗑️ Confirm Deletion' or normalize-space()='Confirm Deletion']");
    private By successDeleteTitle = By.xpath("//*[@id='popupOverlay']//h3[normalize-space()='🗑️ Deletion Successful!' or normalize-space()='✅ Delete Success']");

    // --- Additional Locators (kept for specific tests) ---
    private By customerMenuLoc = By.cssSelector("[class='bi bi-menu-button-wide']");
    private By customerAuthorizeLinkLoc = By.xpath("//*[text()='Authorize']");
    private By customerBreadcrumb = By.xpath("//li[@class='breadcrumb-item']/a[@href='AdminDashboard.aspx']");

    public AuthorizePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // --- Navigation ---
    public void navigateToAuthorizeCustomer() {
        wait.until(ExpectedConditions.elementToBeClickable(customerMenu)).click();
        wait.until(ExpectedConditions.elementToBeClickable(authorizeCustomerLink)).click();
    }

    // --- US17_P1_18 Action Methods ---
    public void selectFirstCustomer() {
        wait.until(ExpectedConditions.elementToBeClickable(firstRowCheckbox)).click();
    }

    public void clickApprove() {
        wait.until(ExpectedConditions.elementToBeClickable(approveBtn)).click();
    }

    public boolean isConfirmPopupDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(confirmApprovalPopup)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickOkOnPopup() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(popupOkBtn));
        try {
            btn.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        }
    }

    public boolean isSuccessMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(successPopup)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getFirstCustomerStatus() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(successPopup));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(firstRowStatusCell)).getText().trim();
    }

    // --- NEW: US17_P1_19 Rejection Action Methods ---

    /**
     * Finds the first customer whose status is NOT "Rejected", selects it, 
     * and returns the Customer ID for later verification.
     */
    public String selectFirstNonRejectedCustomer() {
        List<WebElement> rows = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(resultRows));
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 16) {
                String status = cells.get(15).getText().trim(); // Status is td[16], index 15
                if (!status.equalsIgnoreCase("Rejected")) {
                    WebElement checkbox = row.findElement(By.cssSelector("input[type='checkbox']"));
                    if (!checkbox.isSelected()) checkbox.click();
                    return cells.get(1).getText().trim(); // Return Customer ID (td[2])
                }
            }
        }
        return null;
    }

    public void clickReject() {
        wait.until(ExpectedConditions.elementToBeClickable(rejectBtn)).click();
    }

    public void clickOverlayOk() {
        wait.until(ExpectedConditions.elementToBeClickable(overlayOkBtn)).click();
    }

    public boolean isConfirmRejectPopupVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(confirmRejectTitle)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isRejectSuccessPopupVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(successRejectTitle)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isNoSelectionWarningVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(noSelectionTitle)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getWarningMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(overlayBodyText)).getText().trim();
    }

    public void clearCheckboxes() {
        List<WebElement> checkboxes = driver.findElements(rowCheckboxes);
        for (WebElement cb : checkboxes) {
            if (cb.isSelected()) cb.click();
        }
    }

    public boolean isCustomerMenuVisibleAndClickable() {
        try {
            WebElement customerDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(customerMenu));
            customerDropdown.click();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isAuthorizeCustomerLinkVisibleAndClickable() {
        try {
            WebElement authorizeButton = wait.until(ExpectedConditions.visibilityOfElementLocated(authorizeCustomerLink));
            authorizeButton.click();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSearchBoxVisible() {
        try {
            WebElement searchCustomer = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
            return searchCustomer.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean searchCustomerAndValidate(String value) {
        try {
            WebElement box = wait.until(ExpectedConditions.elementToBeClickable(searchBox));
            box.click();
            box.sendKeys(Keys.CONTROL + "a");
            box.sendKeys(Keys.BACK_SPACE);
            box.sendKeys(value + Keys.ENTER);
            Thread.sleep(2000); 
            List<WebElement> results = driver.findElements(resultRows);
            return !results.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean searchCustomerAndExpectNoResult(String value) {
        try {
            WebElement box = wait.until(ExpectedConditions.elementToBeClickable(searchBox));
            box.click();
            box.sendKeys(Keys.CONTROL + "a");
            box.sendKeys(Keys.BACK_SPACE);
            box.sendKeys(value + Keys.ENTER);
            Thread.sleep(2000); 
            List<WebElement> results = driver.findElements(resultRows);
            return results.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean validateAuthorizeCustomerTitleColor() {
        WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
        String hexColor = Color.fromString(title.getCssValue("color")).asHex();
        return hexColor.equals("#012970"); 
    }

    public String getBreadcrumbTrailText() {
        WebElement trail = wait.until(ExpectedConditions.visibilityOfElementLocated(pageTrail));
        return trail.getText();
    }

    public boolean validateCustomerTableAppearance() {
        WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(customerTable));
        return table.isDisplayed();
    }

    public boolean validateCustomerTableColumnCount() {
        List<WebElement> headers = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(columnHeaders));
        return headers.size() == 17;
    }

    public List<String> getCustomerTableColumnHeaders() {
        List<WebElement> headers = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(columnHeaders));
        List<String> columnList = new ArrayList<>();
        headers.forEach(h -> columnList.add(h.getText().trim()));
        return columnList;
    }

    public boolean validateCustomerTableColumnText() {
        String[] expected = {
            "Customer ID","First Name","Last Name","Gender","Date of Birth","Senior Citizen",
            "Email","User Name","Password","Mobile","Address","City","State","Zip Code",
            "Status","Created Date"
        };
        List<String> actual = getCustomerTableColumnHeaders();
        if (actual.size() < expected.length) return false;

        for (int i = 0; i < expected.length; i++) {
            if (!expected[i].equals(actual.get(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean validateApproveButtonColor() {
        WebElement approve = wait.until(ExpectedConditions.elementToBeClickable(approveBtn));
        String hex = Color.fromString(approve.getCssValue("background-color")).asHex();
        return hex.equals("#198754"); 
    }

    public boolean validateRejectButtonColor() {
        WebElement reject = wait.until(ExpectedConditions.elementToBeClickable(rejectBtn));
        String hex = Color.fromString(reject.getCssValue("background-color")).asHex();
        return hex.equals("#dc3545"); 
    }

    public boolean validateDeleteButtonColor() {
        WebElement delete = wait.until(ExpectedConditions.elementToBeClickable(deleteBtn));
        String hex = Color.fromString(delete.getCssValue("background-color")).asHex();
        return hex.equals("#ffc107"); 
    }

    public boolean validateSearchBoxAppearance() {
        WebElement label = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBoxLabel));
        WebElement box = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
        return label.getText().equals("Search Customer:") && box.isDisplayed();
    }

    public boolean validatePlaceholderText() {
        WebElement box = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBoxPlaceholder));
        return "Search...".equals(box.getAttribute("placeholder"));
    }

    public boolean validateSearchBoxPosition() {
        WebElement box = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(recordsDropdown));
        return box.getLocation().getX() < dropdown.getLocation().getX();
    }

    public boolean validateCheckboxesVisible() {
        List<WebElement> checkboxes = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(rowCheckboxes));
        return !checkboxes.isEmpty() && checkboxes.stream().allMatch(WebElement::isDisplayed);
    }

    public boolean validateCheckboxSelectionToggle() {
        List<WebElement> checkboxes = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(rowCheckboxes));
        if (checkboxes.isEmpty()) return false;

        WebElement first = checkboxes.get(0);
        boolean initialState = first.isSelected();
        first.click();
        boolean toggledState = first.isSelected();
        return initialState != toggledState;
    }

    public boolean validatePaginationControlsClickable() {
        JavascriptExecutor js = (JavascriptExecutor) driver; 
        WebElement paginationArea = wait.until(ExpectedConditions.presenceOfElementLocated(paginationControls)); 
        js.executeScript("arguments[0].scrollIntoView(true);", paginationArea); 
        List<WebElement> controls = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(paginationControls)); 
        if (controls.isEmpty()) return false; 
        for (WebElement control : controls) { 
            wait.until(ExpectedConditions.elementToBeClickable(control)).click(); 
        } 
        return true; 
    }
    
    public boolean validateCurrentPageHighlighted() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement paginationArea = wait.until(ExpectedConditions.presenceOfElementLocated(paginationControls));
        js.executeScript("arguments[0].scrollIntoView(true);", paginationArea);

        List<WebElement> controls = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(paginationControls));
        if (controls.isEmpty()) return false;

        for (WebElement control : controls) {
            String classAttr = control.getAttribute("class");
            if (classAttr != null && classAttr.contains("active")) {
                return true; 
            }
        }
        return false;
    }

    public boolean isCustomerBreadcrumbVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(customerBreadcrumb));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCustomerBreadcrumbStyledConsistently() {
        try {
            WebElement customer = wait.until(ExpectedConditions.visibilityOfElementLocated(customerBreadcrumb));
            String color = customer.getCssValue("color");
            return color != null && !color.isBlank();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean clickCustomerBreadcrumbRedirectsToDashboard() {
        try {
            WebElement customer = wait.until(ExpectedConditions.elementToBeClickable(customerBreadcrumb));
            customer.click();
            wait.until(ExpectedConditions.urlContains("AdminDashboard"));
            return driver.getCurrentUrl().contains("AdminDashboard");
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isRecordsDropdownVisible() {
        try {
            WebElement ddl = wait.until(ExpectedConditions.visibilityOfElementLocated(recordsDropdown));
            WebElement label = wait.until(ExpectedConditions.visibilityOfElementLocated(recordsLabel));
            return ddl.isDisplayed() && label.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getRecordsDropdownLabelText() {
        try {
            WebElement label = wait.until(ExpectedConditions.visibilityOfElementLocated(recordsLabel));
            return label.getText().trim();
        } catch (Exception e) {
            return null;
        }
    }

    public List<String> getRecordsDropdownOptionValues() {
        WebElement ddl = wait.until(ExpectedConditions.visibilityOfElementLocated(recordsDropdown));
        org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(ddl);
        return select.getOptions().stream()
                .map(o -> o.getAttribute("value"))
                .toList();
    }

    public String getRecordsDropdownDefaultValue() {
        WebElement ddl = wait.until(ExpectedConditions.visibilityOfElementLocated(recordsDropdown));
        org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(ddl);
        return select.getFirstSelectedOption().getAttribute("value");
    }

    public void selectRecordsPerPage(String value) {
        WebElement ddl = wait.until(ExpectedConditions.elementToBeClickable(recordsDropdown));
        org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(ddl);
     
        By gridTable = By.id("ContentPlaceHolder_Admin_GridViewCustomers");
        WebElement tableBefore = driver.findElement(gridTable);
     
        select.selectByValue(value); 
     
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.stalenessOf(tableBefore));
        } catch (Exception ignored) {}
     
        wait.until(ExpectedConditions.presenceOfElementLocated(gridTable));
     
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(d -> ((JavascriptExecutor) d)
                            .executeScript("return document.readyState").equals("complete"));
        } catch (Exception ignored) {}
     
        int expectedMax = Integer.parseInt(value);
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(d -> getVisibleDataRowCount() <= expectedMax);
        } catch (Exception ignored) {}
     
        int stable = 0, last = -1;
        for (int i = 0; i < 20; i++) { 
            int now = getVisibleDataRowCount();
            if (now == last) {
                stable++;
                if (stable >= 2) break;
            } else {
                stable = 0;
            }
            last = now;
            try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        }
    }

    private int getVisibleDataRowCount() {
        try {
            return (int) driver.findElements(gridDataRows).stream()
                    .filter(WebElement::isDisplayed)
                    .count();
        } catch (Exception e) {
            return 0;
        }
    }
     
    public int getRenderedRowCount() {
        return getVisibleDataRowCount();
    }
    
    public void clickDelete() { 
        wait.until(ExpectedConditions.elementToBeClickable(deleteBtn)).click(); 
    }

    // --- Actions Methods from Alternate Tests ---
    public String actionsonTitle() {
        driver.findElement(customerMenuLoc).click();
        driver.findElement(customerAuthorizeLinkLoc).click();
        WebElement pageTitleElement = driver.findElement(pageTitle);
        String colourInRGB = pageTitleElement.getCssValue("color");
        String colourInHex = Color.fromString(colourInRGB).asHex();
        if (colourInHex.equals("#012970")) {
            System.out.println("font Colour of title is correct");
            return "font Colour of title is correct";
        } else {
            return "title not as expected";
        }
    }

    public String actionsOnCustomerInfoTableAppearance() {
        driver.findElement(customerMenuLoc).click();
        driver.findElement(customerAuthorizeLinkLoc).click();
        System.out.println("Checking for Appearance of Customer's Details table");
        WebElement table = driver.findElement(customerTable);
        if (table.isDisplayed()) {
            System.out.println("Table is Appearing on the Screen");
            return "Table is Appearing on the Screen";
        } else {
            System.out.println("Table Not Present!!!-Test Failed...");
            return "Table Not Present!!!-Test Failed...";
        }
    }

    public String actionsOncustomerInfoTableColumnCount() {
        driver.findElement(customerMenuLoc).click();
        driver.findElement(customerAuthorizeLinkLoc).click();
        List<WebElement> columnHeadersList = driver.findElements(columnHeaders);
        if (columnHeadersList.size() == 17) {
            System.out.println("All columns are displayed correctly");
            return "All columns are displayed correctly";
        } else return "Column Missing";
    }

    public String actionsOnCustomerInfoTableColumnSpelling() throws InterruptedException {
        driver.findElement(customerMenuLoc).click();
        driver.findElement(customerAuthorizeLinkLoc).click();
        List<WebElement> columnHeadersList = driver.findElements(columnHeaders);
        List<String> columnList = new ArrayList<>();
        columnHeadersList.forEach(ch -> columnList.add(ch.getText()));
        System.out.println(columnList);
        boolean flag = false;
        String data[] = {"Customer ID","First Name","Last Name","Gender","Date of Birth","Senior Citizen","Email","User Name","Password","Mobile","Address","City","State","Zip Code","Status","Created Date"};
        for (int i = 0; i < data.length; i++) {
            if (i + 1 < columnList.size() && data[i].equals(columnList.get(i + 1))) {
                System.out.println("Column Matched-" + data[i]);
            } else {
                flag = true;
                String actual = (i + 1 < columnList.size()) ? columnList.get(i + 1) : "MISSING";
                System.out.println("Mismatch found-" + "Expected: " + data[i] + " Actual: " + actual);
                for (WebElement ele : columnHeadersList) {
                    if (ele.getText().equalsIgnoreCase(actual)) {
                        CustomScreenshots.captureElementScreenshot(driver, ele, actual);
                        break;
                    }
                }
            }
        }
        if (flag)
            return "mismatch";
        return "passed";
    }

    public String performActionsOnPageTrail() {
        WebElement fetchPageTrail = driver.findElement(pageTrail);
        String fetchTrailText = fetchPageTrail.getText();
        System.out.println("BreadCrumbs present");
        return fetchTrailText;
    }

    // --- Deletion Action Methods ---
    public void selectFirstCustomerForDelete() {
        List<WebElement> checkboxes = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(rowCheckboxes));
        if (!checkboxes.isEmpty()) {
            WebElement first = checkboxes.get(0);
            if (!first.isSelected()) {
                first.click();
            }
        }
    }

    public boolean isConfirmDeletePopupVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(confirmDeleteTitle)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isDeleteSuccessPopupVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(successDeleteTitle)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getStatusOfCustomerById(String customerId) {
        try { Thread.sleep(1500); } catch (InterruptedException ignored) {}
        By statusLoc = By.xpath("//tr[td[normalize-space()='" + customerId + "']]/td[16]");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(statusLoc)).getText().trim();
    }
}
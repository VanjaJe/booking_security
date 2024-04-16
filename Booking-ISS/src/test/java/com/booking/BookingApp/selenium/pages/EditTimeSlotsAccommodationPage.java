package com.booking.BookingApp.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class EditTimeSlotsAccommodationPage {

    private WebDriver driver;

    @FindBy(css="input[id='vanja']")
    private WebElement el;

    @FindBy(name="price")
    private WebElement priceInput;

    @FindBy(css = "#dateRangeForEditPrice .mat-mdc-button-touch-target")
    private WebElement datePickerForPriceBtn;

    @FindBy(css = "#dateRangeForEditFreeDates .mat-mdc-button-touch-target")
    private WebElement datePickerForFreeDatesBtn;

    @FindBy(css=".mat-calendar")
    private WebElement dateDialog;

    @FindBy(id = "applyDateForPrice")
    private WebElement btnApplyPrice;

    @FindBy(id = "applyDateForTimeSlots")
    private WebElement btnApplyDates;

    @FindBy(xpath = "//tbody[contains(@class,mat-calendar-body)]")
    private WebElement calendarBody;

    @FindBy(xpath = "//mat-calendar-header//button[contains(@class, 'mat-calendar-period')]")
    private WebElement choseMonthAndYearBtn;

    @FindBy(className = "app-snack-bar")
    private WebElement snackBar;

    @FindBy(className = "editPriceBtn")
    private WebElement acceptEditPrice;

    @FindBy(className = "acceptFreeDatesButton")
    private WebElement acceptEditFreeDates;

    @FindBy(xpath = "//table//tbody/tr")
    private List<WebElement> tableRows;

    @FindBy(xpath = "//table")
    private WebElement table;

    @FindBy(id = "logoutBtn")
    private WebElement logOutBtn;

    int before;

//    //p/following-sibling::div


    private static String PAGE_URL = "http://localhost:4200/home/accommodations/accommodationDetails/1/editDates";

    public EditTimeSlotsAccommodationPage(WebDriver driver) {

        this.driver = driver;
        (new WebDriverWait(driver, Duration.of(10, ChronoUnit.SECONDS))).until(ExpectedConditions.urlToBe("http://localhost:4200/home"));

        driver.get(PAGE_URL);
        PageFactory.initElements(driver, this);
    }

    public void loading() {
        (new WebDriverWait(driver, Duration.of(10, ChronoUnit.SECONDS))).until(ExpectedConditions.visibilityOf(el));
    }

    public void inputPrice(double price) {
        (new WebDriverWait(driver, Duration.of(10, ChronoUnit.SECONDS))).until(ExpectedConditions.visibilityOf(table));
        priceInput.clear();
        priceInput.sendKeys(Double.toString(price));
    }

    public void inputDateForPrice(String year, String month, String startDate, String endDate) {
        datePickerForPriceBtn.click();
        (new WebDriverWait(driver, Duration.of(10, ChronoUnit.SECONDS))).until(ExpectedConditions.visibilityOf(dateDialog));

        choseMonthAndYearBtn.click();
        String yearInp = String.format("//button/span[text()=' %s ']", year);
        driver.findElement(By.xpath(yearInp)).click();

        String monthInp = String.format("//button/span[text()=' %s ']", month);
        driver.findElement(By.xpath(monthInp)).click();

//        String monthInput = String.format("/tr/td[text()=' %s ']", month);
//        WebElement calendar = calendarBody.findElement(By.xpath(monthInput));

        String start = String.format("//td//span[contains(text(),'%s')]", startDate);
        String end = String.format("//td//span[contains(text(),'%s')]", endDate);

        driver.findElement(By.xpath(start)).click();
        driver.findElement(By.xpath(end)).click();

//        String locatorStartDate = String.format("//tbody[contains(@class,mat-calendar-body)]/tr/td[]//td//span[contains(text(),'%s')]", startDate);
//        String locatorEndDate = String.format("//tbody[contains(@class,mat-calendar-body)]//td//span[contains(text(),'%s')]", endDate);

//        driver.findElement(By.xpath(locatorStartDate)).click();
//        driver.findElement(By.xpath(locatorEndDate)).click();
    }


    public void inputDateForFreeDates(String year, String month, String startDate, String endDate) {
        datePickerForFreeDatesBtn.click();
        (new WebDriverWait(driver, Duration.of(10, ChronoUnit.SECONDS))).until(ExpectedConditions.visibilityOf(dateDialog));

        choseMonthAndYearBtn.click();
        String yearInp = String.format("//button/span[text()=' %s ']", year);
        driver.findElement(By.xpath(yearInp)).click();

        String monthInp = String.format("//button/span[text()=' %s ']", month);
        driver.findElement(By.xpath(monthInp)).click();

        String start = String.format("//td//span[contains(text(),'%s')]", startDate);
        String end = String.format("//td//span[contains(text(),'%s')]", endDate);

        driver.findElement(By.xpath(start)).click();
        driver.findElement(By.xpath(end)).click();
    }

    public void clickApplyForPrice() {
        btnApplyPrice.click();
    }

    public void clickApplyForFreeDates() {
        btnApplyDates.click();
    }

    public void acceptPrice() {
        acceptEditPrice.click();
    }

    public void acceptFreeDates() throws InterruptedException {
        acceptEditFreeDates.click();
        Thread.sleep(1000);
    }

    public int getStartTableSize() throws InterruptedException {
        (new WebDriverWait(driver, Duration.of(10, ChronoUnit.SECONDS))).until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//table/tbody/*"), 0));
//        List<WebElement> list = driver.findElements(By.xpath("//table/tbody/*"));
//        (new WebDriverWait(driver, Duration.of(10, ChronoUnit.SECONDS))).until(ExpectedConditions.numberOfElementsToBe(By.xpath("//table/tbody"), 1));
        before = tableRows.size();
        return before;
    }

    public int getEndTableSize() throws InterruptedException {
        (new WebDriverWait(driver, Duration.of(10, ChronoUnit.SECONDS))).until(ExpectedConditions.visibilityOf(table));
        (new WebDriverWait(driver, Duration.of(10, ChronoUnit.SECONDS))).until(ExpectedConditions.numberOfElementsToBe(By.xpath("//table/tbody/*"), before+1));

//        List<WebElement> list = driver.findElements(By.xpath("//table/tbody/*"));
//        (new WebDriverWait(driver, Duration.of(10, ChronoUnit.SECONDS))).until(ExpectedConditions.numberOfElementsToBe(By.xpath("//table/tbody"), 2));
        return before+1;
    }


    public void logout() {
        logOutBtn.click();
    }
}
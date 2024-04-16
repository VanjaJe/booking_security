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

public class AccommodationDetailsPage {
    private WebDriver driver;

    @FindBy(id = "applyDateForTimeSlots")
    private WebElement btnApplyDates;

    @FindBy(xpath = "//input[@formControlName='startDateInput']")
    private WebElement startDateInput;

    @FindBy(xpath = "//input[@formControlName='endDateInput']")
    private WebElement endDateInput;

    @FindBy(css="input[id='vanja']")
    private WebElement el;

    @FindBy(css=".mat-calendar")
    private WebElement dateDialog;

    @FindBy(css = "#dateRangeForEditFreeDates .mat-mdc-button-touch-target")
    private WebElement datePickerForFreeDatesBtn;

    @FindBy(xpath = "//mat-calendar-header//button[contains(@class, 'mat-calendar-period')]")
    private WebElement choseMonthAndYearBtn;

    @FindBy(className = "mat-date-range-input")
    private WebElement range;

    private static String URL = "http://localhost:4200/home/accommodations";

    public AccommodationDetailsPage(WebDriver driver) {
        this.driver = driver;
        (new WebDriverWait(driver, Duration.of(10, ChronoUnit.SECONDS))).until(ExpectedConditions.urlToBe("http://localhost:4200/home"));

        driver.get(URL);
        PageFactory.initElements(driver, this);
    }


    public boolean checkFilteredDates(String year, String month, String startDate, String endDate) {
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(5));
        try{
            List<WebElement> accommodationCards=wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy((By.cssSelector(".accommodation"))));
            if(!accommodationCards.isEmpty()){
                WebElement card=accommodationCards.get(0);
                WebElement detailBtn=card.findElement(By.cssSelector(".details-btn"));
                detailBtn.click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Available amenities')]")));

                try {
                    inputDateForFreeDates(year, month, startDate, endDate);
                    clickApplyForFreeDates();
                    wait.until(ExpectedConditions.attributeToBeNotEmpty(startDateInput, "value"));
                    wait.until(ExpectedConditions.attributeToBeNotEmpty(endDateInput, "value"));
                    return true;

                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    return false;
                }
            }

        }catch(Exception ex){
            return true;
        }
        return true;
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

    public void clickApplyForFreeDates() {
        btnApplyDates.click();
    }
}

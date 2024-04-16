package com.booking.BookingApp.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class AccommodationsPage {

    private WebDriver driver;
    private JavascriptExecutor jsExecutor;

    private static String PAGE_URL = "http://localhost:4200/home/accommodations";

    @FindBy(css = "input[formControlName='destination']")
    private WebElement destinationInput;

    @FindBy(css = "mat-select[formControlName='accommodationType']")
    private WebElement typeSelect;

    @FindBy(css = "input[formControlName='guestNum']")
    private WebElement guestNumInput;

    @FindBy(css = "input[formControlName='minValue']")
    private WebElement minSlider;

    @FindBy(css = "input[formControlName='maxValue']")
    private WebElement maxSlider;
    @FindBy(id = "disablePrice-btn")
    private WebElement disablePriceBtn;

    @FindBy(id = "search-btn")
    private WebElement searchBtn;

    @FindBy(id="clear-filters")
    private WebElement clearBtn;

    //moram da im malo menjam imena

    @FindBy(css = "#dateRangeForEditFreeDates .mat-mdc-button-touch-target")
    private WebElement datePickerForFreeDatesBtn;

    @FindBy(css=".mat-calendar")
    private WebElement dateDialog;

    @FindBy(id = "applyDateForTimeSlots")
    private WebElement btnApplyDates;

    @FindBy(xpath = "//mat-calendar-header//button[contains(@class, 'mat-calendar-period')]")
    private WebElement choseMonthAndYearBtn;

    @FindBy(xpath = "//input[@formControlName='startDateInput']")
    private WebElement startDateInput;

    @FindBy(xpath = "//input[@formControlName='endDateInput']")
    private WebElement endDateInput;

    @FindBy(id="price")
    WebElement calculatedPrice;

    @FindBy(css = ".accommodation")
    List<WebElement>accommodationCards;


    public AccommodationsPage(WebDriver driver) {
        this.driver = driver;
        this.jsExecutor = (JavascriptExecutor) driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(driver, this);

    }
    public boolean pageLoaded() {
        WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy((By.cssSelector(".accommodation"))));
        return true;
    }

    public void filterByLocationTypeAndAmenities(String name, String type,String[] amenities) {

        destinationInput.clear();
        destinationInput.sendKeys(name);

        typeSelect.click();

        WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
        WebElement typeOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-option//span[contains(text(),'" + type.toUpperCase() + "')]")));
        typeOption.click();

        for(String amenity:amenities){
            WebElement checkbox=driver.findElement(By.xpath("//mat-checkbox//label[contains(text(),'"+amenity.toLowerCase()+"')]"));
            checkbox.click();
        }
        disablePriceBtn.click();
        searchBtn.click();
        clearBtn.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void filterByGuestsDatesAndPrices(String guestNum,int priceMin,int priceMax,String year, String month, String startDate, String endDate){

        guestNumInput.clear();
        guestNumInput.sendKeys(guestNum);

        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", minSlider, priceMin);
        ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", minSlider);

        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", maxSlider, priceMax);
        ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", maxSlider);

        inputDateForFreeDates(year, month, startDate, endDate);
        clickApplyForFreeDates();

        searchBtn.click();
        clearBtn.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public void inputDateForFreeDates(String year, String month, String startDate, String endDate) {
        datePickerForFreeDatesBtn.click();
        (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.visibilityOf(dateDialog));

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


    public boolean checkFilteredLocation(String location) {
        for(WebElement card:accommodationCards){
            WebElement address=card.findElement(By.cssSelector(".accommodation-address"));
            if(!address.getText().contains(location)){
                return false;
            }
        }
        return true;
    }



    public boolean checkFilteredAmenities(String[] amenities) {
        WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
        WebElement card=accommodationCards.get(0);
        WebElement detailBtn=card.findElement(By.cssSelector(".details-btn"));
        detailBtn.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Available amenities')]")));
        List<WebElement>spans=driver.findElements(By.xpath("//mat-list-item[contains(@class,'amenityItem')]/span"));
        for (String amenity : amenities) {
            boolean found = false;
            for (WebElement span : spans) {
                if (span.getText().equals(amenity)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }

        return true;
    }


    public boolean checkFilteredPrice(int minPrice, int maxPrice) {
            for(WebElement card:accommodationCards){
                WebElement price=card.findElement(By.id("price"));
                String priceText = price.getText().split(":")[1].trim();
                int priceValue = Integer.parseInt(priceText);
                if(!(priceValue>=minPrice && priceValue<=maxPrice)){
                    return false;
                }
            }
        return true;
    }

    public boolean checkFilteredDates(String year, String month, String startDate, String endDate) {
        WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
        WebElement card=accommodationCards.get(0);
        WebElement detailBtn=card.findElement(By.cssSelector(".details-btn"));
        detailBtn.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Available amenities')]")));

        try {
            inputDateForFreeDates(year, month, startDate, endDate);
            clickApplyForFreeDates();
            wait.until(ExpectedConditions.attributeToBeNotEmpty(startDateInput, "value"));
            wait.until(ExpectedConditions.attributeToBeNotEmpty(endDateInput, "value"));

        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public int countCards() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            List<WebElement> accommodationCards = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy((By.cssSelector(".accommodation"))));
            return accommodationCards.size();
        } catch (Exception ex) {
            return 0; // no cardsFound
        }
    }

}

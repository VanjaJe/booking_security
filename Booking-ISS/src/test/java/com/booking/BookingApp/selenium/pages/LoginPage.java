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


public class LoginPage {

    private WebDriver driver;

    private static String PAGE_URL = "http://localhost:4200/logIn";

    @FindBy(id = "username")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(css = "button[type='submit']")
    private WebElement submitBtn;
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(driver, this);
    }

    public void login(String username,String password){
        setUsername(username);
        setPassword(password);
        submitBtn.click();
//        ExpectedConditions.urlToBe(url);
    }

    private void setPassword(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    private void setUsername(String username) {
        usernameInput.clear();
        usernameInput.sendKeys(username);
    }

}

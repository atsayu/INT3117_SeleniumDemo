package org.example;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;



public class LoginPage {
    protected static WebDriver driver;

    static By username = By.id("user-name");
    static By password = By.id("password");
    static By login_btn = By.id("login-button");
    static By error = By.xpath("//*[@id=\"login_button_container\"]/div/form/div[3]/h3");

    public static void login() {
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        driver.findElement(username).sendKeys("standard_user");
        driver.findElement(password).sendKeys("secret_sauce");
        driver.findElement(login_btn).click();
    }

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
    }

    @After
    public void tearDown() {
        driver.quit();
    }
    @Test
    public void validTest() {
        driver.findElement(username).sendKeys("standard_user");
        driver.findElement(password).sendKeys("secret_sauce");
        driver.findElement(login_btn).click();
    }

    @Test
    public void invalidTest() {
        driver.findElement(username).sendKeys("standard_user_wrong");
        driver.findElement(password).sendKeys("secret_sauce");
        driver.findElement(login_btn).click();
        String errorMessage = driver.findElement(error).getText();
        Assert.assertEquals("Epic sadface: Username and password do not match any user in this service", errorMessage);
    }

}

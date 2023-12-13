package org.example;

import org.checkerframework.checker.units.qual.C;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v85.emulation.Emulation;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;


import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class HomePage {
    WebDriver driver;

    static By addProductButton = By.className("btn_inventory");
    static By productCount = By.xpath("//*[@id=\"shopping_cart_container\"]/a/span");
    static By removeProductButton = By.xpath("//*[@id=\"remove-sauce-labs-backpack\"]");
    static By productList = By.xpath("//*[@id=\"inventory_container\"]/div");

    static By sortList = By.xpath("//*[@id=\"header_container\"]/div[2]/div/span/select");
    static By productName = By.className("inventory_item_name");
    static By productPrice = By.className("inventory_item_price");
    static By product = By.className("inventory_item");

    static By cart = By.xpath("//*[@id=\"shopping_cart_container\"]/a");
    static By checkoutButton = By.xpath("//*[@id=\"checkout\"]");
    static By firstName = By.xpath("//*[@id=\"first-name\"]");
    static By lastName  = By.xpath("//*[@id=\"last-name\"]");
    static By zipCode = By.xpath("//*[@id=\"postal-code\"]");
    static By continueButton = By.xpath("//*[@id=\"continue\"]");
    static By finishButton = By.xpath("//*[@id=\"finish\"]");

    static By errorMessage = By.xpath("//*[@id=\"checkout_info_container\"]/div/form/div[1]/div[4]/h3");
    @Before
    public void setUp() {

        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        driver.findElement(LoginPage.username).sendKeys("standard_user");
        driver.findElement(LoginPage.password).sendKeys("secret_sauce");
        driver.findElement(LoginPage.login_btn).click();
    }

    @After
    public  void tearDown() {
        driver.quit();
    }

    @Test
    public void addProductTest01() {
        driver.findElement(addProductButton).click();
        String number = driver.findElement(productCount).getText();
        Assert.assertEquals("1", number);
    }

    @Test
    public void removeProductTest01() {
        List<WebElement> addButtons = driver.findElements(addProductButton);
        addButtons.get(0).click();
        addButtons.get(1).click();
        String prevProductNumber = driver.findElement(productCount).getText();
        int prevNum = Integer.parseInt(prevProductNumber);
        driver.findElement(removeProductButton).click();
        String afterProductNumber = driver.findElement(productCount).getText();
        int afterNum = Integer.parseInt(afterProductNumber);
        Assert.assertEquals(prevNum - afterNum, 1);
    }

    @Test
    public void sortTest01() {
        Select sortOptions = new Select(driver.findElement(sortList));
        sortOptions.selectByValue("az");
        List< WebElement> products = driver.findElement(productList).findElements(product);
        Assert.assertTrue(products.get(0).findElement(productName).getText().compareTo(products.get(1).findElement(productName).getText()) < 0);
    }

    @Test
    public void sortTest02() {
        Select sortOptions = new Select(driver.findElement(sortList));
        sortOptions.selectByValue("za");
        List< WebElement> products = driver.findElement(productList).findElements(product);
        Assert.assertTrue(products.get(0).findElement(productName).getText().compareTo(products.get(1).findElement(productName).getText()) > 0);
    }

    @Test
    public void sortTest03() {
        Select sortOptions = new Select(driver.findElement(sortList));
        sortOptions.selectByValue("lohi");
        List< WebElement> products = driver.findElement(productList).findElements(product);
        double firstPrice = Double.parseDouble(products.get(0).findElement(productPrice).getText().substring(1));
        double secondPrice = Double.parseDouble(products.get(1).findElement(productPrice).getText().substring(1));
        Assert.assertTrue(firstPrice <= secondPrice);
    }

    @Test
    public void sortTest04() {
        Select sortOptions = new Select(driver.findElement(sortList));
        sortOptions.selectByValue("hilo");
        List< WebElement> products = driver.findElement(productList).findElements(product);
        double firstPrice = Double.parseDouble(products.get(0).findElement(productPrice).getText().substring(1));
        double secondPrice = Double.parseDouble(products.get(1).findElement(productPrice).getText().substring(1));
        Assert.assertTrue(firstPrice >= secondPrice);
    }


    @Test
    public void checkOutTest01() {
        driver.findElement(addProductButton).click();
        driver.findElement(cart).click();
        driver.findElement(checkoutButton).click();
        driver.findElement(firstName).sendKeys("fname");
        driver.findElement(lastName).sendKeys("lname");
        driver.findElement(zipCode).sendKeys("100000");
        driver.findElement(continueButton).click();
        driver.findElement(finishButton).click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/checkout-complete.html");
    }

    @Test
    public void checkOutTest02() {
        driver.findElement(addProductButton).click();
        driver.findElement(cart).click();
        driver.findElement(checkoutButton).click();
        driver.findElement(firstName).sendKeys("");
        driver.findElement(lastName).sendKeys("lname");
        driver.findElement(zipCode).sendKeys("100000");
        driver.findElement(continueButton).click();
        String errorText = driver.findElement(errorMessage).getText();
        Assert.assertEquals(errorText, "Error: First Name is required");
    }

    @Test
    public void checkOutTest03() {
        driver.findElement(addProductButton).click();
        driver.findElement(cart).click();
        driver.findElement(checkoutButton).click();
        driver.findElement(firstName).sendKeys("fname");
        driver.findElement(lastName).sendKeys("");
        driver.findElement(zipCode).sendKeys("100000");
        driver.findElement(continueButton).click();
        String errorText = driver.findElement(errorMessage).getText();
        Assert.assertEquals(errorText, "Error: Last Name is required");
    }

    @Test
    public void checkOutTest04() {
        driver.findElement(addProductButton).click();
        driver.findElement(cart).click();
        driver.findElement(checkoutButton).click();
        driver.findElement(firstName).sendKeys("fname");
        driver.findElement(lastName).sendKeys("lname");
        driver.findElement(zipCode).sendKeys("");
        driver.findElement(continueButton).click();
        String errorText = driver.findElement(errorMessage).getText();
        Assert.assertEquals(errorText, "Error: Postal Code is required");
    }

}

package org.example;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v85.emulation.Emulation;

import java.util.Optional;

public class CDP {
    @Test
    public void mockResolutionTest() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        DevTools devTools = ((HasDevTools) driver).getDevTools();
        devTools.createSession();
        devTools.send(Emulation.setDeviceMetricsOverride(600, 800, 50, true, Optional.empty(), Optional.of(600), Optional.of(800), Optional.empty(), Optional.empty() , Optional.empty(), Optional.empty(), Optional.empty()));
        driver.get("https://www.saucedemo.com/");
        Thread.sleep(5000);
        driver.quit();
    }

    @Test
    public void testLocation() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        DevTools devTools = ((HasDevTools) driver).getDevTools();
        devTools.createSession();
        driver.get("https://my-location.org/");
        devTools.send(Emulation.setGeolocationOverride(Optional.of(35.689487), Optional.of(139.691706), Optional.of(1)));
        driver.navigate().refresh();
        Thread.sleep(10000);
        driver.quit();

    }

}

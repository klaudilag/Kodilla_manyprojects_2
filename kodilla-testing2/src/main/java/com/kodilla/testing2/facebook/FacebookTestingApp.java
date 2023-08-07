package com.kodilla.testing2.facebook;

import com.kodilla.testing2.config.WebDriverConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FacebookTestingApp {
    public static void main(String[] args) {
        WebDriver driver = WebDriverConfig.getDriver(WebDriverConfig.CHROME);
        driver.get("https://facebook.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Maximum waiting time in seconds
        WebElement cookiesButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("u_0_k_6J")));
        cookiesButton.click();

        WebElement registerButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("u_0_0_qw")));
        registerButton.click();
    }
}

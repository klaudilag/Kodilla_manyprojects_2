package com.kodilla.testing2.facebook;

import com.kodilla.testing2.config.WebDriverConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FacebookTestingApp {
    public static final String SEARCHFIELD = "/html/body/div[3]/div[2]/div/div/div/div/div[4]/button[2]";
    public static final String REGISTERBUTTONXPATH = "/html/body/div/div/div/div/div/div/div[2]/div/div/form/div[5]/a";
    public static final String NAME = "/html/body/div[3]/div[2]/div/div/div[2]/div/div/div/form/div/div/div/div/div/input";
    public static final String SURNAME = "//div[contains(@class, \"uiStickyPlaceholderInput\")]/input";
    public static final String DAY = "//div[contains(@class, \"_5k_5\")]/span/span/select";
    public static final String MONTH = "//div[contains(@class, \"_5k_5\")]/span/span/select[2]";
    public static final String YEAR = "//div[contains(@class, \"_5k_5\")]/span/span/select[3]";

    public static void main(String[] args) {
        WebDriver driver = WebDriverConfig.getDriver(WebDriverConfig.CHROME);
        driver.get("https://facebook.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Maximum waiting time in seconds
        WebElement cookiesButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(SEARCHFIELD)));
        cookiesButton.click();

        WebElement registerButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(REGISTERBUTTONXPATH)));
        registerButton.click();

        WebElement namePlace = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(NAME)));
        namePlace.sendKeys("Klaudiusz");

        WebElement surnamePlace = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(SURNAME)));
        surnamePlace.sendKeys("Lagodzinski");

        WebElement dayPlace = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(DAY)));
        Select selectDay = new Select(dayPlace);
        selectDay.selectByIndex(30);

        WebElement monthPlace = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(MONTH)));
        Select selectMonth = new Select(monthPlace);
        selectMonth.selectByIndex(4);

        WebElement yearPlace = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(YEAR)));
        Select selectYear = new Select(yearPlace);
        selectYear.selectByIndex(22);
    }
}

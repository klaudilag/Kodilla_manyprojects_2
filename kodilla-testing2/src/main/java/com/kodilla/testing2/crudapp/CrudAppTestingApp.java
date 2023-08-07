package com.kodilla.testing2.crudapp;

import com.kodilla.testing2.config.WebDriverConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CrudAppTestingApp{
    public static final String XPATH_INPUT = "//html/body/main/section/form/fieldset/input"; // [1]

    public static void main(String[] args) {
        WebDriver driver = WebDriverConfig.getDriver(WebDriverConfig.CHROME);
        driver.get("https://klaudilag.github.io/");                      // [2]

        WebElement searchField = driver.findElement(By.xpath(XPATH_INPUT));    // [3]
        searchField.sendKeys("New robotic task");                              // [4]
    }
}
package com.online_market.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertEquals;

/**
 * Class for testing admin's posibilty for editing items
 *
 * @author Siarhei
 * @version 1.0
 */
public class EditItemSeleniumTest {

    private WebDriver driver;

    @Before
    public void init() {

        System.setProperty("webdriver.chrome.driver", "C:\\chrome_driver\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    public void TestEditItemByAdmin() {

        driver.get(Url.LOGIN);

        String login = "admin1";
        String password = "1234";
        int id = 9;

        driver.findElement(By.id("login")).sendKeys(login);
        driver.findElement(By.id("password")).sendKeys(password);

        driver.findElement(By.className("submit-button")).click();
        assertEquals(Url.CATALOG, driver.getCurrentUrl());

        int itemId = 2;
        driver.findElement(By.id("itemPage" + itemId)).click();
        assertEquals(Url.ITEM + itemId, driver.getCurrentUrl());

        int availableCount = 100;
        driver.findElement(By.id("availableCount")).clear();
        driver.findElement(By.id("availableCount")).sendKeys(availableCount + "");
        driver.findElement(By.id("editItem")).click();

        assertEquals(driver.findElement(By.id("availableCount")).getAttribute("value"), availableCount + "");
    }


    @After
    public void destroy() {
        driver.close();
    }
}

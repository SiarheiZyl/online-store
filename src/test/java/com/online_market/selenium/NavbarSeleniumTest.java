package com.online_market.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertEquals;

/**
 * Class for testing navigation bar
 *
 * @author Siarhei
 * @version 1.0
 */
public class NavbarSeleniumTest {

    private WebDriver driver;

    @Before
    public void init() {

        System.setProperty("webdriver.chrome.driver", "C:\\chrome_driver\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    public void TestLoginWithCorrectParameters() {

        driver.get(Url.LOGIN);

        String login = "admin1";
        String password = "1234";
        int id = 9;

        driver.findElement(By.id("login")).sendKeys(login);
        driver.findElement(By.id("password")).sendKeys(password);

        driver.findElement(By.className("submit-button")).click();
        assertEquals(Url.CATALOG, driver.getCurrentUrl());

        driver.findElement(By.id("editOrders")).click();
        assertEquals(Url.EDIT_ORDERS, driver.getCurrentUrl());

        driver.findElement(By.id("editCategories")).click();
        assertEquals(Url.EDIT_CATEGORIES, driver.getCurrentUrl());

        driver.findElement(By.id("statistics")).click();
        assertEquals(Url.STATISTICS, driver.getCurrentUrl());

        driver.findElement(By.id("profile")).click();
        assertEquals(Url.USER_INFO + id, driver.getCurrentUrl());

        driver.findElement(By.id("search")).click();
        assertEquals(Url.SEARCH, driver.getCurrentUrl());

        driver.findElement(By.id("basket")).click();
        assertEquals(Url.BASKET, driver.getCurrentUrl());

        driver.findElement(By.id("store")).click();
        assertEquals(Url.CATALOG, driver.getCurrentUrl());

        driver.findElement(By.id("login")).click();
        assertEquals(Url.LOGIN, driver.getCurrentUrl());
    }


    @After
    public void destroy() {
        driver.close();
    }
}

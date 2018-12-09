package com.online_market.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertEquals;

/**
 * Class for testing login page
 *
 * @author Siarhei
 * @version 1.0
 */
public class LoginSeleniumTest {

    private WebDriver driver;

    @Before
    public void init() {

        System.setProperty("webdriver.chrome.driver", "C:\\chrome_driver\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    public void TestLoginWithCorrectParameters() {

        driver.get(Url.LOGIN);

        String login = "szbee";
        String password = "1111";

        driver.findElement(By.id("login")).sendKeys(login);
        driver.findElement(By.id("password")).sendKeys(password);

        driver.findElement(By.className("submit-button")).click();

        assertEquals(Url.CATALOG, driver.getCurrentUrl());
    }
    @Test
    public void TestLoginWithIncorrectParameters() {

        driver.get(Url.LOGIN);

        String login = "szbee";
        String password = "11112222";

        driver.findElement(By.id("login")).sendKeys(login);
        driver.findElement(By.id("password")).sendKeys(password);

        driver.findElement(By.className("submit-button")).click();

        String error = driver.findElement(By.id("errormsg")).getText();

        assertEquals(error, "Login or password is incorrect!");
        assertEquals(Url.LOGIN_ERROR, driver.getCurrentUrl());
    }

    @After
    public void destroy() {
        driver.close();
    }
}